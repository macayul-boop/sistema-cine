CREATE TABLE comentario(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    fecha_hora DATETIME NOT NULL,
    comentario VARCHAR(150) NOT NULL,
    id_pelicula BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL
);
