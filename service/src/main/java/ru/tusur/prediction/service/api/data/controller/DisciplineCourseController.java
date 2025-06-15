package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.mapper.DisciplineCourseToDisciplineCourseDtoMapper;
import ru.tusur.prediction.service.core.service.discipline.course.DisciplineCourseService;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_DISCIPLINE_COURSE;

@Tag(name = "Работа с данными курсов дисциплин")
@RestController
@RequestMapping(DATA_API_DISCIPLINE_COURSE)
@Validated
@AllArgsConstructor
public class DisciplineCourseController {

    private final DisciplineCourseService disciplineCourseService;

    private final DisciplineCourseToDisciplineCourseDtoMapper disciplineCourseToDisciplineCourseDtoMapper;

}
