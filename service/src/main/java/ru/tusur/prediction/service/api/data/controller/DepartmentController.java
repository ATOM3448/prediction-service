package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.mapper.DepartmentToDepartmentDtoMapper;
import ru.tusur.prediction.service.core.service.department.DepartmentService;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_DEPARTMENT;

@Tag(name = "Работа с данными кафедр")
@RestController
@RequestMapping(DATA_API_DEPARTMENT)
@Validated
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    private final DepartmentToDepartmentDtoMapper departmentToDepartmentDtoMapper;

}
