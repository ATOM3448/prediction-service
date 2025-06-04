package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.teacher.Teacher;

/**
 * Интерфейс для управления объектами {@link Teacher}.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(Teacher.class)
public interface TeacherRepository {}
