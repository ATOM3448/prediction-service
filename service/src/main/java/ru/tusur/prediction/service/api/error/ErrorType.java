package ru.tusur.prediction.service.api.error;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    ERROR("error");

    @JsonValue private final String value;
}
