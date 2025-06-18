package ru.tusur.prediction.service.core.model.student.group.member;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDate;

public record StudentGroupMember(
        @ColumnName("student_id") long studentId,
        @ColumnName("student_group_id") long studentGroupId,
        LocalDate enrollment,
        LocalDate expulsion
) {
}
