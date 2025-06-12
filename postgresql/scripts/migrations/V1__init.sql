create table if not exists organization
(
    id                          bigserial           primary key,
    name                        varchar(128)        not null
);

comment on table organization is 'Информация по подключенной организации';
comment on column organization.id is 'Идентификатор';
comment on column organization.name is 'Наименование';

create table if not exists student
(
    id                          bigserial           primary key,
    organization_id             bigint              not null,
    local_id                    varchar(32)         not null,

    constraint student_organization_fk foreign key (organization_id) references organization (id),

    constraint student_organization_id_local_id_combination unique (organization_id, local_id)
);

create index if not exists student_local_id_idx on student(local_id);
create index if not exists student_organization_id_idx on student(organization_id);

comment on table student is 'Студент';
comment on column student.id is 'Идентификатор';
comment on column student.organization_id is 'Идентификатор организации';
comment on column student.local_id is 'Идентификатор, используемый на стороне клиента';

create table if not exists api_key
(
    id                          bigserial           primary key,
    prefix                      char(8)             not null,
    hash                        char(60)            not null,
    organization_id             bigint              not null,
    expired                     date                not null,

    constraint api_key_organization_fk foreign key (organization_id) references organization (id),

    constraint value_unique_conditional unique (hash)
);

create index if not exists api_key_prefix_idx on api_key(prefix);

comment on table api_key is 'Ключ доступа к API';
comment on column api_key.id is 'Идентификатор';
comment on column api_key.prefix is 'Значение, по которому идет поиск записи';
comment on column api_key.hash is 'Хэш';
comment on column api_key.organization_id is 'Идентификатор организации - владельца';
comment on column api_key.expired is 'Дата протухания';

create type scope_type as enum (
    'READ',
    'WRITE',
    'PREDICTION'
);

comment on type scope_type is 'Список возможных разрешений';

create table if not exists scope
(
    api_key_id                  bigint,
    type                        scope_type,

    constraint pk_scope primary key (api_key_id, type),

    constraint scope_api_key_fk foreign key (api_key_id) references api_key (id)
);

comment on table scope is 'Разрешение';
comment on column scope.api_key_id is 'Идентификатор API ключа';
comment on column scope.type is 'Тип';

create table if not exists faculty
(
    id                          bigserial           primary key,
    organization_id             bigint              not null,
    name                        varchar(128)        not null,

    constraint faculty_organization_fk foreign key (organization_id) references organization (id),

    constraint faculty_organization_id_name_combination unique (organization_id, name)
);

create index if not exists faculty_organization_id_idx on faculty(organization_id);
create index if not exists faculty_name_idx on faculty(name);

comment on table faculty is 'Факультет';
comment on column faculty.organization_id is 'Идентификатор организации';
comment on column faculty.name is 'Наименование';

create table if not exists department
(
    id                          bigserial           primary key,
    faculty_id                  bigint              not null,
    name                        varchar(128)        not null,

    constraint department_faculty_fk foreign key (faculty_id) references faculty (id),

    constraint department_faculty_id_name_combination unique (faculty_id, name)
);

create index if not exists department_faculty_id_idx on department(faculty_id);
create index if not exists department_name_idx on department(name);

comment on table department is 'Кафедра';
comment on column department.faculty_id is 'Идентификатор факультета';
comment on column department.name is 'Наименование';

create table if not exists student_group
(
    id                          bigserial           primary key,
    department_id               bigint              not null,
    name                        varchar(32)         not null,
    general_enrollment          date                not null,
    planed_expulsion            date                not null,

    constraint student_group_department_fk foreign key (department_id) references department (id),

    constraint student_group_department_id_name_combination unique (department_id, name)
);

create index if not exists student_group_department_id_idx on student_group(department_id);
create index if not exists student_group_name_idx on student_group(name);

comment on table student_group is 'Группа студентов';
comment on column student_group.id is 'Идентификатор';
comment on column student_group.department_id is 'Идентификатор кафедры';
comment on column student_group.name is 'Наиименование';
comment on column student_group.general_enrollment is 'Общая дата зачисления';
comment on column student_group.planed_expulsion is 'Планируемая дата выпуска';

