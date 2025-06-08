package ru.tusur.prediction.service.core.error;

import static ru.tusur.prediction.service.core.error.ErrorMessages.*;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Сервис для работы с сообщениями об ошибках.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessageResolver {

    public static String getErrorMessage(ErrorCode errorCode) {
        return switch (errorCode) {
            case INTERNAL_ERROR -> INTERNAL_ERROR_MESSAGE;

            case INVALID_ARGUMENT -> INVALID_ARGUMENT_MESSAGE;

            case OBJECT_NOT_FOUND -> OBJECT_NOT_FOUND_MESSAGE;

            case UNAUTHORIZED -> UNAUTHORIZED_MESSAGE;

            case ACCESS_DENIED -> ACCESS_DENIED_MESSAGE;

            case DUPLICATE -> DUPLICATE_MESSAGE;
        };
    }
}
