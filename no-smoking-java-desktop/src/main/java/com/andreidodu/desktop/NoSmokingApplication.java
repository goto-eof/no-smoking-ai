package com.andreidodu.desktop;

import com.andreidodu.desktop.util.GuiStarterConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@RequiredArgsConstructor
public class NoSmokingApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        ConfigurableApplicationContext context = SpringApplication.run(NoSmokingApplication.class, args);
        new GuiStarterConfig().start(context);
    }

}
