package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tusur.prediction.service.api.data.dto.PageDto;
import ru.tusur.prediction.service.api.data.dto.student.group.StudentGroupDto;
import ru.tusur.prediction.service.api.data.dto.student.group.UpdateStudentGroupDto;
import ru.tusur.prediction.service.api.data.dto.student.group.member.CreateStudentGroupMemberDto;
import ru.tusur.prediction.service.api.data.dto.student.group.member.StudentGroupMemberDto;
import ru.tusur.prediction.service.api.data.dto.student.group.member.UpdateStudentGroupMemberDto;
import ru.tusur.prediction.service.api.data.mapper.StudentGroupToStudentGroupDtoMapper;
import ru.tusur.prediction.service.api.security.annotation.ReadAccess;
import ru.tusur.prediction.service.api.security.annotation.WriteAccess;
import ru.tusur.prediction.service.configuration.openapi.annotation.BadRequestApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.InternalErrorApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.NotFoundApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.OkApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.ResourceCreatedApiResponse;
import ru.tusur.prediction.service.core.service.student.group.StudentGroupService;

import java.net.URI;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_STUDENT_GROUP_MEMBER;
import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_STUDENT_GROUP;
import static ru.tusur.prediction.service.api.data.ApiPaths.ID;
import static ru.tusur.prediction.service.util.PageMapperUtils.mapToPage;

@Tag(name = "Работа с данными студенческих групп")
@RestController
@RequestMapping(DATA_API_STUDENT_GROUP)
@Validated
@AllArgsConstructor
public class StudentGroupController {

    private final StudentGroupService studentGroupService;

    private final StudentGroupToStudentGroupDtoMapper studentGroupToStudentGroupDtoMapper;

    @GetMapping
    @Operation(description = "Возвращает данные по студенческим группам")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public PageDto<StudentGroupDto> getAllStudentGroups(
            @RequestParam(defaultValue = "0")
                    @Min(value = 0, message = "Страница не может иметь значение ниже нуля")
                    int page,
            @RequestParam(defaultValue = "25")
                    @Min(value = 1, message = "Размер страницы не может иметь значение ниже единицы")
                    @Max(value = 50, message = "Размер страницы не может иметь значение выше пятидесяти")
                    int size,
            @PathVariable
                    long programId,
            @PathVariable
                    long profileId
    ) {
        return mapToPage(
                studentGroupService.getStudentGroups(programId, profileId),
                page,
                size,
                studentGroupToStudentGroupDtoMapper::map
        );
    }

    @GetMapping(DATA_API_STUDENT_GROUP_MEMBER)
    @Operation(description = "Возвращает членов студенческой группы")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public PageDto<StudentGroupMemberDto> getAllStudentGroupMembers(
            @RequestParam(defaultValue = "0")
                    @Min(value = 0, message = "Страница не может иметь значение ниже нуля")
                    int page,
            @RequestParam(defaultValue = "25")
                    @Min(value = 1, message = "Размер страницы не может иметь значение ниже единицы")
                    @Max(value = 50, message = "Размер страницы не может иметь значение выше пятидесяти")
                    int size,
            @PathVariable
                    long programId,
            @PathVariable
                    long profileId,
            @PathVariable
                    long studentGroupId
    ) {
        return mapToPage(
                studentGroupService.getStudentGroupMembers(programId, profileId, studentGroupId),
                page,
                size,
                studentGroupToStudentGroupDtoMapper::map
        );
    }

    @GetMapping(ID)
    @Operation(description = "Возвращает данные по студенческой группе")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public StudentGroupDto getStudentGroup(
            @PathVariable long programId,
            @PathVariable long profileId,
            @PathVariable long id
    ) {
        return studentGroupToStudentGroupDtoMapper.map(studentGroupService.getStudentGroup(programId, profileId, id));
    }

    @GetMapping(DATA_API_STUDENT_GROUP_MEMBER + ID)
    @Operation(description = "Возвращает данные по члену студенческой группы")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public StudentGroupMemberDto getStudentGroupMember(
            @PathVariable long programId,
            @PathVariable long profileId,
            @PathVariable long studentGroupId,
            @PathVariable long id
    ) {
        return studentGroupToStudentGroupDtoMapper.map(studentGroupService.getStudentGroupMember(programId, profileId, studentGroupId, id));
    }

    @PostMapping
    @Operation(description = "Сохраняет информацию по студенческой группе")
    @ResourceCreatedApiResponse
    @NotFoundApiResponse
    @BadRequestApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public ResponseEntity<StudentGroupDto> createStudentGroup(
            @PathVariable long programId,
            @PathVariable long profileId,
            @Valid @RequestBody UpdateStudentGroupDto studentGroup
    ) {
        StudentGroupDto created = studentGroupToStudentGroupDtoMapper.map(studentGroupService.saveStudentGroup(
                programId,
                profileId,
                studentGroup
        ));

        URI location = UriComponentsBuilder.fromPath(DATA_API_STUDENT_GROUP)
                .buildAndExpand(programId, profileId)
                .toUri()
                .resolve("/" + created.id());

        return ResponseEntity.created(location).body(created);
    }

    @PostMapping(DATA_API_STUDENT_GROUP_MEMBER)
    @Operation(description = "Сохраняет информацию по члену группе")
    @OkApiResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public ResponseEntity<StudentGroupMemberDto> createStudentGroupMember(
            @PathVariable long programId,
            @PathVariable long profileId,
            @PathVariable long studentGroupId,
            @Valid @RequestBody CreateStudentGroupMemberDto studentGroupMember
    ) {
        StudentGroupMemberDto created = studentGroupToStudentGroupDtoMapper.map(studentGroupService.saveMember(
           programId,
           profileId,
           studentGroupId,
           studentGroupMember
        ));

        URI location = UriComponentsBuilder.fromPath(DATA_API_STUDENT_GROUP + DATA_API_STUDENT_GROUP_MEMBER)
                .buildAndExpand(programId, profileId, studentGroupId)
                .toUri()
                .resolve("/" + created.studentId());

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(ID)
    @Operation(description = "Обновить информацию по студенческой группе")
    @OkApiResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public StudentGroupDto updateStudentGroup(
            @PathVariable long programId,
            @PathVariable long profileId,
            @PathVariable long id,
            @Valid @RequestBody UpdateStudentGroupDto studentGroup
    ) {
        return studentGroupToStudentGroupDtoMapper.map(studentGroupService.updateStudentGroup(
                programId,
                profileId,
                id,
                studentGroup
        ));
    }

    @PutMapping(DATA_API_STUDENT_GROUP_MEMBER + ID)
    @Operation(description = "Обновить информацию по студенческой группе")
    @OkApiResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public StudentGroupMemberDto updateStudentMemberGroup(
            @PathVariable long programId,
            @PathVariable long profileId,
            @PathVariable long studentGroupId,
            @PathVariable long id,
            @Valid @RequestBody UpdateStudentGroupMemberDto studentGroupMember
    ) {
        return studentGroupToStudentGroupDtoMapper.map(studentGroupService.updateStudentGroupMember(
                programId,
                profileId,
                studentGroupId,
                id,
                studentGroupMember
        ));
    }
    
}
