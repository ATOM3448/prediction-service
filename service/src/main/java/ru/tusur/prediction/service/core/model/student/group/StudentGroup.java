package ru.tusur.prediction.service.core.model.student.group;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDate;

public record StudentGroup(
        long id,
        @ColumnName("profile_id") long profileId,
        String name,
        @ColumnName("general_enrollment") LocalDate generalEnrollment,
        @ColumnName("planed_expulsion") LocalDate planedExpulsion
) {
}
