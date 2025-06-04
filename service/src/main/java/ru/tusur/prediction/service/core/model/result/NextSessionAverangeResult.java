package ru.tusur.prediction.service.core.model.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

/**
 * Класс описывающий данные по среднему баллу ближайшей сессии.
 */
@Data
@Builder
@AllArgsConstructor
@Jacksonized
public class NextSessionAverangeResult {}
