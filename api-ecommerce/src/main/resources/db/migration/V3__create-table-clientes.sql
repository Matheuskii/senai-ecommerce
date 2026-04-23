CREATE TABLE clientes(
                         id bigint not null auto_increment,
                         nome varchar(100) not null,
                         email varchar(255) not null unique,
                         cpf varchar(11) not null unique,
                         telefone varchar(20),
                         ativo tinyint not null,

                         primary key(id)
);