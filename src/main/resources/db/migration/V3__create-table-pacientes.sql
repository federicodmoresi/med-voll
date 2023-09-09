CREATE TABLE pacientes(
	id bigint NOT NULL auto_increment,
	nombre varchar(100) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE,
	telefono VARCHAR(20) NOT NULL,
	documento VARCHAR(10) NOT NULL UNIQUE,
	calle VARCHAR(100) NOT NULL,
	distrito VARCHAR(100) NOT NULL,
	complemento VARCHAR(100),
	numero VARCHAR(20),
	ciudad VARCHAR(100) NOT NULL,
	PRIMARY KEY(id)

);