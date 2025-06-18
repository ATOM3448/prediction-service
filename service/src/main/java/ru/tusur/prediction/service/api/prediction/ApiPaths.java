package ru.tusur.prediction.service.api.prediction;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiPaths {

    public static final String ID = "/{id}";

    public static final String PREDICTION_API = "/prediction-api";

    public static final String PREDICTION_API_STUDENT = PREDICTION_API + "/student";

}
