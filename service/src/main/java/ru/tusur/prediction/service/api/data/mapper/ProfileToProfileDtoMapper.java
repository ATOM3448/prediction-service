package ru.tusur.prediction.service.api.data.mapper;

import org.mapstruct.Mapper;
import ru.tusur.prediction.service.api.data.dto.profile.ProfileDto;
import ru.tusur.prediction.service.core.model.profile.Profile;

@Mapper(componentModel = "spring")
public interface ProfileToProfileDtoMapper {

    ProfileDto map(Profile profile);

}
