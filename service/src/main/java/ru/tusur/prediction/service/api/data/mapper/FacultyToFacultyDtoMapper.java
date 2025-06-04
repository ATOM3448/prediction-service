package ru.tusur.prediction.service.api.data.mapper;

import org.mapstruct.Mapper;
import ru.tusur.prediction.service.api.data.dto.faculty.FacultyDto;
import ru.tusur.prediction.service.core.model.faculty.Faculty;

import java.util.List;

/**
 * Маппер для конвертации в {@link FacultyDto}.
 */
@Mapper(componentModel = "spring")
public interface FacultyToFacultyDtoMapper {

    /**
     * Конвертация {@link Faculty} в {@link FacultyDto}.
     */
    FacultyDto map(Faculty faculty);

    /**
     * Конвертация списка {@link Faculty} в список {@link FacultyDto}
     */
    List<FacultyDto> map(List<Faculty> faculties);
}
