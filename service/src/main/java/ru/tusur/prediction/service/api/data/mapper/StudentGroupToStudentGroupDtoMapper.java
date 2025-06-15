package ru.tusur.prediction.service.api.data.mapper;

import org.mapstruct.Mapper;
import ru.tusur.prediction.service.api.data.dto.student.group.StudentGroupDto;
import ru.tusur.prediction.service.core.model.student.group.StudentGroup;

@Mapper(componentModel = "spring")
public interface StudentGroupToStudentGroupDtoMapper {

    StudentGroupDto map(StudentGroup studentGroup);

}
