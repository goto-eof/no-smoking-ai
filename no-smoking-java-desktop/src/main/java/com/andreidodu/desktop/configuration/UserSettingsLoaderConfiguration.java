package com.andreidodu.desktop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

@Configuration
public class UserSettingsLoaderConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() throws IOException {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        File file = new File("user-settings.properties");
        if (!file.exists()) {
            file.createNewFile();
        }
        Resource[] resources = new Resource[]{
                new FileSystemResource("user-settings.properties") // Your custom file
        };
        configurer.setLocations(resources);
        return configurer;
    }
}