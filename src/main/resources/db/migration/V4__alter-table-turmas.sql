alter table turmas add curso_id int;

alter table turmas add constraint FK_curso
foreign key (curso_id) references cursos(id);