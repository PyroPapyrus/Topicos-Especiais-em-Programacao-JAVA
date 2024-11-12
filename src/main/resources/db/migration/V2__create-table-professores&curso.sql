create table professores (
    id int not null primary key auto_increment,
    nome varchar(100),
    email varchar(100),
    telefone varchar(15),
    especialidade varchar(100)
);

create table cursos (
    id int not null primary key auto_increment,
    nome varchar(100),
    codigo varchar(20),
    carga_horaria int
);