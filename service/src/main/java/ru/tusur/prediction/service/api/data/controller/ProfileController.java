package ru.tusur.prediction.service.api.data.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.profile.ProfileDto;
import ru.tusur.prediction.service.core.profile.ProfileService;

import java.util.List;

/**
 * Контроллер для работы с данными профилями подготовки.
 */
@RestController
@RequestMapping(ApiPaths.DATA_API_PROFILE)
@AllArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

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
