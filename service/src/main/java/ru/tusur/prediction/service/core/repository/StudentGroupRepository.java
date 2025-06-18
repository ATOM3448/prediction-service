package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.student.group.StudentGroup;
import ru.tusur.prediction.service.core.model.student.group.member.StudentGroupMember;

import java.time.LocalDate;
import java.util.List;

@Transactional
@RegisterConstructorMapper(StudentGroup.class)
@RegisterConstructorMapper(StudentGroupMember.class)
public interface StudentGroupRepository {

    @SqlQuery(
            """
            select *
            from student_group
            where profile_id = :profileId
            order by id;
            """
    )
    List<StudentGroup> getStudentGroupsByProfileId(@Bind("profileId") long profileId);

    @SqlQuery(
            """
            select *
            from student_group
            where id = :id;
            """
    )
    StudentGroup getStudentGroupById(@Bind("id") long id);

    @SqlQuery(
            """
            insert into student_group (profile_id, name, general_enrollment, planed_expulsion)
            values (:profileId, :name, :generalEnrollment, :planedExpulsion)
            returning *;
            """
    )
    StudentGroup saveStudentGroup(
            @Bind("profileId") long profileId,
            @Bind("name") String name,
            @Bind("generalEnrollment") LocalDate generalEnrollment,
            @Bind("planedExpulsion") LocalDate planedExpulsion
    );

    @SqlQuery(
            """
            update student_group
            set name = :name,
                general_enrollment = :generalEnrollment,
                planed_expulsion = :planedExpulsion
            where id = :id
            returning *;
            """
    )
    StudentGroup updateStudentGroup(
            @Bind("id") long id,
            @Bind("name") String name,
            @Bind("generalEnrollment") LocalDate generalEnrollment,
            @Bind("planedExpulsion") LocalDate planedExpulsion
    );

    @SqlQuery(
            """
            select *
            from student_group_member
            where student_group_id = :studentGroupId
            order by student_id;
            """
    )
    List<StudentGroupMember> getMembersByStudentGroupId(@Bind("studentGroupId") long studentGroupId);

    @SqlQuery(
            """
            select *
            from student_group_member
            where student_group_id = :studentGroupId
                and student_id = :studentId;
            """
    )
    StudentGroupMember getMemberByStudentGroupIdAndStudentId(
            @Bind("studentGroupId") long studentGroupId,
            @Bind("studentId") long studentId
    );

    @SqlQuery(
            """
            insert into student_group_member (student_group_id, student_id, enrollment, expulsion)
            values (:studentGroupId, :studentId, :enrollment, :expulsion)
            returning *;
            """
    )
    StudentGroupMember saveMember(
            @Bind("studentGroupId") long studentGroupId,
            @Bind("studentId") long studentId,
            @Bind("enrollment") LocalDate enrollment,
            @Bind("expulsion") LocalDate expulsion
    );

    @SqlQuery(
            """
            update student_group_member
            set enrollment = :enrollment,
                expulsion = :expulsion
            where student_group_id = :studentGroupId
                and student_id = :studentId
            returning *;
            """
    )
    StudentGroupMember updateMember(
            @Bind("studentGroupId") long studentGroupId,
            @Bind("studentId") long studentId,
            @Bind("enrollment") LocalDate enrollment,
            @Bind("expulsion") LocalDate expulsion
    );
}
