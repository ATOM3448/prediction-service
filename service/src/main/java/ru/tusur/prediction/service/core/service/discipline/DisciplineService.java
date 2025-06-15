package ru.tusur.prediction.service.core.service.discipline;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.api.data.dto.discipline.UpdateDisciplineDto;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ServiceException;
import ru.tusur.prediction.service.core.model.discipline.Discipline;
import ru.tusur.prediction.service.core.repository.DisciplineRepository;

import java.util.List;

import static ru.tusur.prediction.service.core.error.ErrorMessages.OBJECT_NOT_FOUND_MESSAGE;
import static ru.tusur.prediction.service.util.SecurityUtils.getOrganizationIdFromSecurityContext;
import static ru.tusur.prediction.service.util.SecurityUtils.validateAccessByOrganizationId;

@Slf4j
@Service
@RequiredArgsConstructor
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;

    public List<Discipline> getDisciplines() {
        long organizationId = getOrganizationIdFromSecurityContext();

        return disciplineRepository.getDisciplinesByOrganizationId(organizationId);
    }

    public Discipline getDiscipline(long id) {
        Discipline discipline = disciplineRepository.getDisciplineById(id);

        if ((discipline == null) || !validateAccessByOrganizationId(discipline.organizationId())) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }

        return discipline;
    }

    public Discipline saveDiscipline(UpdateDisciplineDto discipline) {
        long organizationId = getOrganizationIdFromSecurityContext();

        String disciplineName = discipline.name();

        Discipline savedDiscipline = disciplineRepository.saveDiscipline(organizationId, disciplineName);

        log.info(
                "Данные по дисциплине #{} сохранены",
                savedDiscipline.id()
        );

        return savedDiscipline;
    }

    public void updateDiscipline(long disciplineId, UpdateDisciplineDto newDiscipline) {
        Discipline oldDiscipline = disciplineRepository.getDisciplineById(disciplineId);

        if ((oldDiscipline == null) || !validateAccessByOrganizationId(oldDiscipline.organizationId())) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }

        String newName = newDiscipline.name();

        if (newName.equals(oldDiscipline.name())) {
            return;
        }

        disciplineRepository.updateDiscipline(disciplineId, newName);

        log.info(
                "Данные по дисциплине #{} обновлены на \"{}\"",
                disciplineId,
                newName
        );
    }
}
