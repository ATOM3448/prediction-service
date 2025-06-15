package ru.tusur.prediction.service.core.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static ru.tusur.prediction.service.core.error.ErrorMessages.ACCESS_DENIED_MESSAGE;
import static ru.tusur.prediction.service.core.error.ErrorMessages.DUPLICATE_MESSAGE;
import static ru.tusur.prediction.service.core.error.ErrorMessages.INTERNAL_ERROR_MESSAGE;
import static ru.tusur.prediction.service.core.error.ErrorMessages.INVALID_ARGUMENT_MESSAGE;
import static ru.tusur.prediction.service.core.error.ErrorMessages.OBJECT_NOT_FOUND_MESSAGE;
import static ru.tusur.prediction.service.core.error.ErrorMessages.UNAUTHORIZED_MESSAGE;

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
