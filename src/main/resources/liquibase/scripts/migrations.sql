--formatted sql

--changeSet student: 1
create table students (
    id serial,
    name text,
    age integer
);

--changeSet student: 2
create index student_name_index on students(name);

--changeset student:3
create table faculties (
    id serial,
    name text,
    color text
);

--changeset student:4
create index faculty_name_index on faculties(name);

--changeset student:5
create index faculty_color_index on faculties(color);