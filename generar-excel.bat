@echo off
echo ========================================
echo Generando archivo Excel con datos de prueba
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
    echo 3. O ejecuta manualmente:
    echo    mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"
    echo.
    pause
    exit /b 1
)

echo Maven encontrado. Generando Excel...
echo.

call mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo [EXITO] Archivo Excel generado!
    echo Ubicacion: src\main\resources\testData.xlsx
    echo ========================================
) else (
    echo.
    echo ========================================
    echo [ERROR] No se pudo generar el archivo
    echo ========================================
)

echo.
pause
