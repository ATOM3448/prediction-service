package ru.tusur.prediction.service.core.service.discipline.course;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.core.repository.DisciplineCourseRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class DisciplineCourseService {

    private final DisciplineCourseRepository disciplineCourseRepository;

}
