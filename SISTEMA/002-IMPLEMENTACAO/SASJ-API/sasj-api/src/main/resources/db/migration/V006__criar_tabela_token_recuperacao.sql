CREATE TABLE token_recuperacao (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	token VARCHAR(65) NOT NULL,
    codigo_usuario BIGINT(20),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
