package ru.tusur.prediction.service.core.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessages {

    public static final String INTERNAL_ERROR_MESSAGE = "Внутрення ошибка";

    public static final String INVALID_ARGUMENT_MESSAGE =
            "Для параметров заданы некорректные значения";

    public static final String OBJECT_NOT_FOUND_MESSAGE = "Объект не найден";

    public static final String UNAUTHORIZED_MESSAGE = "Ошибка авторизации";

    public static final String ACCESS_DENIED_MESSAGE = "Доступ запрещен";

    public static final String DUPLICATE_MESSAGE = "Дубликат данных";
}
