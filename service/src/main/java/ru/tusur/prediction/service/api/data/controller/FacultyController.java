package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;

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
import ru.tusur.prediction.service.api.data.dto.faculty.FacultyDto;
import ru.tusur.prediction.service.api.data.dto.faculty.UpdateFacultyDto;
import ru.tusur.prediction.service.api.data.mapper.FacultyToFacultyDtoMapper;
import ru.tusur.prediction.service.api.security.annotation.ReadAccess;
import ru.tusur.prediction.service.api.security.annotation.WriteAccess;
import ru.tusur.prediction.service.configuration.openapi.annotation.BadRequestApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.InternalErrorApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.NotFoundApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.OkApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.ResourceCreatedApiResponse;
import ru.tusur.prediction.service.core.service.faculty.FacultyService;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_FACULTY;
import static ru.tusur.prediction.service.api.data.ApiPaths.ID;
import static ru.tusur.prediction.service.util.PageMapperUtils.mapToPage;

@Tag(name = "Работа с данными факультетов")
@RestController
@RequestMapping(DATA_API_FACULTY)
@Validated
@AllArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    private final FacultyToFacultyDtoMapper facultyToFacultyDtoMapper;

    @GetMapping
    @Operation(description = "Возвращает данные по факультетам")
    @OkApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public PageDto<FacultyDto> getAllFaculties(
            @RequestParam(defaultValue = "0")
                    @Min(value = 0, message = "Страница не может иметь значение ниже нуля")
                    int page,
            @RequestParam(defaultValue = "25")
                    @Min(value = 1, message = "Размер страницы не может иметь значение ниже единицы")
                    @Max(value = 50, message = "Размер страницы не может иметь значение выше пятидесяти")
                    int size
    ) {
        return mapToPage(
                facultyService.getFaculties(),
                page,
                size,
                facultyToFacultyDtoMapper::map
        );
    }

    @GetMapping(ID)
    @Operation(description = "Возвращает данные по факультету")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public FacultyDto getFaculty(@PathVariable long id) {
        return facultyToFacultyDtoMapper.map(facultyService.getFaculty(id));
    }

    @PostMapping
    @Operation(description = "Сохраняет информацию по факультету")
    @ResourceCreatedApiResponse
    @BadRequestApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public ResponseEntity<FacultyDto> createFaculty(@Valid @RequestBody UpdateFacultyDto faculty) {
        FacultyDto created = facultyToFacultyDtoMapper.map(facultyService.saveFaculty(faculty));

        return ResponseEntity.created(URI.create(DATA_API_FACULTY + "/" + created.id()))
                .body(created);
    }

    @PutMapping(ID)
    @Operation(description = "Обновить информацию по факультету")
    @OkApiResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public FacultyDto updateFaculty(
            @PathVariable long id,
            @Valid @RequestBody UpdateFacultyDto faculty
    ) {
        return facultyToFacultyDtoMapper.map(facultyService.updateFaculty(id, faculty));
    }
}
