package ru.tusur.prediction.service.core.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Перечисление возможных кодов ошибки.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INTERNAL_ERROR(1),

    INVALID_ARGUMENT(2),

    OBJECT_NOT_FOUND(3),

    UNAUTHORIZED(4),

    ACCESS_DENIED(5),

    DUPLICATE(601);

    private final int code;
}
