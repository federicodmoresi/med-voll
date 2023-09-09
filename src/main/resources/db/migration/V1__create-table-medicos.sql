CREATE TABLE medicos(
	id bigint NOT NULL auto_increment,
	nombre varchar(100) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE,
	documento VARCHAR(10) NOT NULL UNIQUE,
	especialidad VARCHAR(100) NOT NULL,
	calle VARCHAR(100) NOT NULL,
	distrito VARCHAR(100) NOT NULL,
	complemento VARCHAR(100),
	numero VARCHAR(20),
	ciudad VARCHAR(100) NOT NULL,
	PRIMARY KEY(id)

);
