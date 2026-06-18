$ErrorActionPreference = 'Stop'
Write-Host "COMPILACION Y PRUEBA LOCAL DEL PERFIL RENDER" -ForegroundColor Cyan
Write-Host "1/3 Compilando modulos con Maven..." -ForegroundColor Yellow
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) { throw "Maven no logro generar los JAR." }

Write-Host "2/3 Construyendo y levantando contenedores..." -ForegroundColor Yellow
docker compose -f docker-compose.render-local.yml up --build -d
if ($LASTEXITCODE -ne 0) { throw "Docker Compose no logro levantar la solución." }

Write-Host "3/3 Estado de los contenedores:" -ForegroundColor Yellow
docker compose -f docker-compose.render-local.yml ps

Write-Host "`nPruebas sugeridas:" -ForegroundColor Green
Write-Host "GET http://localhost:8080/api/usuario"
Write-Host "GET http://localhost:8080/api/peliculas/catalogo"
Write-Host "`nPara detener: .\scripts\03_detener_render_local.ps1" -ForegroundColor Gray