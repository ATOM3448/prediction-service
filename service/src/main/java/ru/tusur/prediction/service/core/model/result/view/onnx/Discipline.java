package ru.tusur.prediction.service.core.model.result.view.onnx;

import ru.tusur.prediction.service.core.model.indicator.IndicatorType;

import java.util.Map;

public record Discipline(
        long id,
        Map<IndicatorType, Double> results,
        int semester
) {
}
