package ru.tusur.prediction.service.api.data.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.result.ResultDto;
import ru.tusur.prediction.service.core.result.ResultService;

/**
 * Контроллер для работы с данными результатов.
 */
@RestController
@RequestMapping(ApiPaths.DATA_API_RESULT)
@AllArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping
    public List<ResultDto> getResult() {
        return null;
    }

    @PostMapping
    public void saveResult() {}

    @PutMapping
    public void updateResult() {}
}
