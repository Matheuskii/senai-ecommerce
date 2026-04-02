create table categorias (
    id bigint not null auto_increment,
    nome varchar(60) not null unique,
    descricao varchar(250),
primary key(id)
);