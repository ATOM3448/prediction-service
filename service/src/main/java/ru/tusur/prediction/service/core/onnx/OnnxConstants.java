package ru.tusur.prediction.service.core.onnx;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Список констант, необходимых при работе с моделью
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OnnxConstants {

    public static final int FEATURE_SIZE = 9;

    public static final int MAX_SEMESTERS_COUNT_SUPPORTED = 8;

    public static final int MAX_DISCIPLINES_COUNT_SUPPORTED = 8;

    public static final int SEQUENCE_LENGTH =
            MAX_SEMESTERS_COUNT_SUPPORTED * MAX_DISCIPLINES_COUNT_SUPPORTED;
}
