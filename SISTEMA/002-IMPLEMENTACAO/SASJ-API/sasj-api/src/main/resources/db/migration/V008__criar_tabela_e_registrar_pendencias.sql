CREATE TABLE pendencia (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(250) NOT NULL,
    responsavel VARCHAR(50) NOT NULL,
    resolvida BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;