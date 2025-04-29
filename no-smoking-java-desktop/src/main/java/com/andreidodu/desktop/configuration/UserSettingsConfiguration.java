package com.andreidodu.desktop.configuration;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@Setter
@ConfigurationProperties(prefix = "user.settings")
@RequiredArgsConstructor
public class UserSettingsConfiguration {

    private String jwt;
    private String email;

}
