package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tusur.prediction.service.api.data.dto.PageDto;
import ru.tusur.prediction.service.api.data.dto.profile.ProfileDto;
import ru.tusur.prediction.service.api.data.dto.profile.CreateProfileDto;
import ru.tusur.prediction.service.api.data.dto.profile.UpdateProfileDto;
import ru.tusur.prediction.service.api.data.mapper.ProfileToProfileDtoMapper;
import ru.tusur.prediction.service.api.security.annotation.ReadAccess;
import ru.tusur.prediction.service.api.security.annotation.WriteAccess;
import ru.tusur.prediction.service.configuration.openapi.annotation.BadRequestApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.InternalErrorApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.NotFoundApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.OkApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.ResourceCreatedApiResponse;
import ru.tusur.prediction.service.core.service.profile.ProfileService;

import java.net.URI;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_PROFILE;
import static ru.tusur.prediction.service.api.data.ApiPaths.ID;
import static ru.tusur.prediction.service.util.PageMapperUtils.mapToPage;

@Tag(name = "Работа с данными образовательных профилей")
@RestController
@RequestMapping(DATA_API_PROFILE)
@Validated
@AllArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    private final ProfileToProfileDtoMapper profileToProfileDtoMapper;

    @GetMapping
    @Operation(description = "Возвращает данные по образовательным профилям")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public PageDto<ProfileDto> getAllProfiles(
            @RequestParam(defaultValue = "0")
                    @Min(value = 0, message = "Страница не может иметь значение ниже нуля")
                    int page,
            @RequestParam(defaultValue = "25")
                    @Min(value = 1, message = "Размер страницы не может иметь значение ниже единицы")
                    @Max(value = 50, message = "Размер страницы не может иметь значение выше пятидесяти")
                    int size,
            @PathVariable
                    long programId
    ) {
        return mapToPage(
                profileService.getProfiles(programId),
                page,
                size,
                profileToProfileDtoMapper::map
        );
    }

    @GetMapping(ID)
    @Operation(description = "Возвращает данные по образовательному профилю")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public ProfileDto getProfile(
            @PathVariable long programId,
            @PathVariable long id
    ) {
        return profileToProfileDtoMapper.map(profileService.getProfile(programId, id));
    }

    @PostMapping
    @Operation(description = "Сохраняет информацию по образовательному профилю")
    @ResourceCreatedApiResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public ResponseEntity<ProfileDto> createProfile(
            @PathVariable long programId,
            @Valid @RequestBody CreateProfileDto profile
    ) {
        ProfileDto created = profileToProfileDtoMapper.map(profileService.saveProfile(programId, profile));

        URI location = UriComponentsBuilder.fromPath(DATA_API_PROFILE)
                .buildAndExpand(programId)
                .toUri()
                .resolve("/" + created.id());

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(ID)
    @Operation(description = "Обновить информацию по образовательному профилю")
    @OkApiResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public ProfileDto updateProfile(
            @PathVariable long programId,
            @PathVariable long id,
            @Valid @RequestBody UpdateProfileDto profile
    ) {
        return profileToProfileDtoMapper.map(profileService.updateProfile(programId, id, profile));
    }

}
