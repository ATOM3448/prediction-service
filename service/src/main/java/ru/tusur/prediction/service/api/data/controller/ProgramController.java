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
import ru.tusur.prediction.service.api.data.dto.program.ProgramDto;
import ru.tusur.prediction.service.api.data.dto.program.UpdateProgramDto;
import ru.tusur.prediction.service.api.data.mapper.ProgramToProgramDtoMapper;
import ru.tusur.prediction.service.api.security.annotation.ReadAccess;
import ru.tusur.prediction.service.api.security.annotation.WriteAccess;
import ru.tusur.prediction.service.configuration.openapi.annotation.BadRequestApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.InternalErrorApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.NotFoundApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.OkApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.ResourceCreatedApiResponse;
import ru.tusur.prediction.service.core.service.program.ProgramService;

import java.net.URI;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_FACULTY;
import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_PROGRAM;
import static ru.tusur.prediction.service.api.data.ApiPaths.ID;
import static ru.tusur.prediction.service.util.PageMapperUtils.mapToPage;

@Tag(name = "Работа с данными образовательных программ")
@RestController
@RequestMapping(DATA_API_PROGRAM)
@Validated
@AllArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    private final ProgramToProgramDtoMapper programToProgramDtoMapper;

    @GetMapping
    @Operation(description = "Возвращает данные по образовательным программам")
    @OkApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public PageDto<ProgramDto> getAllPrograms(
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Страница не может иметь значение ниже нуля")
            int page,
            @RequestParam(defaultValue = "25")
            @Min(value = 1, message = "Размер страницы не может иметь значение ниже единицы")
            @Max(value = 50, message = "Размер страницы не может иметь значение выше пятидесяти")
            int size
    ) {
        return mapToPage(
                programService.getPrograms(),
                page,
                size,
                programToProgramDtoMapper::map
        );
    }

    @GetMapping(ID)
    @Operation(description = "Возвращает данные по образовательной программе")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public ProgramDto getProgram(@PathVariable long id) {
        return programToProgramDtoMapper.map(programService.getProgram(id));
    }

    @PostMapping
    @Operation(description = "Сохраняет информацию по образовательной программе")
    @ResourceCreatedApiResponse
    @BadRequestApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public ResponseEntity<ProgramDto> createProgram(@Valid @RequestBody UpdateProgramDto program) {
        ProgramDto created = programToProgramDtoMapper.map(programService.saveProgram(program));

        return ResponseEntity.created(URI.create(DATA_API_FACULTY + "/" + created.id()))
                .body(created);
    }

    @PutMapping(ID)
    @Operation(description = "Обновить информацию по образовательной программе")
    @OkApiResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public ProgramDto updateProgram(
            @PathVariable long id,
            @Valid @RequestBody UpdateProgramDto program
    ) {
        return programToProgramDtoMapper.map(programService.updateProgram(id, program));
    }

}
