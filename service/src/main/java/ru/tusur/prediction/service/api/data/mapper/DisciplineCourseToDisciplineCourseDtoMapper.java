package ru.tusur.prediction.service.api.data.mapper;

import org.mapstruct.Mapper;
import ru.tusur.prediction.service.api.data.dto.discipline.course.DisciplineCourseDto;
import ru.tusur.prediction.service.core.model.discipline.course.DisciplineCourse;

@Mapper(componentModel = "spring")
public interface DisciplineCourseToDisciplineCourseDtoMapper {

    DisciplineCourseDto map(DisciplineCourse disciplineCourse);

}
