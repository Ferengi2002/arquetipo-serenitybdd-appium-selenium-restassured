$env:GEMINI_API_KEY = "AIzaSyAedJbFskKhZdF3yCe9XPr2b769Jl6XUAQ"

$timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$carpetaEvidencia = "Evidencias\Ejecucion_$timestamp"

# Crear la carpeta de evidencia para esta ejecución
New-Item -ItemType Directory -Force -Path $carpetaEvidencia | Out-Null
$archivoLog = "$carpetaEvidencia\consola.log"

Write-Host "=========================================================" -ForegroundColor Cyan
Write-Host "Iniciando ejecución de pruebas Serenity BDD" -ForegroundColor Cyan
Write-Host "Las evidencias se guardarán en: $carpetaEvidencia" -ForegroundColor Cyan
Write-Host "=========================================================" -ForegroundColor Cyan

# Iniciar las pruebas con Gradle y capturar la salida con Tee-Object
.\gradlew clean test "-Denvironment=edge" "-Dwebdriver.edge.driver=src/test/resources/drivers/msedgedriver.exe" 2>&1 | Tee-Object -FilePath $archivoLog -Append

# Copiar el reporte de Serenity (con imágenes e HTML) a la carpeta de evidencia
$rutaReporteOriginal = "target\site\serenity"
$rutaReporteEvidencia = "$carpetaEvidencia\reporte_serenity"

if (Test-Path $rutaReporteOriginal) {
    Copy-Item -Path $rutaReporteOriginal -Destination $rutaReporteEvidencia -Recurse -Force
    Write-Host "✅ Reporte de Serenity (HTML y Capturas) copiado a: $rutaReporteEvidencia" -ForegroundColor Green
} else {
    Write-Host "⚠️ No se pudo encontrar el reporte de Serenity en $rutaReporteOriginal." -ForegroundColor Yellow
}

Write-Host "✅ Log de consola guardado en: $archivoLog" -ForegroundColor Green
Write-Host "=========================================================" -ForegroundColor Cyan
Write-Host "Ejecución finalizada. Revisa la carpeta '$carpetaEvidencia'" -ForegroundColor Green
