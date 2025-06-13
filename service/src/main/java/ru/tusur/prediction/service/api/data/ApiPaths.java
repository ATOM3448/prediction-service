package ru.tusur.prediction.service.api.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * URL-адреса для запросов работы с данными.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiPaths {

    public static final String DATA_API = "/data-api";

    public static final String DATA_API_FACULTY = DATA_API + "/faculty";

    public static final String DATA_API_DEPARTMENT = DATA_API + "/department";

}
