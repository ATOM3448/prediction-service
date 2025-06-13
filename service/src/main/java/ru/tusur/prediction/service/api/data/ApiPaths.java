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

    public static final String DATA_API_PROGRAM = DATA_API + "/program";

    public static final String DATA_API_PROFILE = DATA_API + "/profile";

    public static final String DATA_API_DISCIPLINE = DATA_API + "/discipline";

    public static final String DATA_API_TEACHER = DATA_API + "/teacher";

    public static final String DATA_API_STUDENT = DATA_API + "/student";

    public static final String DATA_API_RESULT = DATA_API + "/result";
}
