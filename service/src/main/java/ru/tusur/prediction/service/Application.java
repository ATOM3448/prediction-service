package ru.tusur.prediction.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Входная точка в приложение.
 */
@SpringBootApplication(scanBasePackages = "ru.tusur.prediction.service")
public class Application {
    public static void main(String[] args) {
        var app = new SpringApplication(Application.class);
        app.run(args);
    }
}
