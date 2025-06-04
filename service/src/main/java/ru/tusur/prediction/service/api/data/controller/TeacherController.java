package ru.tusur.prediction.service.api.data.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.teacher.TeacherDto;
import ru.tusur.prediction.service.core.teacher.TeacherService;

/**
 * Контроллер для работы с данными преподавателей.
 */
@RestController
@RequestMapping(ApiPaths.DATA_API_TEACHER)
@AllArgsConstructor
public class TeacherController {

  private final TeacherService teacherService;

  @GetMapping
  public List<TeacherDto> getTeacher() {
    return null;
  }

  @PostMapping
  public void saveTeacher() {}

  @PutMapping
  public void updateTeacher() {}
}
