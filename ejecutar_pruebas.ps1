# =========================================================
# Leer variables desde el archivo .env
# =========================================================
$envFile = Join-Path $PSScriptRoot ".env"

if (Test-Path $envFile) {
    Get-Content $envFile | ForEach-Object {
        if ($_ -match "^([^#][^=]+)=(.+)$") {
            $key   = $Matches[1].Trim()
            $value = $Matches[2].Trim()
            [System.Environment]::SetEnvironmentVariable($key, $value, "Process")
            Write-Host "[OK] Variable cargada: $key" -ForegroundColor DarkGray
        }
    }
} else {
    Write-Host "[ERROR] No se encontro el archivo .env en: $envFile" -ForegroundColor Red
    Write-Host "        Crea el archivo .env con: GEMINI_API_KEY=tu_api_key" -ForegroundColor Yellow
    exit 1
}

if (-not $env:GEMINI_API_KEY -or $env:GEMINI_API_KEY -eq "PEGA_AQUI_TU_NUEVA_API_KEY") {
    Write-Host "[ERROR] GEMINI_API_KEY no esta configurada en el archivo .env" -ForegroundColor Red
    exit 1
}

$timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$carpetaEvidencia = "Evidencias\Ejecucion_$timestamp"

New-Item -ItemType Directory -Force -Path $carpetaEvidencia | Out-Null
$archivoLog = "$carpetaEvidencia\consola.log"

Write-Host "=========================================================" -ForegroundColor Cyan
Write-Host "Iniciando ejecucion de pruebas Serenity BDD" -ForegroundColor Cyan
Write-Host "Las evidencias se guardaran en: $carpetaEvidencia" -ForegroundColor Cyan
Write-Host "=========================================================" -ForegroundColor Cyan

.\gradlew clean test "-Denvironment=edge" "-Dwebdriver.edge.driver=src/test/resources/drivers/msedgedriver.exe" 2>&1 | Tee-Object -FilePath $archivoLog -Append

$rutaReporteOriginal  = "target\site\serenity"
$rutaReporteEvidencia = "$carpetaEvidencia\reporte_serenity"

if (Test-Path $rutaReporteOriginal) {
    Copy-Item -Path $rutaReporteOriginal -Destination $rutaReporteEvidencia -Recurse -Force
    Write-Host "[OK] Reporte de Serenity copiado a: $rutaReporteEvidencia" -ForegroundColor Green
} else {
    Write-Host "[AVISO] No se encontro el reporte de Serenity en $rutaReporteOriginal" -ForegroundColor Yellow
}

Write-Host "[OK] Log de consola guardado en: $archivoLog" -ForegroundColor Green
Write-Host "=========================================================" -ForegroundColor Cyan
Write-Host "Ejecucion finalizada. Revisa la carpeta $carpetaEvidencia" -ForegroundColor Green
