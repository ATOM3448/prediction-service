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
import org.springframework.web.util.UriComponentsBuilder;
import ru.tusur.prediction.service.api.data.dto.PageDto;
import ru.tusur.prediction.service.api.data.dto.department.DepartmentDto;
import ru.tusur.prediction.service.api.data.dto.department.UpdateDepartmentDto;
import ru.tusur.prediction.service.api.data.mapper.DepartmentToDepartmentDtoMapper;
import ru.tusur.prediction.service.api.security.annotation.ReadAccess;
import ru.tusur.prediction.service.api.security.annotation.WriteAccess;
import ru.tusur.prediction.service.configuration.openapi.annotation.BadRequestApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.InternalErrorApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.NotFoundApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.OkApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.ResourceCreatedApiResponse;
import ru.tusur.prediction.service.core.service.department.DepartmentService;

import java.net.URI;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_DEPARTMENT;
import static ru.tusur.prediction.service.api.data.ApiPaths.ID;
import static ru.tusur.prediction.service.util.PageMapperUtils.mapToPage;

@Tag(name = "Работа с данными кафедр")
@RestController
@RequestMapping(DATA_API_DEPARTMENT)
@Validated
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    private final DepartmentToDepartmentDtoMapper departmentToDepartmentDtoMapper;

    @GetMapping
    @Operation(description = "Возвращает данные по кафедрам")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public PageDto<DepartmentDto> getAllDepartments(
            @RequestParam(defaultValue = "0")
                @Min(value = 0, message = "Страница не может иметь значение ниже нуля")
                int page,
            @RequestParam(defaultValue = "25")
                @Min(value = 1, message = "Размер страницы не может иметь значение ниже единицы")
                @Max(value = 50, message = "Размер страницы не может иметь значение выше пятидесяти")
                int size,
            @PathVariable
                long facultyId
    ) {
        return mapToPage(
                departmentService.getDepartments(facultyId),
                page,
                size,
                departmentToDepartmentDtoMapper::map
        );
    }

    @GetMapping(ID)
    @Operation(description = "Возвращает данные по кафедре")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public DepartmentDto getDepartment(
            @PathVariable long facultyId,
            @PathVariable long id
    ) {
        return departmentToDepartmentDtoMapper.map(departmentService.getDepartment(facultyId, id));
    }

    @PostMapping
    @Operation(description = "Сохраняет информацию по кафедре")
    @ResourceCreatedApiResponse
    @NotFoundApiResponse
    @BadRequestApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public ResponseEntity<DepartmentDto> createDepartment(
            @PathVariable long facultyId,
            @Valid @RequestBody UpdateDepartmentDto department
    ) {
        DepartmentDto created = departmentToDepartmentDtoMapper.map(departmentService.saveDepartment(facultyId, department));

        URI location = UriComponentsBuilder.fromPath(DATA_API_DEPARTMENT)
                .buildAndExpand(facultyId)
                .toUri()
                .resolve("/" + created.id());

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(ID)
    @Operation(description = "Обновить информацию по кафедре")
    @OkApiResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public DepartmentDto updateDepartment(
            @PathVariable long facultyId,
            @PathVariable long id,
            @Valid @RequestBody UpdateDepartmentDto department
    ) {
        return departmentToDepartmentDtoMapper.map(departmentService.updateDepartment(facultyId, id, department));
    }

}
