package com.andreidodu.server.entity;

import com.andreidodu.server.entity.common.ModelCommon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ns_daily_smoking_data")
@EntityListeners(AuditingEntityListener.class)
public class DailySmokingData extends ModelCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date")
    private LocalDate date;

    @Column(nullable = false)
    private Integer weekday;

    @Column(name = "is_holiday", nullable = false)
    private Boolean isHoliday;

    @Column(name = "cigarettes_smoked_count", nullable = false)
    private Integer cigarettesSmokedCount;

}
