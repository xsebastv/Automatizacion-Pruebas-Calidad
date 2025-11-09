# 🚀 CÓMO PROBAR EL PROYECTO - GUÍA PRÁCTICA

## ⚠️ IMPORTANTE: Necesitas instalar Maven primero

---

## PASO 1: INSTALAR JAVA (si no lo tienes)

### Verificar si tienes Java:
```powershell
java -version
```

### Si no tienes Java:
1. Ve a: https://adoptium.net/
2. Descarga: **Eclipse Temurin 17 LTS** (Windows x64 MSI)
3. Ejecuta el instalador
4. ✅ Marca: "Add to PATH"
5. ✅ Marca: "Set JAVA_HOME"
6. Click "Install"
7. **REINICIA PowerShell**
8. Verifica: `java -version`

---

## PASO 2: INSTALAR MAVEN

### Opción A: Usando Chocolatey (Recomendado - Rápido)

1. **Instalar Chocolatey:**
   Abre PowerShell como Administrador y ejecuta:
   ```powershell
   Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
   ```

2. **Instalar Maven con Chocolatey:**
   ```powershell
   choco install maven -y
   ```

3. **Reiniciar PowerShell**

4. **Verificar:**
   ```powershell
   mvn -version
   ```

### Opción B: Instalación Manual

1. **Descargar Maven:**
   - Ve a: https://maven.apache.org/download.cgi
   - Descarga: **apache-maven-3.9.9-bin.zip**

2. **Descomprimir:**
   - Descomprime en: `C:\Program Files\Apache\maven`
   - Deberías tener: `C:\Program Files\Apache\maven\bin\mvn.cmd`

3. **Agregar al PATH:**
   - Busca "Variables de entorno" en Windows
   - Click "Variables de entorno"
   - En "Variables del sistema", busca "Path"
   - Click "Editar"
   - Click "Nuevo"
   - Agrega: `C:\Program Files\Apache\maven\bin`
   - Click "Aceptar" en todas las ventanas

4. **Crear MAVEN_HOME:**
   - En "Variables del sistema"
   - Click "Nueva"
   - Nombre: `MAVEN_HOME`
   - Valor: `C:\Program Files\Apache\maven`
   - Click "Aceptar"

5. **REINICIAR PowerShell**

6. **Verificar:**
   ```powershell
   mvn -version
   ```

---

## PASO 3: COMPILAR EL PROYECTO

```powershell
# Navegar a la carpeta del proyecto
cd "c:\Users\sebas\OneDrive\Documents\Calidad y pruebas de software\Proyecto Final\STORE_2511"

# Limpiar y compilar
mvn clean compile
```

**✅ Resultado esperado:**
```
[INFO] BUILD SUCCESS
```

---

## PASO 4: GENERAR EL ARCHIVO EXCEL

### Opción A: Con Maven
```powershell
mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"
```

### Opción B: Con el script BAT
```
Doble clic en: generar-excel.bat
```

**✅ Resultado esperado:**
- Mensaje: "Archivo Excel creado exitosamente en: src\main\resources\testData.xlsx"
- El archivo se crea en: `src\main\resources\testData.xlsx`

**Verificar:**
```powershell
dir "src\main\resources\testData.xlsx"
```

---

## PASO 5: EJECUTAR LAS PRUEBAS

### Opción A: Ejecutar todas las pruebas
```powershell
mvn clean test
```

### Opción B: Con el script BAT
```
Doble clic en: ejecutar-pruebas.bat
```

**✅ Qué verás:**
1. Maven compilando el proyecto
2. Chrome abriéndose automáticamente
3. Navegación a OpenCart
4. Ejecución de las pruebas:
   - Registro de usuarios
   - Login con diferentes credenciales
   - Búsqueda de productos
   - Agregado al carrito
   - Verificación del carrito
5. Chrome cerrándose
6. Mensaje: `[INFO] BUILD SUCCESS`

**Tiempo estimado:** 5-10 minutos

---

## PASO 6: VER LOS RESULTADOS

### Reportes HTML de TestNG

**Ubicación:** `target\surefire-reports\index.html`

**Abrir:**
```powershell
start target\surefire-reports\index.html
```

O navega manualmente y haz doble clic en `index.html`

**Contenido:**
- ✅ Resumen de pruebas ejecutadas
- ✅ Cantidad de passed/failed
- ✅ Tiempo de ejecución
- ✅ Detalles de cada test

