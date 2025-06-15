package ru.tusur.prediction.service.api.data.mapper;

import org.mapstruct.Mapper;
import ru.tusur.prediction.service.api.data.dto.department.DepartmentDto;
import ru.tusur.prediction.service.core.model.department.Department;

@Mapper(componentModel = "spring")
public interface DepartmentToDepartmentDtoMapper {

    DepartmentDto map(Department indicator);

}
