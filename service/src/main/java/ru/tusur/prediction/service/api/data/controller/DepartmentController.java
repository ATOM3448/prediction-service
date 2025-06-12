package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.ApiPaths;

/**
 * Контроллер для работы с данными по кафедрам.
 */
@Tag(name = "Работа с данными кафедр")
@RestController
@RequestMapping(ApiPaths.DATA_API_DEPARTMENT)
public class DepartmentController {


}
