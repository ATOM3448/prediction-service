package ru.tusur.prediction.service.api.data.dto.result.filter;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сущность описывающая тип фильтра")
public enum FilterTypeDto {
    INCLUDE_LESS_THAN,
    NOT_INCLUDE_LESS_THAN,
    INCLUDE_EQUALS_TO,
    NOT_INCLUDE_EQUALS_TO,
    INCLUDE_GREATER_THAN,
    NOT_INCLUDE_GREATER_THAN;
}
