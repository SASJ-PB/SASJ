CREATE TABLE sessao_juridica (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	quantidade_oitivas INT(20) NOT NULL,
    observacao VARCHAR(150),
    duracao_estimada INT(8) NOT NULL,
    codigo_processo BIGINT(20),
    codigo_pendencia BIGINT(20),
    agendamento DATETIME NOT NULL,
    status_agendamento VARCHAR(20) NOT NULL,
    FOREIGN KEY (codigo_processo) REFERENCES processo(codigo),
    FOREIGN KEY (codigo_pendencia) REFERENCES pendencia(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE audiencia (
	codigo BIGINT(20) PRIMARY KEY,
	tipo_audiencia VARCHAR(40) NOT NULL,
    FOREIGN KEY (codigo) REFERENCES sessao_juridica(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE conciliacao (
	codigo BIGINT(20) PRIMARY KEY,
	nome_conciliador VARCHAR(100) NOT NULL,
    FOREIGN KEY (codigo) REFERENCES sessao_juridica(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
