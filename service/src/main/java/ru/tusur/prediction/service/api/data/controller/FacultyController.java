package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.faculty.FacultyDto;
import ru.tusur.prediction.service.api.data.dto.faculty.FacultyUpdateDto;
import ru.tusur.prediction.service.api.data.mapper.FacultyToFacultyDtoMapper;
import ru.tusur.prediction.service.core.service.faculty.FacultyService;

import static ru.tusur.prediction.service.api.AuthorityExpression.READ_AUTHORITY;
import static ru.tusur.prediction.service.api.AuthorityExpression.WRITE_AUTHORITY;

/**
 * Контроллер для работы с данными факультетов.
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
    @PreAuthorize(READ_AUTHORITY)
    public List<FacultyDto> getFaculty() {
        return facultyToFacultyDtoMapper.map(facultyService.getFaculties());
    }

    @PostMapping
    @Operation(description = "Сохраняет информацию по факультету организации клиента")
    @PreAuthorize(WRITE_AUTHORITY)
    public void saveFaculty(@RequestBody FacultyDto faculty) {
        facultyService.saveFaculty(faculty.name());
    }

    @PatchMapping
    @Operation(description = "Обновить информацию по факультету")
    @PreAuthorize(WRITE_AUTHORITY)
    public void updateFaculty(@RequestBody FacultyUpdateDto facultyUpdate) {
        facultyService.updateFaculty(facultyUpdate.oldFaculty().name(), facultyUpdate.newFaculty().name());
    }
}
