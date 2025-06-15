package ru.tusur.prediction.service.api.data.mapper;

import org.mapstruct.Mapper;
import ru.tusur.prediction.service.api.data.dto.faculty.FacultyDto;
import ru.tusur.prediction.service.core.model.faculty.Faculty;

@Mapper(componentModel = "spring")
public interface FacultyToFacultyDtoMapper {

    FacultyDto map(Faculty faculty);

}
