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
import ru.tusur.prediction.service.api.data.dto.discipline.DisciplineDto;
import ru.tusur.prediction.service.api.data.dto.discipline.UpdateDisciplineDto;
import ru.tusur.prediction.service.api.data.mapper.DisciplineToDisciplineDtoMapper;
import ru.tusur.prediction.service.api.security.annotation.ReadAccess;
import ru.tusur.prediction.service.api.security.annotation.WriteAccess;
import ru.tusur.prediction.service.configuration.openapi.annotation.BadRequestApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.InternalErrorApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.NotFoundApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.OkApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.ResourceCreatedApiResponse;
import ru.tusur.prediction.service.core.service.discipline.DisciplineService;

import java.net.URI;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_DISCIPLINE;
import static ru.tusur.prediction.service.api.data.ApiPaths.ID;
import static ru.tusur.prediction.service.util.PageMapperUtils.mapToPage;

@Tag(name = "Работа с данными дисциплин")
@RestController
@RequestMapping(DATA_API_DISCIPLINE)
@Validated
@AllArgsConstructor
public class DisciplineController {

    private final DisciplineService disciplineService;

    private final DisciplineToDisciplineDtoMapper disciplineToDisciplineDtoMapper;

    @GetMapping
    @Operation(description = "Возвращает данные по дисциплине")
    @OkApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public PageDto<DisciplineDto> getAllDisciplines(
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Страница не может иметь значение ниже нуля")
            int page,
            @RequestParam(defaultValue = "25")
            @Min(value = 1, message = "Размер страницы не может иметь значение ниже единицы")
            @Max(value = 50, message = "Размер страницы не может иметь значение выше пятидесяти")
            int size
    ) {
        return mapToPage(
                disciplineService.getDisciplines(),
                page,
                size,
                disciplineToDisciplineDtoMapper::map
        );
    }

    @GetMapping(ID)
    @Operation(description = "Возвращает данные по дисциплине")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public DisciplineDto getDiscipline(@PathVariable long id) {
        return disciplineToDisciplineDtoMapper.map(disciplineService.getDiscipline(id));
    }

    @PostMapping
    @Operation(description = "Сохраняет информацию по дисциплине")
    @ResourceCreatedApiResponse
    @BadRequestApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public ResponseEntity<DisciplineDto> createDiscipline(@Valid @RequestBody UpdateDisciplineDto discipline) {
        DisciplineDto created = disciplineToDisciplineDtoMapper.map(disciplineService.saveDiscipline(discipline));

        return ResponseEntity.created(URI.create(DATA_API_DISCIPLINE + "/" + created.id()))
                .body(created);
    }

    @PutMapping(ID)
    @Operation(description = "Обновить информацию по дисциплине")
    @OkApiResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public DisciplineDto updateDiscipline(
            @PathVariable long id,
            @Valid @RequestBody UpdateDisciplineDto discipline
    ) {
        return disciplineToDisciplineDtoMapper.map(disciplineService.updateDiscipline(id, discipline));
    }
    
}
