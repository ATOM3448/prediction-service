package ru.tusur.prediction.service.api.data.mapper;

import org.mapstruct.Mapper;
import ru.tusur.prediction.service.api.data.dto.teacher.TeacherDto;
import ru.tusur.prediction.service.core.model.teacher.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherToTeacherDtoMapper {

    TeacherDto map(Teacher teacher);

}
