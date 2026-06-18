CREATE TABLE confiteria(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    fecha_hora DATETIME NOT NULL,
    id_producto BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    cantidad BIGINT NOT NULL
);
