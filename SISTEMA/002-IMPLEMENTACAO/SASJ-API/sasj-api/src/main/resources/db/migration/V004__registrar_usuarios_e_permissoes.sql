-- senha = admin
INSERT INTO usuario (nome, cargo, matricula, senha, email, tipo_usuario, ativo) VALUES ('João Silva', 'Técnico Administrativo', 'MM-1234', '$2a$10$k6mErhbNu9NKu.lgfVSvWeqJNQ9L4DppfUSK9QPTsHL52S9NUrkCa', 'admin@admin.com', 'ADMIN', true);

-- senha = padrao
INSERT INTO usuario (nome, cargo, matricula, senha, email, tipo_usuario, ativo) VALUES ('Maria Ferreira', 'Secretária(o)', 'MM-123', '$2a$10$LpoTVnTPdfcNRjKSaNOkeORFm9LXCsopU8nN0svyOHmJIOr8UCipS', 'padrao@padrao.com', 'PADRAO', true);

-- senha = public
INSERT INTO usuario (nome, cargo, matricula, senha, email, tipo_usuario, ativo) VALUES ('Public', 'Carg. Public', 'PP-1234', '$2a$10$Spzrz3h6884W5Jjc/yNbl.z7.JzHSzoaIfVOfr7iPvo8t6wVnagam', 'public@public.com', 'PADRAO', true);

INSERT INTO permissao (codigo, descricao) VALUES (1, 'ROLE_CADASTRAR_USUARIO');
INSERT INTO permissao (codigo, descricao) VALUES (2, 'ROLE_PESQUISAR_USUARIO');
INSERT INTO permissao (codigo, descricao) VALUES (3, 'ROLE_ATUALIZAR_USUARIO');
INSERT INTO permissao (codigo, descricao) VALUES (4, 'ROLE_REMOVER_USUARIO');
INSERT INTO permissao (codigo, descricao) VALUES (5, 'ROLE_CADASTRAR_AUDIENCIA');
INSERT INTO permissao (codigo, descricao) VALUES (6, 'ROLE_PESQUISAR_AUDIENCIA');
INSERT INTO permissao (codigo, descricao) VALUES (7, 'ROLE_ATUALIZAR_AUDIENCIA');
INSERT INTO permissao (codigo, descricao) VALUES (8, 'ROLE_REMOVER_AUDIENCIA');
INSERT INTO permissao (codigo, descricao) VALUES (9, 'ROLE_CADASTRAR_CONCILIACAO');
INSERT INTO permissao (codigo, descricao) VALUES (10, 'ROLE_PESQUISAR_CONCILIACAO');
INSERT INTO permissao (codigo, descricao) VALUES (11, 'ROLE_ATUALIZAR_CONCILIACAO');
INSERT INTO permissao (codigo, descricao) VALUES (12, 'ROLE_REMOVER_CONCILIACAO');

-- admin
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 1);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 3);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 4);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 5);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 6);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 7);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 8);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 9);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 10);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 11);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 12);

-- padrao
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 3);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 5);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 6);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 7);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 8);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 9);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 10);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 11);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 12);


