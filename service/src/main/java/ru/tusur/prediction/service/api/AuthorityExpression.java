package ru.tusur.prediction.service.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * Выражения проверки разрешений.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthorityExpression {

    public static final String READ_AUTHORITY = "hasAuthority('READ')";

    public static final String WRITE_AUTHORITY = "hasAuthority('WRITE')";

    public static final String PREDICTION_AUTHORITY = "hasAuthority('PREDICTION')";

}
