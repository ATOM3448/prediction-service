package ru.tusur.prediction.service.api.prediction.mapper;

import org.mapstruct.Mapper;
import ru.tusur.prediction.service.api.prediction.dto.result.StudentPredictionResultDto;

/**
 * Маппер для конвертации в {@link StudentPredictionResultDto}
 */
@Mapper(componentModel = "spring")
public interface StudentPredictionResultDtoMapper {

    /**
     * Обертка {@link Boolean} в {@link StudentPredictionResultDto}
     *
     * @param predictionResult результат предсказания модели.
     * @return {@link StudentPredictionResultDto} с результатом предсказания.
     */
    StudentPredictionResultDto map(Boolean predictionResult);
}
