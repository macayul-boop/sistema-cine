--postman_gateway_8080(POST)
{
    "name": "05 POST peliculas por Gateway",
    "request": {
        "_comentario": "NOTA: Este token expira cada 1 hora. Generar uno nuevo en el endpoint de Login.",
        "method": "POST",
        "url": "http://localhost:8080/api/peliculas",
        "header": [
            {
            "key": "Content-Type",
            "value": "application/json"
            },
            {
            "key": "Authorization",
            "value": "Bearer TU_TOKEN_AQUI"
           }
        ],  
        "body": {
           "mode": "raw",
           "raw": "{\n  \"id\":1, \n  \"nombre\": \"AVATAR\", \"duracion\": 162,\n  \"sinopsis\": \"sinopsis de la pelicula", \n  \"categoria\": \"categoria de la pelicula\"\n}",
            "options": {
                "raw": {
                    "language": "json"
               }
            }
        }
    }
}


--postman_render_online(POST)
{
    "name": "05 POST peliculas por Gateway",
    "request": {
        "_comentario": "NOTA: Este token expira cada 1 hora. Generar uno nuevo en el endpoint de Login.",
        "method": "POST",
        "url": "{{gateway_url}}/api/peliculas",
        "header": [
            {
            "key": "Content-Type",
            "value": "application/json"
            },
            {
            "key": "Authorization",
            "value": "Bearer TU_TOKEN_AQUI"
           }
        ],  
        "body": {
           "mode": "raw",
           "raw": "{\n  \"id\":1, \n  \"nombre\": \"AVATAR\", \"duracion\": 162,\n  \"sinopsis\": \"sinopsis de la pelicula", \n  \"categoria\": \"categoria de la pelicula\"\n}",
        }
    }
}