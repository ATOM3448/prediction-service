package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.mapper.StudentGroupToStudentGroupDtoMapper;
import ru.tusur.prediction.service.core.service.student.group.StudentGroupService;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_STUDENT_GROUP;

@Tag(name = "Работа с данными студенческих групп")
@RestController
@RequestMapping(DATA_API_STUDENT_GROUP)
@Validated
@AllArgsConstructor
public class StudentGroupController {

    private final StudentGroupService studentGroupService;

    private final StudentGroupToStudentGroupDtoMapper studentGroupToStudentGroupDtoMapper;

}
