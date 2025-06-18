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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tusur.prediction.service.api.data.dto.PageDto;
import ru.tusur.prediction.service.api.data.dto.result.CreateResultDto;
import ru.tusur.prediction.service.api.data.dto.result.ResultDto;
import ru.tusur.prediction.service.api.data.dto.result.UpdateResultDto;
import ru.tusur.prediction.service.api.data.dto.result.filter.ResultFilterDto;
import ru.tusur.prediction.service.api.data.mapper.ResultToResultDtoMapper;
import ru.tusur.prediction.service.api.security.annotation.ReadAccess;
import ru.tusur.prediction.service.api.security.annotation.WriteAccess;
import ru.tusur.prediction.service.configuration.openapi.annotation.BadRequestApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.InternalErrorApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.NotFoundApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.OkApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.ResourceCreatedApiResponse;
import ru.tusur.prediction.service.core.service.result.ResultService;

import java.net.URI;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_RESULT;
import static ru.tusur.prediction.service.api.data.ApiPaths.ID;
import static ru.tusur.prediction.service.util.PageMapperUtils.mapToPage;

@Tag(name = "Работа с данными результатов")
@RestController
@RequestMapping(DATA_API_RESULT)
@Validated
@AllArgsConstructor
public class ResultController {

    private final ResultService resultService;

    private final ResultToResultDtoMapper resultToResultDtoMapper;

    @GetMapping
    @Operation(description = "Возвращает данные по результатам")
    @OkApiResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public PageDto<ResultDto> getAllResults(
            @RequestParam(defaultValue = "0")
                    @Min(value = 0, message = "Страница не может иметь значение ниже нуля")
                    int page,
            @RequestParam(defaultValue = "25")
                    @Min(value = 1, message = "Размер страницы не может иметь значение ниже единицы")
                    @Max(value = 50, message = "Размер страницы не может иметь значение выше пятидесяти")
                    int size,
            @PathVariable
                    long programId,
            @PathVariable
                    long profileId,
            @PathVariable
                    long disciplineCourseId,
            @ModelAttribute
                    ResultFilterDto filter
    ) {
        return mapToPage(
                resultService.getResults(
                        programId,
                        profileId,
                        disciplineCourseId,
                        filter
                ),
                page,
                size,
                resultToResultDtoMapper::map
        );
    }

    @GetMapping(ID)
    @Operation(description = "Возвращает данные по результату")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public ResultDto getResult(
            @PathVariable long programId,
            @PathVariable long profileId,
            @PathVariable long disciplineCourseId,
            @PathVariable long id
    ) {
        return resultToResultDtoMapper.map(resultService.getResult(programId, profileId, disciplineCourseId, id));
    }

    @PostMapping
    @Operation(description = "Сохраняет информацию по результату")
    @ResourceCreatedApiResponse
    @NotFoundApiResponse
    @BadRequestApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public ResponseEntity<ResultDto> createResult(
            @PathVariable long programId,
            @PathVariable long profileId,
            @PathVariable long disciplineCourseId,
            @Valid @RequestBody CreateResultDto result
    ) {
        ResultDto created = resultToResultDtoMapper.map(resultService.saveResult(
                programId,
                profileId,
                disciplineCourseId,
                result
        ));

        URI location = UriComponentsBuilder.fromPath(DATA_API_RESULT)
                .buildAndExpand(programId, profileId, disciplineCourseId)
                .toUri()
                .resolve("/" + created.id());

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(ID)
    @Operation(description = "Обновить информацию по результату")
    @OkApiResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public ResultDto updateResult(
            @PathVariable long programId,
            @PathVariable long profileId,
            @PathVariable long disciplineCourseId,
            @PathVariable long id,
            @Valid @RequestBody UpdateResultDto result
    ) {
        return resultToResultDtoMapper.map(resultService.updateResult(
                programId,
                profileId,
                disciplineCourseId,
                id,
                result
        ));
    }
    
}
