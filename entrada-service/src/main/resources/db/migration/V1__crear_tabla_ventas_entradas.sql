CREATE TABLE venta_entrada(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    id_funcion BIGINT NOT NULL,
    cantidad BIGINT NOT NULL,
    valor BIGINT NOT NULL
);

