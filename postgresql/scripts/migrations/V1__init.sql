create table if not exists organization
(
    id                          bigserial           primary key,
    name                        varchar(120)        not null
);

create table if not exists faculty
(
    id                          bigserial           primary key,
    organization_id             bigint              not null,
    name                        varchar(120)        not null,

    constraint faculty_organization_fk foreign key (organization_id) references organization (id),

    constraint organization_id_name_combination unique (organization_id, name)
);

create index if not exists faculty_organization_id_idx on faculty(organization_id);

create table if not exists department
(
    id                          bigserial           primary key,
    faculty_id                  bigint              not null,
    name                        varchar(120)        not null,

    constraint department_faculty_fk foreign key (faculty_id) references faculty (id),

    constraint faculty_id_name_combination unique (faculty_id, name)
);

create index if not exists department_faculty_id_idx on department(faculty_id);

create table if not exists program
(
    id                          bigserial           primary key,
    department_id               bigint              not null,
    code                        varchar(20)         not null,
    name                        varchar(120)        not null,

    constraint program_department_fk foreign key (department_id) references department (id),

    constraint department_id_code_name_combination unique (department_id, code, name)
);

create index if not exists program_department_id_idx on program(department_id);

create table if not exists profile
(
    id                          bigserial           primary key,
    program_id                  bigint              not null,
    name                        varchar(120)        not null,

    constraint profile_program_fk foreign key (program_id) references program (id),

    constraint program_id_name_combination unique (program_id, name)
);

create index if not exists profile_program_id_idx on profile(program_id);

create table if not exists teacher
(
    id                          bigserial           primary key,
    organization_teacher_id     bigint              not null
);

create index if not exists teacher_organization_teacher_id_idx on teacher(organization_teacher_id);

create table if not exists discipline
(
    id                          bigserial           primary key,
    profile_id                  bigint              not null,
    teacher_id                  bigint              not null,
    semester                    int                 not null,
    name                        varchar(120)        not null,

    constraint discipline_profile_fk foreign key (profile_id) references profile (id),
    constraint discipline_teacher_fk foreign key (teacher_id) references teacher (id),

    constraint profile_id_teacher_id_semester_combination unique (profile_id, teacher_id, name)
);

create table if not exists indicator_type
(
    id                          bigserial           primary key,
    name                        varchar(120)        not null unique,
    description                 varchar(120)        not null
);

create index if not exists indicator_type_name_idx on indicator_type(name);

insert into indicator_type (name, description)
values  ('ATTENDANCE_LECTURE_FISRT_PERIOD', 'Посещаемость лекций за промежуток 1'),
        ('ATTENDANCE_LECTURE_SECOND_PERIOD', 'Посещаемость лекций за промежуток 2'),
        ('ATTENDANCE_LECTURE_THIRD_PERIOD', 'Посещаемость лекций за промежуток 3'),
        ('ATTENDANCE_PRACTICE_FISRT_PERIOD', 'Посещаемость практик за промежуток 1'),
        ('ATTENDANCE_PRACTICE_SECOND_PERIOD', 'Посещаемость практик за промежуток 2'),
        ('ATTENDANCE_PRACTICE_THIRD_PERIOD', 'Посещаемость практик за промежуток 3'),
        ('RESULT_CONTROL_POINT_FIRST', 'Результат КТ 1'),
        ('RESULT_CONTROL_POINT_SECOND', 'Результат КТ 2'),
        ('RESULT_SESSION', 'Результат сессии'),
        ('AVERANGE_RESULT', 'Средний балл')
on conflict do nothing;

create table if not exists indicator
(
    id                          bigserial           primary key,
    indicator_type_id           bigint              not null,
    discipline_id               bigint              not null,
    name                        varchar(120)        not null,
    max_value                   int                 not null,

    constraint indicator_indicator_type_fk foreign key (indicator_type_id) references indicator_type (id),
    constraint indicator_discipline_fk foreign key (discipline_id) references discipline (id),

    constraint indicator_type_id_discipline_id_name_combination unique (indicator_type_id, discipline_id, name)
);

create index if not exists indicator_discipline_id_idx on indicator(discipline_id);

create table if not exists student_group
(
    id                          bigserial           primary key,
    department_id               bigint              not null,
    name                        varchar(20)         not null,

    constraint student_group_department_fk foreign key (department_id) references department (id),

    constraint department_id_name_combination unique (department_id, name)
);

create table if not exists student
(
    id                          bigserial           primary key,
    student_group_id            bigint              not null,
    organization_student_id     bigint              not null,

    constraint student_student_group_fk foreign key (student_group_id) references student_group (id),

    constraint student_group_id_organization_student_id_combination unique (student_group_id, organization_student_id)
);

create index if not exists student_organization_student_id_idx on student(organization_student_id);

create table if not exists result
(
    id                          bigserial           primary key,
    indicator_id                bigint              not null,
    student_id                  bigint              not null,
    value                       double precision    not null,
    is_prediction               boolean             not null,

    constraint result_indicator_fk foreign key (indicator_id) references indicator (id),
    constraint result_student_fk foreign key (student_id) references student (id),

    constraint indicator_student_combination unique (indicator_id, student_id)
);

create index if not exists result_student_id_idx on result(student_id);

create table if not exists next_session_averange_result
(
    id                          bigserial           primary key,
    student_id                  bigint              not null,
    value                       double precision    not null,
    prediction_time             timestamptz(3)      not null        default now(),

    constraint next_session_averange_result_student_fk foreign key (student_id) references student (id)
);
