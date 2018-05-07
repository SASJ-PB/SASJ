-- senha = admin
INSERT INTO usuario (nome, cargo, matricula, senha, email, tipo_usuario, ativo) VALUES ('João Silva', 'Técnico Administrativo', 'MM-1234', '$2a$10$k6mErhbNu9NKu.lgfVSvWeqJNQ9L4DppfUSK9QPTsHL52S9NUrkCa', 'admin@admin.com', 'ADMIN', true);

-- senha = padrao
INSERT INTO usuario (nome, cargo, matricula, senha, email, tipo_usuario, ativo) VALUES ('Maria Ferreira', 'Secretária(o)', 'TT-123', '$2a$10$LpoTVnTPdfcNRjKSaNOkeORFm9LXCsopU8nN0svyOHmJIOr8UCipS', 'padrao@padrao.com', 'PADRAO', true);

INSERT INTO permissao (codigo, descricao) VALUES (1, 'ROLE_CADASTRAR_USUARIO');
INSERT INTO permissao (codigo, descricao) VALUES (2, 'ROLE_PESQUISAR_USUARIO');
INSERT INTO permissao (codigo, descricao) VALUES (3, 'ROLE_ATUALIZAR_USUARIO');
INSERT INTO permissao (codigo, descricao) VALUES (4, 'ROLE_REMOVER_USUARIO');

-- padrao
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 3);

-- admin
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 1);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 3);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 4);
