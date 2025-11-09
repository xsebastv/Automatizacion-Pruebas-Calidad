@echo off
echo ========================================
echo Ejecutando pruebas de OpenCart
echo ========================================
echo.

cd /d "%~dp0"

echo Buscando Maven...
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Maven no encontrado en el PATH
    echo.
    echo Por favor:
    echo 1. Instala Maven desde https://maven.apache.org/download.cgi
    echo 2. O agrega Maven al PATH del sistema
    echo.
    pause
    exit /b 1
)

echo Maven encontrado. Compilando y ejecutando pruebas...
echo.

call mvn clean test

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo [EXITO] Pruebas completadas!
    echo ========================================
    echo.
    echo Reportes disponibles en:
    echo - target\surefire-reports\index.html
    echo - src\main\resources\logs\
    echo.
    echo Abriendo reporte...
    start target\surefire-reports\index.html
) else (
    echo.
    echo ========================================
    echo [ADVERTENCIA] Algunas pruebas fallaron
    echo ========================================
    echo.
    echo Revisa los reportes en:
    echo - target\surefire-reports\index.html
    echo - src\main\resources\logs\
    echo.
)

echo.
pause
