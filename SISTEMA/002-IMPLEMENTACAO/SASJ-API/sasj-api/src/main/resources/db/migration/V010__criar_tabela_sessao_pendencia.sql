CREATE TABLE sessao_pendencia (
	codigo_sessao BIGINT(20) NOT NULL,
	codigo_pendencia BIGINT(20) NOT NULL,
	PRIMARY KEY (codigo_sessao, codigo_pendencia),
	FOREIGN KEY (codigo_sessao) REFERENCES sessao_juridica(codigo),
	FOREIGN KEY (codigo_pendencia) REFERENCES pendencia(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8



