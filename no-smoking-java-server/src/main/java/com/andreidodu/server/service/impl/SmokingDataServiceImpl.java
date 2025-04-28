package com.andreidodu.server.service.impl;

import com.andreidodu.common.dto.DailySmokingDataDTO;
import com.andreidodu.common.dto.SmokingDataDTO;
import com.andreidodu.common.dto.api.CountersDTO;
import com.andreidodu.server.entity.DailySmokingData;
import com.andreidodu.server.entity.SmokingData;
import com.andreidodu.server.entity.User;
import com.andreidodu.server.mapper.DailySmokingDataMapper;
import com.andreidodu.server.mapper.SmokingDataMapper;
import com.andreidodu.server.repository.DailySmokingDataRepository;
import com.andreidodu.server.repository.SmokingDataRepository;
import com.andreidodu.server.repository.UserRepository;
import com.andreidodu.server.service.SmokingDataService;
import com.andreidodu.server.util.AuthenticationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SmokingDataServiceImpl implements SmokingDataService {

    @Value("${application.ai.endpoint}")
    private String aiEndPoint;

    private final SmokingDataRepository smokingDataRepository;
    private final SmokingDataMapper smokingDataMapper;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final DailySmokingDataRepository dailySmokingDataRepository;
    private final DailySmokingDataMapper dailySmokingDataMapper;
    private final AuthenticationUtil authenticationUtil;


    @Override
    public SmokingDataDTO add() {
        User user = getAuthenticatedUser();
        LocalDateTime now = LocalDateTime.now();
        SmokingDataDTO smokingData = new SmokingDataDTO();
        smokingData.setDateTime(now);
        return save(user.getId(), smokingData);
    }

    private User getAuthenticatedUser() {
        final String email = authenticationUtil.getAuthenticatedUserEmail();
        return this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    private SmokingDataDTO save(int userId, SmokingDataDTO smokingDataDTO) {
        SmokingData model = smokingDataMapper.toModel(smokingDataDTO);
        User user = userRepository.findById(userId).orElseThrow();
        model.setUser(user);
        model = smokingDataRepository.save(model);
        return smokingDataMapper.toDTO(model);
    }

    @Override
    public Optional<SmokingDataDTO> deleteLastInserted() {
        User user = getAuthenticatedUser();

        return smokingDataRepository.findTopByUser_IdOrderByIdDesc(user.getId())
                .flatMap(lastSmokingData -> {
                    SmokingDataDTO dto = smokingDataMapper.toDTO(lastSmokingData);
                    smokingDataRepository.delete(lastSmokingData);
                    return Optional.of(dto);
                });
    }


    @Override
    public Optional<SmokingDataDTO> getLastItemInserted() {
        User user = getAuthenticatedUser();
        return smokingDataRepository.findTopByUser_IdOrderByIdDesc(user.getId())
                .flatMap(lastInserted -> {
                    SmokingDataDTO dto = smokingDataMapper.toDTO(lastInserted);
                    return Optional.of(dto);
                });
    }

    @Override
    public int countByDate(LocalDate date) {
        User user = getAuthenticatedUser();
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = start.with(LocalTime.MAX);
        log.debug("{}-{}", start, end);
        return smokingDataRepository.countByUserAndDateTimeIsBetween(user, start, end);
    }

    @Override
    public DailySmokingDataDTO populateDailySmokingData(LocalDateTime dateTime) {
        User user = getAuthenticatedUser();

        // populate for yesterday
        dateTime = dateTime.minusDays(1);
        LocalDateTime start = dateTime.minusDays(1).toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        Integer countByDate = smokingDataRepository.countByUser_IdAndCreatedDateBetween(user.getId(), start, end);

        LocalDate localDate = dateTime.toLocalDate();
        Optional<DailySmokingData> dailySmokingDataOptional = dailySmokingDataRepository.findByUser_idAndDate(user.getId(), localDate);
        if (dailySmokingDataOptional.isPresent()) {
            DailySmokingData dailySmokingData = dailySmokingDataOptional.get();
            dailySmokingData.setCigarettesSmokedCount(countByDate);
            dailySmokingData = dailySmokingDataRepository.save(dailySmokingData);
            return dailySmokingDataMapper.toDTO(dailySmokingData);
        }

        DailySmokingData dailySmokingData = new DailySmokingData();
        dailySmokingData.setUser(user);
        dailySmokingData.setCigarettesSmokedCount(countByDate);
        dailySmokingData.setDate(localDate);
        dailySmokingData.setWeekday(dateTime.getDayOfWeek().getValue());
        dailySmokingData.setIsHoliday(isHoliday(localDate));
        dailySmokingData = dailySmokingDataRepository.save(dailySmokingData);
        return dailySmokingDataMapper.toDTO(dailySmokingData);
    }

    private static boolean isHoliday(LocalDate date) {
        return DayOfWeek.SATURDAY.getValue() == date.getDayOfWeek().getValue() ||
                DayOfWeek.SUNDAY.getValue() == date.getDayOfWeek().getValue();
    }

    @Override
    public Integer addAndPopulateAndTrainAndRetrieveDailySmokingData(LocalDateTime dateTime) {
        User user = getAuthenticatedUser();
        add();
        populateDailySmokingData(dateTime);
        trainModelByUserId(user.getId());
        return getPrediction(dateTime.toLocalDate());
    }

    @Override
    public Integer getPredictionByDate(int userId, LocalDate now) {
        try {
            return getPrediction(now);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    @Override
    public String trainModelByUserId(Integer userId) {
        String url = aiEndPoint + "/train/" + userId;
        log.debug("trainModelByUserId: {}", url);
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        log.debug(response.getBody());
        return response.getBody();
    }

    @Override
    public Integer getPrediction(LocalDate date) {
        User user = getAuthenticatedUser();
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", user.getId());
        requestBody.put("weekday", date.getDayOfWeek().getValue());
        requestBody.put("is_holiday", isHoliday(date));

        log.debug("Prediction requestBody: {}", requestBody);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                aiEndPoint + "/predict",
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map<String, Object> body = response.getBody();
        if (body != null && body.containsKey("predicted_cigarettes")) {
            return (int) (Integer) body.get("predicted_cigarettes");
        } else {
            System.out.println("Error: " + body);
            return -1;
        }
    }

    @Override
    public CountersDTO getCounters() {
        LocalDate now = LocalDate.now();
        return CountersDTO
                .builder()
                .currentCount(countByDate(now))
                .predictionCount(getPrediction(now))
                .build();
    }

}
