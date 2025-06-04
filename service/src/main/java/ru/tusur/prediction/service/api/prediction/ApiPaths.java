package ru.tusur.prediction.service.api.prediction;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * URL-адреса для работы с моделью напрямую.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiPaths {

    public static final String PREDICTION_API = "/prediction-api";

    public static final String PREDICTION_API_STUDENT = PREDICTION_API + "/student";
}
