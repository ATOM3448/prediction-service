package ru.tusur.prediction.service.api.prediction.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tusur.prediction.service.api.prediction.dto.result.StudentPredictionResultDto;

@Mapper(componentModel = "spring")
public interface StudentPredictionResultDtoMapper {

    @Mapping(target = "predictionResult", source = "predictionResult")
    StudentPredictionResultDto map(Boolean predictionResult);
}
