package ru.tusur.prediction.service.api.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiPaths {

    public static final String ID = "/{id}";

    public static final String DATA_API = "/data-api";

    public static final String DATA_API_STUDENT = DATA_API + "/student";

    public static final String DATA_API_INDICATOR = DATA_API + "/indicator";

    public static final String DATA_API_FACULTY = DATA_API + "/faculty";

    public static final String DATA_API_DEPARTMENT = DATA_API_FACULTY + ID + "/department";

    public static final String DATA_API_TEACHER = DATA_API_DEPARTMENT + ID + "/teacher";

    public static final String DATA_API_PROGRAM = DATA_API + "/program";

    public static final String DATA_API_PROFILE = DATA_API_PROGRAM + ID + "/profile";

    public static final String DATA_API_STUDENT_GROUP = DATA_API_PROFILE + ID + "/student-group";

    public static final String DATA_API_DISCIPLINE = DATA_API + "/discipline";

    public static final String DATA_API_DISCIPLINE_COURSE = DATA_API_DISCIPLINE + ID + "/discipline-course";

    public static final String DATA_API_RESULT = DATA_API_DISCIPLINE_COURSE + ID + "/result";

}
