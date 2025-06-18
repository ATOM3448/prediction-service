package ru.tusur.prediction.service.core.service.student.group;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.api.data.dto.student.group.UpdateStudentGroupDto;
import ru.tusur.prediction.service.api.data.dto.student.group.member.CreateStudentGroupMemberDto;
import ru.tusur.prediction.service.api.data.dto.student.group.member.UpdateStudentGroupMemberDto;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ServiceException;
import ru.tusur.prediction.service.core.model.student.group.StudentGroup;
import ru.tusur.prediction.service.core.model.student.group.member.StudentGroupMember;
import ru.tusur.prediction.service.core.repository.StudentGroupRepository;
import ru.tusur.prediction.service.core.service.profile.ProfileService;
import ru.tusur.prediction.service.core.service.student.StudentService;

import java.time.LocalDate;
import java.util.List;

import static ru.tusur.prediction.service.core.error.ErrorMessages.OBJECT_NOT_FOUND_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentGroupService {

    private final ProfileService profileService;

    private final StudentService studentService;

    private final StudentGroupRepository studentGroupRepository;

    public List<StudentGroup> getStudentGroups(long programId, long profileId) {
        profileService.getProfile(programId, profileId);

        return studentGroupRepository.getStudentGroupsByProfileId(profileId);
    }

    public StudentGroup getStudentGroup(long programId, long profileId, long id) {
        profileService.getProfile(programId, profileId);

        StudentGroup studentGroup = studentGroupRepository.getStudentGroupById(id);

        checkExistenceOfStudentGroup(studentGroup, profileId);

        return studentGroup;
    }

    public StudentGroup saveStudentGroup(long programId, long profileId, UpdateStudentGroupDto studentGroup) {
        profileService.getProfile(programId, profileId);

        String name = studentGroup.name();
        LocalDate generalEnrollment = studentGroup.generalEnrollment();
        LocalDate planedExpulsion = studentGroup.planedExpulsion();

        StudentGroup savedStudentGroup = studentGroupRepository.saveStudentGroup(profileId, name, generalEnrollment, planedExpulsion);

        log.info(
                "Данные по студенческой группе #{} сохранены",
                savedStudentGroup.id()
        );

        return savedStudentGroup;
    }

    public StudentGroup updateStudentGroup(
            long programId,
            long profileId,
            long studentGroupId,
            UpdateStudentGroupDto studentGroup
    ) {
        profileService.getProfile(programId, profileId);

        StudentGroup oldStudentGroup = studentGroupRepository.getStudentGroupById(studentGroupId);

        checkExistenceOfStudentGroup(oldStudentGroup, profileId);

        String name = studentGroup.name();
        LocalDate generalEnrollment = studentGroup.generalEnrollment();
        LocalDate planedExpulsion = studentGroup.planedExpulsion();

        StudentGroup savedStudentGroup = studentGroupRepository.updateStudentGroup(
                studentGroupId,
                name,
                generalEnrollment,
                planedExpulsion
        );

        log.info(
                "Данные по студенческой группе #{} обновлены на [\"{}\", {}, {}]",
                savedStudentGroup.id(),
                name,
                generalEnrollment,
                planedExpulsion
        );

        return savedStudentGroup;
    }

    public List<StudentGroupMember> getStudentGroupMembers(long programId, long profileId, long studentGroupId) {
        getStudentGroup(programId, profileId, studentGroupId);

        return studentGroupRepository.getMembersByStudentGroupId(studentGroupId);
    }

    public StudentGroupMember getStudentGroupMember(long programId, long profileId, long studentGroupId, long studentId) {
        getStudentGroup(programId, profileId, studentGroupId);

        StudentGroupMember studentGroupMember = studentGroupRepository.getMemberByStudentGroupIdAndStudentId(
                studentGroupId,
                studentId
        );

        checkExistenceOfStudentGroupMember(studentGroupMember);

        return studentGroupMember;
    }


    public StudentGroupMember saveMember(
            long programId,
            long profileId,
            long studentGroupId,
            CreateStudentGroupMemberDto studentGroupMember
    ) {
        getStudentGroup(programId, profileId, studentGroupId);

        long studentId = studentGroupMember.studentId();

        studentService.getStudent(studentId);

        LocalDate enrollment = studentGroupMember.enrollment();
        LocalDate expulsion = studentGroupMember.expulsion();

        StudentGroupMember savedStudentGroupMember = studentGroupRepository.saveMember(
                studentGroupId,
                studentId,
                enrollment,
                expulsion
        );

        log.info(
                "Данные по члену студенческой группы #{} сохранены",
                studentGroupId
        );

        return savedStudentGroupMember;
    }

    public StudentGroupMember updateStudentGroupMember(
            long programId,
            long profileId,
            long studentGroupId,
            long studentId,
            UpdateStudentGroupMemberDto studentGroupMember
    ) {
        getStudentGroup(programId, profileId, studentGroupId);
        studentService.getStudent(studentId);

        StudentGroupMember oldMember = studentGroupRepository.getMemberByStudentGroupIdAndStudentId(
                studentGroupId,
                studentId
        );

        checkExistenceOfStudentGroupMember(oldMember);

        LocalDate enrollment = studentGroupMember.enrollment();
        LocalDate expulsion = studentGroupMember.expulsion();

        StudentGroupMember savedStudentGroupMember = studentGroupRepository.updateMember(
                studentGroupId,
                studentId,
                enrollment,
                expulsion
        );

        log.info(
                "Данные по студенческой группе #{} обновлены на [{}, {}]",
                studentGroupId,
                enrollment,
                expulsion
        );

        return savedStudentGroupMember;
    }

    private void checkExistenceOfStudentGroup(StudentGroup studentGroup, long profileId) {
        if ((studentGroup == null) || (profileId != studentGroup.profileId())) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }
    }

    private void checkExistenceOfStudentGroupMember(StudentGroupMember member) {
        if (member == null) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }
    }
}
