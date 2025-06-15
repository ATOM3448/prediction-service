package ru.tusur.prediction.service.api.data.mapper;

import org.mapstruct.Mapper;
import ru.tusur.prediction.service.api.data.dto.discipline.DisciplineDto;
import ru.tusur.prediction.service.core.model.discipline.Discipline;

@Mapper(componentModel = "spring")
public interface DisciplineToDisciplineDtoMapper {

    DisciplineDto map(Discipline discipline);

}
