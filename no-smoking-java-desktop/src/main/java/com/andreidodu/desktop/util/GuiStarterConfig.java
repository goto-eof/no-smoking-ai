package com.andreidodu.desktop.util;


import com.andreidodu.desktop.service.SmokingDataService;
import com.andreidodu.desktop.window.Window;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class GuiStarterConfig {

    public static final String THEME_METAL_JAVA_IMPROVED = "Metal";
    public static final String THEME_CDE_MOTIF_WINDOWS_95 = "CDE/Motif";
    public static final String THEME_GTK_PLUS = "GTK+";
    public static final String THEME_NIMBUS = "Nimbus";

    public static final List<String> knownThemesLowerCase = List.of(
            THEME_NIMBUS.toLowerCase(),
            THEME_GTK_PLUS.toLowerCase(),
            THEME_CDE_MOTIF_WINDOWS_95.toLowerCase(),
            THEME_METAL_JAVA_IMPROVED.toLowerCase()
    );

    public void start(ConfigurableApplicationContext context) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    applyPreferredTheme();
                } catch (Exception e) {
                    System.err.println("GUI error: " + e.getMessage());
                    tryToApplyDefaultTheme();
                }
                final SmokingDataService smokingDataService = context.getBean(SmokingDataService.class);
                ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
                Window window = new Window(smokingDataService);
                beanFactory.registerSingleton("window", window);

                ScheduledAnnotationBeanPostProcessor postProcessor = context.getBean(ScheduledAnnotationBeanPostProcessor.class);
                postProcessor.postProcessAfterInitialization(window, "window");

                Window windowBean = context.getBean(Window.class);
                windowBean.run();
            }
        });
    }

    private void tryToApplyDefaultTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("GUI error: " + ex.getMessage());
        }
    }


    private List<String> getSystemThemeNamesLowerCase(UIManager.LookAndFeelInfo[] installedLookAndFeels) {
        return Arrays.stream(installedLookAndFeels)
                .map(UIManager.LookAndFeelInfo::getName)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    private void applyPreferredTheme() {
        UIManager.LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();

        Optional.ofNullable("GTK+").filter(theme -> getSystemThemeNamesLowerCase(installedLookAndFeels).contains(theme.toLowerCase()))
                .ifPresentOrElse(theme -> applyTheme(theme), () -> applyDefaultTheme(installedLookAndFeels));
    }

    public static void applyTheme(String themeName) {
        UIManager.LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();
        System.out.println("Applying theme: " + themeName);
        Arrays.stream(installedLookAndFeels)
                .filter(theme -> theme.getName().equalsIgnoreCase(themeName))
                .findFirst()
                .ifPresent(theme -> {
                    try {
                        UIManager.setLookAndFeel(theme.getClassName());
                    } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                             InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                });
        System.out.println("Applied theme: " + themeName);
    }

    public static void applyTheme(Optional<UIManager.LookAndFeelInfo> gtkPlusThemeOptional) {
        UIManager.LookAndFeelInfo lookAndFeelInfo = gtkPlusThemeOptional.orElseThrow();
        try {
            System.out.println("Applying theme: " + lookAndFeelInfo.getName());
            UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
            System.out.println("Applied theme: " + lookAndFeelInfo.getName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void applyDefaultTheme(UIManager.LookAndFeelInfo[] installedLookAndFeels) {

        Optional<UIManager.LookAndFeelInfo> themeOptional = null;

        themeOptional = findTheme(installedLookAndFeels, THEME_GTK_PLUS);
        if (themeOptional.isPresent()) {
            applyTheme(themeOptional);
            return;
        }

        themeOptional = findTheme(installedLookAndFeels, THEME_NIMBUS);
        if (themeOptional.isPresent()) {
            applyTheme(themeOptional);
            return;
        }

        themeOptional = findTheme(installedLookAndFeels, THEME_METAL_JAVA_IMPROVED);
        if (themeOptional.isPresent()) {
            applyTheme(themeOptional);
            return;
        }

        themeOptional = findTheme(installedLookAndFeels, THEME_CDE_MOTIF_WINDOWS_95);
        if (themeOptional.isPresent()) {
            applyTheme(themeOptional);
            return;
        }

        themeOptional = Arrays.stream(installedLookAndFeels).findFirst();
        if (themeOptional.isPresent()) {
            applyTheme(themeOptional);
        }
    }

    private static Optional<UIManager.LookAndFeelInfo> findTheme(UIManager.LookAndFeelInfo[] installedLookAndFeels, String themeName) {
        return Arrays.stream(installedLookAndFeels).filter(info -> themeName.equalsIgnoreCase(info.getName())).findFirst();
    }

}
