CREATE TABLE funcion(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    fecha_hora_inicio DATETIME NOT NULL,
    fecha_hora_termino DATETIME NOT NULL,
    id_pelicula BIGINT NOT NULL,
    id_sala BIGINT NOT NULL
);
