CREATE TABLE IF NOT EXISTS alumno_entity (
                                       id              INT PRIMARY KEY AUTO_INCREMENT,
                                       nombre          VARCHAR(50) NOT NULL,
    apellido        VARCHAR(50) NOT NULL,
    estado          VARCHAR(10) NOT NULL,
    edad            INT NOT NULL
    );
