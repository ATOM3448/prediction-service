package ru.tusur.prediction.service.api.data.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.program.ProgramDto;
import ru.tusur.prediction.service.core.program.ProgramService;

import java.util.List;

/**
 * Контроллер для работы с данными программ подготовки.
 */
@RestController(ApiPaths.DATA_API_PROGRAM)
@AllArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @GetMapping
    public List<ProgramDto> getProgram() {
        return null;
    }

    @PostMapping
    public void saveProgram() {

    }

    @PutMapping
    public void updateProgram() {

    }
    
}
