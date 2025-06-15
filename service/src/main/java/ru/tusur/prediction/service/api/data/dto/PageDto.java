package ru.tusur.prediction.service.api.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Сущность страницы")
public record PageDto<T>(
        @Schema(description = "Содержимое ответа списком", requiredMode = Schema.RequiredMode.REQUIRED)
                List<T> content,
        @Schema(description = "Номер страницы")
                int page,
        @Schema(description = "Размер страницы")
                int size,
        @Schema(description = "Всего элементов")
                int totalElements,
        @Schema(description = "Всего страниц")
                int totalPages
) {}