create table if not exists student_group_member
(
    student_id                  bigint,
    student_group_id            bigint,
    enrollment                  date                not null,
    expulsion                   date,

    constraint pk_student_group_member primary key (student_id, student_group_id),

    constraint student_group_member_student_fk foreign key (student_id) references student (id),
    constraint student_group_member_student_group_fk foreign key (student_group_id) references student_group (id)
);

comment on table student_group_member is 'Член группы студентов';
comment on column student_group_member.student_id is 'Идентификатор студента';
comment on column student_group_member.student_group_id is 'Идентификатор группы студентов';
comment on column student_group_member.enrollment is 'Дата зачисления';
comment on column student_group_member.expulsion is 'Дата отчисления';

create table if not exists program
(
    id                          bigserial           primary key,
    organization_id             bigint              not null,
    code                        varchar(32)         not null,
    name                        varchar(128)        not null,

    constraint program_organization_fk foreign key (organization_id) references organization (id),

    constraint program_organization_id_code_combination unique (organization_id, code),
    constraint program_organization_id_name_combination unique (organization_id, name)
);

create index if not exists program_organization_id_idx on program(organization_id);
create index if not exists program_code_idx on program(code);
create index if not exists program_name_idx on program(name);

comment on table program is 'Программа обучения';
comment on column program.id is 'Идентификатор';
comment on column program.organization_id is 'Идентификатор организации';
comment on column program.code is 'Код';
comment on column program.name is 'Наименование';

create table if not exists profile
(
    id                          bigserial           primary key,
    program_id                  bigint              not null,
    name                        varchar(128)        not null,

    constraint profile_program_fk foreign key (program_id) references program (id),

    constraint profile_program_id_name_combination unique (program_id, name)
);

create index if not exists profile_program_id_idx on profile(program_id);
create index if not exists profile_name_idx on profile(name);

comment on table profile is 'Профиль обучения';
comment on column profile.id is 'Идентификатор';
comment on column profile.program_id is 'Идентификатор программы';
comment on column profile.name is 'Наименование';

create table if not exists discipline
(
    id                          bigserial           primary key,
    organization_id             bigint              not null,
    name                        varchar(128)        not null,

    constraint discipline_organization_fk foreign key (organization_id) references organization (id),

    constraint discipline_organization_id_name_combination unique (organization_id, name)
);

create index if not exists discipline_organization_id_idx on discipline(organization_id);
create index if not exists discipline_name_idx on discipline(name);

comment on table discipline is 'Дисциплина';
comment on column discipline.id is 'Идентификатор';
comment on column discipline.organization_id is 'Идентификатор организации';
comment on column discipline.name is 'Наименование';

create table if not exists teacher
(
    id                          bigserial           primary key,
    organization_id             bigint              not null,
    local_id                    varchar(32)         not null,

    constraint teacher_organization_fk foreign key (organization_id) references organization (id),

    constraint teacher_organization_id_local_id_combination unique (organization_id, local_id)
);

create index if not exists teacher_organization_id_idx on teacher(organization_id);
create index if not exists teacher_local_id_idx on teacher(local_id);

comment on table teacher is 'Преподаватель';
comment on column teacher.id is 'Идентификатор';
comment on column teacher.organization_id is 'Идентификатор организации';
comment on column teacher.local_id is 'Идентификатор, используемый на стороне клиента';

create table if not exists discipline_cource
(
    id                          bigserial           primary key,
    department_id               bigint              not null,
    profile_id                  bigint              not null,
    discipline_id               bigint              not null,
    teacher_id                  bigint              not null,
    semester                    int                 not null,

    constraint discipline_cource_department_fk foreign key (department_id) references department (id),
    constraint discipline_cource_profile_fk foreign key (profile_id) references profile (id),
    constraint discipline_cource_discipline_fk foreign key (discipline_id) references discipline (id),
    constraint discipline_cource_teacher_fk foreign key (teacher_id) references teacher (id),

    constraint discipline_cource_department_id_discipline_id_teacher_id_semester_combination unique (department_id, profile_id, discipline_id, teacher_id, semester)
);

