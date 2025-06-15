package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.mapper.ResultToResultDtoMapper;
import ru.tusur.prediction.service.core.service.result.ResultService;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_RESULT;

@Tag(name = "Работа с данными результатов")
@RestController
@RequestMapping(DATA_API_RESULT)
@Validated
@AllArgsConstructor
public class ResultController {

    private final ResultService resultService;

    private final ResultToResultDtoMapper resultToResultDtoMapper;

}