### Logs Personalizados

**Ubicación:** `src\main\resources\logs\`

**Ver:**
```powershell
# Listar logs
dir "src\main\resources\logs\"

# Abrir el último log (reemplaza la fecha)
notepad "src\main\resources\logs\TestLog_20251108_143022.txt"
```

**Contenido:**
- ✅ Registros exitosos/fallidos con emails
- ✅ Logins exitosos/fallidos
- ✅ Productos agregados (categoría, subcategoría, producto, cantidad)
- ✅ Verificación final del carrito

---

## 🎯 COMANDOS ÚTILES

### Ver estructura del proyecto
```powershell
tree /F /A
```

### Limpiar compilaciones anteriores
```powershell
mvn clean
```

### Solo compilar (sin ejecutar tests)
```powershell
mvn compile
```

### Ejecutar un test específico
```powershell
mvn test -Dtest=RegistroUsuarioTest
mvn test -Dtest=LoginTest
mvn test -Dtest=BusquedaYCarritoTest
```

### Ver dependencias
```powershell
mvn dependency:tree
```

---

## ❌ SOLUCIÓN DE PROBLEMAS

### Problema: "mvn no se reconoce"
**Solución:** Maven no está instalado o no está en PATH
- Sigue el PASO 2 arriba
- REINICIA PowerShell después de instalar

### Problema: "BUILD FAILURE - No se encuentra testData.xlsx"
**Solución:** El Excel no se ha generado
```powershell
mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"
```

### Problema: "Email already registered"
**Solución:** Los emails ya existen en OpenCart
1. Abre: `src\main\resources\testData.xlsx`
2. Cambia los emails en "UsuariosRegistro"
3. Usa emails únicos: `juan.perez.2025@test.com`
4. Guarda y vuelve a ejecutar

### Problema: "Connection timeout"
**Solución:** Verifica tu conexión a internet
- Prueba abrir: https://opencart.abstracta.us/
- Si es lento, aumenta timeouts en WaitHelper.java

### Problema: "Tests fallan"
**Solución:** Revisa los logs y reportes para detalles
```powershell
start target\surefire-reports\index.html
notepad "src\main\resources\logs\TestLog_*.txt"
```

---

## 📸 CAPTURAR EVIDENCIAS

Durante la ejecución, captura:

1. **PowerShell ejecutando `mvn test`**
2. **Chrome con OpenCart:**
   - Formulario de registro llenándose
   - Página de login
   - Búsqueda de producto
   - Carrito con productos
3. **Mensaje "BUILD SUCCESS"**
4. **Reporte HTML**
5. **Log con resultados**

Ver guía completa en: [EVIDENCIAS.md](EVIDENCIAS.md)

---

## ✅ CHECKLIST RÁPIDO

```
Antes de ejecutar:
□ Java instalado y funcionando
□ Maven instalado y funcionando  
□ Conexión a internet activa
□ Chrome instalado

Durante:
□ Excel generado correctamente
□ Chrome se abre automáticamente
□ Pruebas se ejecutan sin errores

Después:
□ Reportes HTML generados
□ Logs creados con información completa
□ Evidencias capturadas
```

---

## 🎬 DEMOSTRACIÓN RÁPIDA (5 MIN)

```powershell
# 1. Navegar al proyecto
cd "c:\Users\sebas\OneDrive\Documents\Calidad y pruebas de software\Proyecto Final\STORE_2511"

# 2. Verificar que tienes Maven
mvn -version

# 3. Generar Excel
mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"

# 4. Ejecutar pruebas
mvn clean test

# 5. Ver reportes
start target\surefire-reports\index.html

# 6. Ver logs
dir src\main\resources\logs\
```

---

## 🆘 AYUDA ADICIONAL

Si tienes problemas:

1. **Lee INICIO_RAPIDO.md** - Guía detallada
2. **Lee GUIA_VISUAL.md** - Instrucciones con ejemplos
3. **Revisa CHECKLIST.md** - Verificación completa
4. **Consulta INDICE.md** - Encuentra cualquier información

---

## 📞 SIGUIENTE PASO

**Una vez que Maven esté instalado y verificado:**

1. Abre PowerShell en esta carpeta
2. Ejecuta:
   ```powershell
   mvn clean compile
   mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"
   mvn clean test
   ```
3. ¡Listo! Tu proyecto estará ejecutándose

---

**¡Instala Maven y estarás listo para probar! 🚀**