create index if not exists discipline_cource_department_id_idx on discipline_cource(department_id);
create index if not exists discipline_cource_profile_id_idx on discipline_cource(profile_id);
create index if not exists discipline_cource_discipline_id_idx on discipline_cource(discipline_id);
create index if not exists discipline_cource_teacher_id_idx on discipline_cource(teacher_id);
create index if not exists discipline_cource_semester_idx on discipline_cource(semester);

comment on table discipline_cource is 'Курс дисциплины';
comment on column discipline_cource.id is 'Идентификатор';
comment on column discipline_cource.department_id is 'Идентификатор кафедры, предоставляющая преподавателя';
comment on column discipline_cource.discipline_id is 'Идентификатор дисциплины';
comment on column discipline_cource.teacher_id is 'Идентификатор преподавателя';
comment on column discipline_cource.semester is 'Семестр, на котором проходит курс';

create type indicator_type as enum (
    'ATTENDANCE_LECTURE_FISRT_PERIOD',
    'ATTENDANCE_LECTURE_SECOND_PERIOD',
    'ATTENDANCE_LECTURE_THIRD_PERIOD',
    'ATTENDANCE_PRACTICE_FISRT_PERIOD',
    'ATTENDANCE_PRACTICE_SECOND_PERIOD',
    'ATTENDANCE_PRACTICE_THIRD_PERIOD',
    'RESULT_CONTROL_POINT_FIRST',
    'RESULT_CONTROL_POINT_SECOND',
    'RESULT_SESSION'
);

comment on type scope_type is 'Список типов индикатора';

create table if not exists indicator
(
    id                          bigserial           primary key,
    organization_id             bigint              not null,
    type                        indicator_type      not null,
    name                        varchar(128)        not null,
    max_value                   int                 not null,

    constraint indicator_organization_fk foreign key (organization_id) references organization (id),

    constraint indicator_type_name_combination unique (type, name)
);

create index if not exists indicator_organization_id_idx on indicator(organization_id);
create index if not exists indicator_type_idx on indicator(type);

comment on table indicator is 'Индикатор прогресса обучения студента';
comment on column indicator.id is 'Идентификатор';
comment on column indicator.organization_id is 'Идентификатор организации';
comment on column indicator.type is 'Тип';
comment on column indicator.name is 'Наименование';
comment on column indicator.max_value is 'Максимально возможное значение результата';

create table if not exists result
(
    id                          bigserial           primary key,
    discipline_cource_id        bigint              not null,
    indicator_id                bigint              not null,
    student_id                  bigint              not null,
    value                       double precision    not null,
    date                        date                not null,
    is_retake                   boolean             not null,
    is_prediction               boolean             not null,

    constraint result_discipline_cource_fk foreign key (discipline_cource_id) references discipline_cource (id),
    constraint result_indicator_fk foreign key (indicator_id) references indicator (id),
    constraint result_student_fk foreign key (student_id) references student (id),

    constraint result_indicator_student_date_combination unique (indicator_id, student_id, date)
);

create index if not exists result_discipline_cource_id_idx on result(discipline_cource_id);
create index if not exists result_indicator_id_idx on result(indicator_id);
create index if not exists result_student_id_idx on result(student_id);

comment on table result is 'Результат';
comment on column result.id is 'Идентификатор';
comment on column result.discipline_cource_id is 'Идентификатор';
comment on column result.indicator_id is 'Идентификатор';
comment on column result.student_id is 'Идентификатор';
comment on column result.value is 'Значение';
comment on column result.date is 'Дата проставления';
comment on column result.is_retake is 'Флаг, получен ли результат после пересдачи';
comment on column result.is_prediction is 'Флаг, получен ли результат путем предсказания';