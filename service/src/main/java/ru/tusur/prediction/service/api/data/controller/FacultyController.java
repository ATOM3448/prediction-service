package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.faculty.FacultyDto;
import ru.tusur.prediction.service.api.data.mapper.FacultyToFacultyDtoMapper;
import ru.tusur.prediction.service.core.faculty.FacultyService;

// TODO Аутентификация на всех эндпоинтах
// TODO Подключить свагер
// TODO Логирование
/**
 * Контроллер для работы с данными факультетов
 */
@Tag(name = "Работа с данными факультетов")
@RestController
@RequestMapping(ApiPaths.DATA_API_FACULTY)
@Validated
@AllArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    private final FacultyToFacultyDtoMapper facultyToFacultyDtoMapper;

    @GetMapping
    @Operation(description = "Возвращает список факультетов в организации клиента")
    public List<FacultyDto> getFaculty() {
        return facultyToFacultyDtoMapper.map(facultyService.getFaculties());
    }

    @PostMapping
    public void saveFaculty(@RequestBody FacultyDto faculty) {
        facultyService.saveFaculty(faculty.name());
    }
}
