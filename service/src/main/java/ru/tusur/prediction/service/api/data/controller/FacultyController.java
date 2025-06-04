package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tusur.prediction.service.TempContext;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.faculty.FacultyDto;
import ru.tusur.prediction.service.api.data.mapper.FacultyToFacultyDtoMapper;
import ru.tusur.prediction.service.core.faculty.FacultyService;
import ru.tusur.prediction.service.core.model.faculty.Faculty;

import java.util.List;

// TODO Аутентификация на всех эндпоинтах
// TODO Подключить свагер
// TODO Логирование
// TODO Заменить использования временного контекста
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
        int organizationId = TempContext.ORGANIZATION_ID;

        List<Faculty> faculties = facultyService.getFacultiesByOrganizationId(organizationId);

        return facultyToFacultyDtoMapper.map(faculties);
    }

    @PostMapping
    public void saveFaculty() {

    }

    @PutMapping
    public void updateFaculty() {

    }

}
