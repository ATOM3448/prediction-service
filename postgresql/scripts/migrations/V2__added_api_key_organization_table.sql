create table if not exists api_key
(
    id                          bigserial           primary key,
    value                       char(120)           not null,
    organization_id             bigint              not null,

    constraint api_key_organization_fk foreign key (organization_id) references organization (id),

    constraint value_unique_conditional unique (value)
);