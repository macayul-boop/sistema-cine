CREATE TABLE inventario(
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        producto VARCHAR(100) NOT NULL,
        cantidad BIGINT NOT NULL,
        valor BIGINT NOT NULL
);
