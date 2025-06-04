package ru.tusur.prediction.service.api.data.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.program.ProgramDto;
import ru.tusur.prediction.service.core.program.ProgramService;

/**
 * Контроллер для работы с данными программ подготовки.
 */
@RestController
@RequestMapping(ApiPaths.DATA_API_PROGRAM)
@AllArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @GetMapping
    public List<ProgramDto> getProgram() {
        return null;
    }

    @PostMapping
    public void saveProgram() {}

    @PutMapping
    public void updateProgram() {}
}
