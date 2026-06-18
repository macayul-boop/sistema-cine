$ErrorActionPreference = 'Stop'
docker compose -f docker-compose.render-local.yml down
Write-Host "Contenedores del perfil Render local detenidos correctamente." -ForegroundColor Green