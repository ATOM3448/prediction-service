package ru.tusur.prediction.service.core.service.program;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.api.data.dto.program.UpdateProgramDto;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ServiceException;
import ru.tusur.prediction.service.core.model.program.Program;
import ru.tusur.prediction.service.core.repository.ProgramRepository;

import java.util.List;

import static ru.tusur.prediction.service.core.error.ErrorMessages.OBJECT_NOT_FOUND_MESSAGE;
import static ru.tusur.prediction.service.util.SecurityUtils.getOrganizationIdFromSecurityContext;
import static ru.tusur.prediction.service.util.SecurityUtils.validateAccessByOrganizationId;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;

    public List<Program> getPrograms() {
        long organizationId = getOrganizationIdFromSecurityContext();

        return programRepository.getProgramsByOrganizationId(organizationId);
    }

    public Program getProgram(long id) {
        Program program = programRepository.getProgramById(id);

        checkExistenceOfProgram(program);

        return program;
    }

    public Program saveProgram(UpdateProgramDto program) {
        long organizationId = getOrganizationIdFromSecurityContext();

        String programCode = program.code();
        String programName = program.name();

        Program savedProgram = programRepository.saveProgram(organizationId, programCode, programName);

        log.info(
                "Данные по программе #{} сохранены",
                savedProgram.id()
        );

        return savedProgram;
    }

    public Program updateProgram(long programId, UpdateProgramDto newProgram) {
        Program oldProgram = programRepository.getProgramById(programId);

        checkExistenceOfProgram(oldProgram);

        String newCode = newProgram.code();
        String newName = newProgram.name();

        Program updatedProgram =  programRepository.updateProgram(programId, newCode, newName);

        log.info(
                "Данные по программе #{} обновлены на \"{}\"",
                programId,
                newName
        );

        return updatedProgram;
    }

    private void checkExistenceOfProgram(Program program) {
        if ((program == null) || !validateAccessByOrganizationId(program.organizationId())) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }
    }

}
