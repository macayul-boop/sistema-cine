CREATE TABLE peliculas(
    id_pelicula BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre_pelicula VARCHAR(255) NOT NULL,
    duracion  INT NOT NULL,
    sinopsis VARCHAR(255) NOT NULL,
    categoria VARCHAR(255) NOT NULL);