package com.andreidodu.server.mapper;

import com.andreidodu.common.dto.UserDTO;
import com.andreidodu.server.entity.User;
import com.andreidodu.server.mapper.common.CommonMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {})
public abstract class UserMapper implements CommonMapper<User, UserDTO> {

}
