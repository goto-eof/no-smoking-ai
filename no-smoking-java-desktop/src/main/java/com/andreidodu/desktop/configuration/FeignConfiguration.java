package com.andreidodu.desktop.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableFeignClients(basePackages = "com.andreidodu.desktop.client")
public class FeignConfiguration {
}
