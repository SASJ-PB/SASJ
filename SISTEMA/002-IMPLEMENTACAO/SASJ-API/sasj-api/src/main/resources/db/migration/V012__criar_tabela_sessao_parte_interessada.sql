CREATE TABLE sessao_parte_interessada (
	codigo_sessao BIGINT(20) NOT NULL,
	codigo_parte_interessada BIGINT(20) NOT NULL,
	PRIMARY KEY (codigo_sessao, codigo_parte_interessada),
	FOREIGN KEY (codigo_sessao) REFERENCES sessao_juridica(codigo),
	FOREIGN KEY (codigo_parte_interessada) REFERENCES parte_interessada(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8



