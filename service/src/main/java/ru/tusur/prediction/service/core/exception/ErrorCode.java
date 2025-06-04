package ru.tusur.prediction.service.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Перечисление возможных кодов ошибки.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INTERNAL_ERROR(1);

    private final int code;
}
