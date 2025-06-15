package ru.tusur.prediction.service.api.data.mapper;

import org.mapstruct.Mapper;
import ru.tusur.prediction.service.api.data.dto.indicator.IndicatorDto;
import ru.tusur.prediction.service.core.model.indicator.Indicator;

@Mapper(componentModel = "spring")
public interface IndicatorToIndicatorDtoMapper {

    IndicatorDto map(Indicator indicator);

}
