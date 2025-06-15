package ru.tusur.prediction.service.api.data.mapper;

import org.mapstruct.Mapper;
import ru.tusur.prediction.service.api.data.dto.result.ResultDto;
import ru.tusur.prediction.service.core.model.result.Result;

@Mapper(componentModel = "spring")
public interface ResultToResultDtoMapper {

    ResultDto map(Result result);

}
