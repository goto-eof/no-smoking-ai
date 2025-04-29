package com.andreidodu.desktop.service.impl;

import com.andreidodu.desktop.configuration.UserSettingsConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
@Component
public class UserSettingsServiceImpl {

    @Autowired
    private UserSettingsConfiguration userSettings;

    public void saveToFile() {
        Properties props = new Properties();
        props.setProperty("user.settings.jwt", userSettings.getJwt());
        props.setProperty("user.settings.email", userSettings.getEmail());

        try (FileOutputStream out = new FileOutputStream("user-settings.properties")) {
            props.store(out, "User Settings");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
