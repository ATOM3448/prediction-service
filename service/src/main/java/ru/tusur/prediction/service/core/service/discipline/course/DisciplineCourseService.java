package ru.tusur.prediction.service.core.service.discipline.course;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.api.data.dto.discipline.course.CreateDisciplineCourseDto;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ServiceException;
import ru.tusur.prediction.service.core.model.discipline.course.DisciplineCourse;
import ru.tusur.prediction.service.core.repository.DisciplineCourseRepository;
import ru.tusur.prediction.service.core.service.discipline.DisciplineService;
import ru.tusur.prediction.service.core.service.profile.ProfileService;
import ru.tusur.prediction.service.core.service.teacher.TeacherService;

import java.util.List;

import static ru.tusur.prediction.service.core.error.ErrorMessages.OBJECT_NOT_FOUND_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class DisciplineCourseService {

    private final DisciplineService disciplineService;

    private final TeacherService teacherService;

    private final ProfileService profileService;

    private final DisciplineCourseRepository disciplineCourseRepository;

    public List<DisciplineCourse> getDisciplineCourses(long programId, long profileId) {
        profileService.getProfile(programId, profileId);

        return disciplineCourseRepository.getDisciplineCoursesByProfileId(profileId);
    }

    public DisciplineCourse getDisciplineCourse(long programId, long profileId, long id) {
        profileService.getProfile(programId, profileId);

        DisciplineCourse disciplineCourse = disciplineCourseRepository.getDisciplineCourseById(id);

        checkExistenceOfDisciplineCourse(disciplineCourse, profileId);

        return disciplineCourse;
    }

    public DisciplineCourse getDisciplineCourseByIdNotProtected(long id) {
        return disciplineCourseRepository.getDisciplineCourseById(id);
    }

    public DisciplineCourse saveDisciplineCourse(
            long programId,
            long profileId,
            CreateDisciplineCourseDto disciplineCourse
    ) {
        profileService.getProfile(programId, profileId);

        long disciplineId = disciplineCourse.disciplineId();
        disciplineService.getDiscipline(disciplineId);

        long teacherId = disciplineCourse.teacherId();
        teacherService.getTeacher(teacherId);

        int semester = disciplineCourse.semester();

        DisciplineCourse savedDisciplineCourse = disciplineCourseRepository.saveDisciplineCourse(
                profileId,
                disciplineId,
                teacherId,
                semester
        );

        log.info(
                "Данные по курсу дисциплины #{} сохранены",
                savedDisciplineCourse.id()
        );

        return savedDisciplineCourse;
    }

    private void checkExistenceOfDisciplineCourse(DisciplineCourse disciplineCourse, long profileId) {
        if ((disciplineCourse == null) || (profileId != disciplineCourse.profileId())) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }
    }

}
