package ru.tusur.prediction.service.api.data.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.result.ResultDto;

import java.util.List;

/**
 * Контроллер для работы с данными результатов.
 */
@RestController(ApiPaths.DATA_API_RESULT)
@AllArgsConstructor
public class ResultController {

    @GetMapping
    public List<ResultDto> getResult() {
        return null;
    }

    @PostMapping
    public void saveResult() {

    }

    @PutMapping
    public void updateResult() {

    }
    
}
