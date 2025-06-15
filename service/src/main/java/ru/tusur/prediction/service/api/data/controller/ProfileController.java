package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.mapper.ProfileToProfileDtoMapper;
import ru.tusur.prediction.service.core.service.profile.ProfileService;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_PROFILE;

@Tag(name = "Работа с данными образовательных профилей")
@RestController
@RequestMapping(DATA_API_PROFILE)
@Validated
@AllArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    private final ProfileToProfileDtoMapper profileToProfileDtoMapper;

}
