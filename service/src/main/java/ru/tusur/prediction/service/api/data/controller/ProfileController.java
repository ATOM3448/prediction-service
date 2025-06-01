package ru.tusur.prediction.service.api.data.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.profile.ProfileDto;

import java.util.List;

/**
 * Контроллер для работы с данными профилями подготовки.
 */
@RestController(ApiPaths.DATA_API_PROFILE)
@AllArgsConstructor
public class ProfileController {

    @GetMapping
    public List<ProfileDto> getProfile() {
        return null;
    }

    @PostMapping
    public void saveProfile() {

    }

    @PutMapping
    public void updateProfile() {

    }

}
