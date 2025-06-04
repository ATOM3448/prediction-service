package ru.tusur.prediction.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Временный контекст для использования нереализованных полей при разработке.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TempContext {

    private static final TempContext MY_INSTANCE = new TempContext();

    public static final int ORGANIZATION_ID = 1;

    public static TempContext getInstance() {
        return MY_INSTANCE;
    }
}
