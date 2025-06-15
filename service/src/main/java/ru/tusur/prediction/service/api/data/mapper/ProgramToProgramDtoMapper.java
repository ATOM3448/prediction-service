package ru.tusur.prediction.service.api.data.mapper;

import org.mapstruct.Mapper;
import ru.tusur.prediction.service.api.data.dto.program.ProgramDto;
import ru.tusur.prediction.service.core.model.program.Program;

@Mapper(componentModel = "spring")
public interface ProgramToProgramDtoMapper {

    ProgramDto map(Program program);

}
