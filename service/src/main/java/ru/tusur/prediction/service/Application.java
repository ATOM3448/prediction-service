package ru.tusur.prediction.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.tusur.prediction.service.configuration")
public class Application {
    public static void main(String[] args) {
        var app = new SpringApplication(Application.class);
        app.run(args);
    }
}
