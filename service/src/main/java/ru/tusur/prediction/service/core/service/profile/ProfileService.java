package ru.tusur.prediction.service.core.service.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.api.data.dto.profile.CreateProfileDto;
import ru.tusur.prediction.service.api.data.dto.profile.UpdateProfileDto;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ServiceException;
import ru.tusur.prediction.service.core.model.profile.Profile;
import ru.tusur.prediction.service.core.repository.ProfileRepository;
import ru.tusur.prediction.service.core.service.department.DepartmentService;
import ru.tusur.prediction.service.core.service.program.ProgramService;

import java.util.List;

import static ru.tusur.prediction.service.core.error.ErrorMessages.OBJECT_NOT_FOUND_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProgramService programService;

    private final DepartmentService departmentService;

    private final ProfileRepository profileRepository;

    public List<Profile> getProfiles(long programId) {
        programService.getProgram(programId);

        return profileRepository.getProfilesByProgramId(programId);
    }

    public Profile getProfile(long programId, long id) {
        programService.getProgram(programId);

        Profile profile = profileRepository.getProfileById(id);

        checkExistenceOfProfile(profile, programId);

        return profile;
    }

    public Profile saveProfile(long programId, CreateProfileDto profile) {
        programService.getProgram(programId);

        long departmentId = profile.departmentId();
        departmentService.checkExistenceOfDepartment(departmentId);

        String name = profile.name();

        Profile savedProfile = profileRepository.saveProfile(programId, departmentId, name);

        log.info(
                "Данные по образовательному профилю #{} сохранены",
                savedProfile.id()
        );

        return savedProfile;
    }

    public Profile updateProfile(long programId, long profileId, UpdateProfileDto profile) {
        programService.getProgram(programId);

        Profile oldProfile = profileRepository.getProfileById(profileId);

        checkExistenceOfProfile(oldProfile, programId);

        String name = profile.name();

        Profile savedProfile = profileRepository.updateProfile(profileId, name);

        log.info(
                "Данные по образовательному профилю #{} обновлены на \"{}\"",
                savedProfile.id(),
                name
        );

        return savedProfile;
    }

    private void checkExistenceOfProfile(Profile profile, long programId) {
        if ((profile == null) || (programId != profile.programId())) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }
    }

}
