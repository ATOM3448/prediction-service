package ru.tusur.prediction.service.api.data.mapper;

import org.mapstruct.Mapper;
import ru.tusur.prediction.service.api.data.dto.student.StudentDto;
import ru.tusur.prediction.service.core.model.student.Student;

@Mapper(componentModel = "spring")
public interface StudentToStudentDtoMapper {

    StudentDto map(Student student);

}
