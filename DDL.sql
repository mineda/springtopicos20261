create table usr_usuario (
  usr_id bigint generated always as identity,
  usr_nome varchar(20) not null,
  usr_senha varchar(150) not null,
  primary key (usr_id),
  unique (usr_nome)
);

create table aut_autorizacao (
  aut_id bigint generated always as identity,
  aut_nome varchar(20) not null,
  primary key (aut_id),
  unique (aut_nome)
);

create table uau_usuario_autorizacao (
  usr_id bigint not null,
  aut_id bigint not null,
  primary key (usr_id, aut_id),
  foreign key (usr_id) references usr_usuario (usr_id) on delete restrict on update
  cascade,
  foreign key (aut_id) references aut_autorizacao (aut_id) on delete restrict on
  update cascade
);

create table ant_anotacao (
  ant_id bigint generated always as identity,
  ant_texto varchar(256) not null,
  ant_data_hora timestamp not null,
  ant_usr_id bigint not null,
  primary key (ant_id),
  foreign key (ant_usr_id) references usr_usuario(usr_id)
);

insert into usr_usuario (usr_nome, usr_senha)
  values ('admin', '$2a$10$i3.Z8Yv1Fwl0I5SNjdCGkOTRGQjGvHjh/gMZhdc3e7LIovAklqM6C');
insert into aut_autorizacao (aut_nome)
  values ('ROLE_ADMIN');
insert into uau_usuario_autorizacao (usr_id, aut_id)
  values (1, 1);
insert into ant_anotacao(ant_texto, ant_data_hora, ant_usr_id)
  values('Meu novo projeto', '2023-08-01 19:10', 1);

create user spring with password 'pass123';

-- Tabela trabalho, do exemplo de avaliação Spring

create table tra_trabalho (
  tra_id bigint generated always as identity,
  tra_titulo varchar(100) not null unique,
  tra_data_hora_entrega timestamp not null,
  tra_descricao varchar(200),
  tra_usr_id bigint not null,
  tra_nota int,
  primary key(tra_id),
  foreign key (tra_usr_id) references usr_usuario (usr_id) on delete restrict on update cascade
);

insert into tra_trabalho (tra_titulo, tra_data_hora_entrega, tra_nota, tra_usr_id)
  values ('Teste 1', current_timestamp, 6, 1),
    ('Teste 2', current_timestamp, null, 1);

-- Tabelas novas
create table cap_capitulo (
  cap_id bigint generated always as identity,
  cap_titulo varchar(100) not null unique,
  cap_conteudo varchar(200),
  cap_data_criacao date not null,
  cap_data_entrega date,
  cap_trabalho bigint not null,
  foreign key (cap_trabalho) references tra_trabalho (tra_id) on delete restrict,
  primary key (cap_id)
);

insert into cap_capitulo (cap_titulo, cap_conteudo, cap_data_criacao, cap_data_entrega, cap_trabalho)
  values ('Capítulo 1', 'Conteúdo do capítulo 1', '2025-12-07', current_date, 1),
    ('Capítulo 2', 'Conteúdo do capítulo 2', current_date, null, 1);

create table rev_revisao (
  rev_id bigint generated always as identity,
  rev_feedback varchar(100) not null,
  rev_nota int not null,
  rev_data_revisao date not null,
  rev_data_leitura date,
  rev_capitulo bigint not null,
  foreign key (rev_capitulo) references cap_capitulo (cap_id) on delete restrict,
  primary key (rev_id)
);

insert into rev_revisao (rev_feedback, rev_nota, rev_data_revisao, rev_data_leitura, rev_capitulo)
  values ('Muito bom!', 10, '2025-12-08', current_date, 1),
    ('Horrível. O que houve?', 3, current_date, null, 2);

create table rvr_revisor (
  rvr_id bigint generated always as identity,
  rvr_revisao bigint not null unique,
  rvr_usuario bigint not null,
  rvr_observacao varchar(200),
  rvr_prazo date,
  foreign key (rvr_revisao) references rev_revisao (rev_id) on delete restrict,
  foreign key (rvr_usuario) references usr_usuario (usr_id) on delete restrict,
  primary key (rvr_id)
);

insert into rvr_revisor (rvr_revisao, rvr_usuario, rvr_observacao, rvr_prazo)
  values (1, 1, null, '2025-12-15'),
    (2, 1, 'Entregou correndo. Pode estar ruim.', null);

-- Execute ao final para dar acesso ao usuario spring
grant update, delete, insert, select on all tables in schema public to spring;