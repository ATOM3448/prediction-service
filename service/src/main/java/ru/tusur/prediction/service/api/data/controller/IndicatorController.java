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
import ru.tusur.prediction.service.api.data.dto.PageDto;
import ru.tusur.prediction.service.api.data.dto.indicator.IndicatorDto;
import ru.tusur.prediction.service.api.data.dto.indicator.UpdateIndicatorDto;
import ru.tusur.prediction.service.api.data.mapper.IndicatorToIndicatorDtoMapper;
import ru.tusur.prediction.service.api.security.annotation.ReadAccess;
import ru.tusur.prediction.service.api.security.annotation.WriteAccess;
import ru.tusur.prediction.service.configuration.openapi.annotation.BadRequestApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.InternalErrorApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.NotFoundApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.OkApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.ResourceCreatedApiResponse;
import ru.tusur.prediction.service.core.service.indicator.IndicatorService;

import java.net.URI;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_INDICATOR;
import static ru.tusur.prediction.service.api.data.ApiPaths.ID;
import static ru.tusur.prediction.service.util.PageMapperUtils.mapToPage;

@Tag(name = "Работы с данными индикаторов")
@RestController
@RequestMapping(DATA_API_INDICATOR)
@Validated
@AllArgsConstructor
public class IndicatorController {

    private final IndicatorService indicatorService;

    private final IndicatorToIndicatorDtoMapper indicatorToIndicatorDtoMapper;

    @GetMapping
    @Operation(description = "Возвращает данные по индикаторам")
    @OkApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public PageDto<IndicatorDto> getIndicators(
            @RequestParam(defaultValue = "0")
                    @Min(value = 0, message = "Страница не может иметь значение ниже нуля")
            int page,
            @RequestParam(defaultValue = "25")
                    @Min(value = 1, message = "Размер страницы не может иметь значение ниже единицы")
                    @Max(value = 50, message = "Размер страницы не может иметь значение выше пятидесяти")
            int size
    ) {
        return mapToPage(
                indicatorService.getIndicators(),
                page,
                size,
                indicatorToIndicatorDtoMapper::map
        );
    }

    @GetMapping(ID)
    @Operation(description = "Возвращает данные по индикатору")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public IndicatorDto getIndicator(@PathVariable long id) {
        return indicatorToIndicatorDtoMapper.map(indicatorService.getIndicator(id));
    }

    @PostMapping
    @Operation(description = "Сохраняет информацию по индикатору")
    @ResourceCreatedApiResponse
    @BadRequestApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public ResponseEntity<IndicatorDto> createIndicator(@Valid @RequestBody UpdateIndicatorDto indicator) {
        IndicatorDto created = indicatorToIndicatorDtoMapper.map(indicatorService.saveIndicator(indicator));

        return ResponseEntity.created(URI.create(DATA_API_INDICATOR + "/" + created.id()))
                .body(created);
    }

    @PutMapping(ID)
    @Operation(description = "Обновить информацию по индикатору")
    @OkApiResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public void updateIndicator(
            @PathVariable long id,
            @Valid @RequestBody UpdateIndicatorDto indicator
    ) {
        indicatorService.updateIndicator(id, indicator);
    }

}
