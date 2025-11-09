# 🎯 GUÍA VISUAL PASO A PASO

## Instrucciones detalladas con ejemplos

---

## 📦 PASO 1: VERIFICAR INSTALACIONES

### 1.1 Verificar Java
```powershell
# Abrir PowerShell y ejecutar:
java -version

# ✅ Resultado esperado:
# openjdk version "17.0.x" o superior
# Si muestra error, instalar desde: https://adoptium.net/
```

### 1.2 Verificar Maven
```powershell
# En PowerShell ejecutar:
mvn -version

# ✅ Resultado esperado:
# Apache Maven 3.x.x
# Si muestra error:
# 1. Descargar: https://maven.apache.org/download.cgi
# 2. Descomprimir en C:\Program Files\Apache\maven
# 3. Agregar al PATH: C:\Program Files\Apache\maven\bin
```

---

## 📊 PASO 2: GENERAR ARCHIVO EXCEL

### Opción A: Usar el Script BAT (RECOMENDADO)

```
1. Navegar a la carpeta del proyecto:
   STORE_2511\

2. Localizar el archivo:
   generar-excel.bat

3. Doble clic en el archivo

4. ✅ Verás una ventana que dice:
   "Archivo Excel creado exitosamente en: src\main\resources\testData.xlsx"

5. Presionar cualquier tecla para cerrar
```

### Opción B: Comando Maven Manual

```powershell
# 1. Abrir PowerShell
# 2. Navegar a la carpeta del proyecto
cd "c:\Users\sebas\OneDrive\Documents\Calidad y pruebas de software\Proyecto Final\STORE_2511"

# 3. Compilar primero
mvn clean compile

# 4. Ejecutar el generador
mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"

# ✅ Resultado esperado:
# [INFO] BUILD SUCCESS
# Archivo Excel creado exitosamente en: src\main\resources\testData.xlsx
```

### Verificar que el Excel se creó

```powershell
# Ver el archivo
dir "src\main\resources\testData.xlsx"

# ✅ Debe mostrar: testData.xlsx
```

---

## 📝 PASO 3: REVISAR/EDITAR DATOS (OPCIONAL)

### Abrir el Excel

```
1. Navegar a: src\main\resources\
2. Doble clic en: testData.xlsx
3. Se abrirá Microsoft Excel
```

### Revisar las 3 Hojas

#### Hoja 1: UsuariosRegistro
```
Columnas: First Name | Last Name | E-Mail | Telephone | Password

Ejemplo de fila:
Juan | Pérez | juan.perez@test.com | 3001234567 | Test123!

⚠️ IMPORTANTE:
- Los emails deben ser únicos
- Si ya ejecutaste el test antes, cambia los emails
- Formato sugerido: nombre.apellido.123@test.com
```

#### Hoja 2: LoginData
```
Columnas: Email | Password | Expected Result

Ejemplo de fila válida:
juan.perez@test.com | Test123! | Success

Ejemplo de fila inválida:
usuario.invalido@test.com | wrong123 | Fail

⚠️ IMPORTANTE:
- Los usuarios con "Success" DEBEN estar registrados primero
- Ejecuta RegistroUsuarioTest antes de LoginTest
```

#### Hoja 3: ProductosBusqueda
```
Columnas: Categoria | SubCategoria | Producto | Cantidad

Ejemplo:
Desktops | PC | HP LP3065 | 1

⚠️ IMPORTANTE:
- Los nombres de productos deben coincidir con OpenCart
- Categorías opcionales (puede estar vacía)
- Subcategorías opcionales (puede estar vacía)
```

### Guardar Cambios

```
1. Hacer cambios si es necesario
2. Ctrl + S para guardar
3. Cerrar Excel
```

---

## ▶️ PASO 4: EJECUTAR LAS PRUEBAS

### Opción A: Usar el Script BAT (RECOMENDADO)

```
1. Navegar a la carpeta del proyecto:
   STORE_2511\

2. Localizar el archivo:
   ejecutar-pruebas.bat

3. Doble clic en el archivo

4. ✅ Verás:
   - Ventana de PowerShell
   - Maven compilando el proyecto
   - Chrome abriéndose automáticamente
   - Pruebas ejecutándose
   - Mensajes en la consola

5. ✅ Al finalizar:
   - Se abre automáticamente el reporte HTML
   - Mensaje: "Pruebas completadas!"
```

### Opción B: Comando Maven Manual

```powershell
# 1. Abrir PowerShell
# 2. Navegar a la carpeta del proyecto
cd "c:\Users\sebas\OneDrive\Documents\Calidad y pruebas de software\Proyecto Final\STORE_2511"

# 3. Ejecutar pruebas
mvn clean test

# ✅ Verás en la consola:
# [INFO] Scanning for projects...
# [INFO] Building STORE_2511 1.0-SNAPSHOT
# [INFO] Running TestSuite
# [INFO] Tests run: X, Failures: 0, Errors: 0, Skipped: 0
# [INFO] BUILD SUCCESS
```

### ¿Qué verás durante la ejecución?

```
1. Chrome se abre automáticamente
2. Navegará a: https://opencart.abstracta.us/
3. Ejecutará en orden:
   a) RegistroUsuarioTest
      - Abre formulario de registro
      - Llena datos del Excel
      - Verifica mensaje de éxito
   
   b) LoginTest
      - Abre formulario de login
      - Prueba cada credencial del Excel
      - Valida éxito/fallo
   
   c) BusquedaYCarritoTest
      - Busca cada producto del Excel
      - Los agrega al carrito
      - Verifica que estén en el carrito

4. Al finalizar, Chrome se cierra
```

---

## 📊 PASO 5: VER RESULTADOS

### 5.1 Reporte HTML de TestNG

```
Ubicación: target\surefire-reports\index.html

Para abrir:
Opción 1: El script ejecutar-pruebas.bat lo abre automáticamente

Opción 2: Manual
1. Navegar a: target\surefire-reports\
2. Doble clic en: index.html
3. Se abre en el navegador

✅ Verás:
- Resumen de pruebas (Pass/Fail)
- Tiempo de ejecución
- Detalles de cada test
- Stack traces de errores (si hay)
```

### 5.2 Emailable Report

```
Ubicación: target\surefire-reports\emailable-report.html

Para abrir:
1. Navegar a: target\surefire-reports\
2. Doble clic en: emailable-report.html

✅ Verás:
- Reporte compacto
- Fácil de enviar por email
- Resumen de resultados
```

### 5.3 Log Personalizado

```
Ubicación: src\main\resources\logs\

Para ver:
1. Navegar a: src\main\resources\logs\
2. Buscar archivo: TestLog_YYYYMMDD_HHMMSS.txt
3. Doble clic para abrir con Notepad

✅ Contenido:
========================================
REPORTE DE EJECUCIÓN DE PRUEBAS - OpenCart
Fecha: 08/11/2025 14:30:45
========================================

========== PRUEBA DE REGISTRO DE USUARIOS ==========

[REGISTRO] 08/11/2025 14:30:46
  Email: juan.perez@test.com
  Estado: EXITOSO
  Mensaje: Your Account Has Been Created!

[REGISTRO] 08/11/2025 14:30:52
  Email: maria.gonzalez@test.com
  Estado: EXITOSO
  Mensaje: Your Account Has Been Created!

========== PRUEBA DE INICIO DE SESIÓN ==========

[LOGIN] 08/11/2025 14:31:10
  Email: juan.perez@test.com
  Estado: EXITOSO
  Mensaje: Login exitoso como esperado

[LOGIN] 08/11/2025 14:31:15
  Email: usuario.invalido@test.com
  Estado: FALLIDO
  Mensaje: Falló como esperado: Warning: No match for E-Mail...

========== PRUEBA DE BÚSQUEDA Y AGREGADO AL CARRITO ==========

[PRODUCTO AGREGADO] 08/11/2025 14:32:10
  Categoría: Desktops
  Subcategoría: PC
  Producto: HP LP3065
  Cantidad: 1
  Estado: EXITOSO

[VERIFICACIÓN CARRITO] 08/11/2025 14:33:00
  Productos Esperados: 5
  Productos Encontrados: 5
  Estado: EXITOSO

========== FIN DEL REPORTE ==========
```

---

## 🔍 VERIFICAR QUE TODO FUNCIONÓ

### ✅ Checklist de Éxito

```
Marca cada punto si lo ves:

□ Excel generado en src\main\resources\testData.xlsx
□ Chrome se abrió durante la ejecución
□ Se ejecutaron las 3 clases de test
□ Consola mostró "BUILD SUCCESS"
□ Reportes generados en target\surefire-reports\
□ index.html se puede abrir y muestra resultados
□ Log generado en src\main\resources\logs\
□ Log contiene información de:
  □ Registros exitosos/fallidos
  □ Logins exitosos/fallidos
  □ Productos agregados con categoría, subcategoría, producto, cantidad
  □ Verificación de carrito
```

---

## ❌ SOLUCIÓN DE PROBLEMAS

### Problema 1: "mvn no se reconoce"
```
Causa: Maven no está instalado o no está en el PATH

Solución:
1. Instalar Maven:
   - Descargar de: https://maven.apache.org/download.cgi
   - Descomprimir en: C:\Program Files\Apache\maven

2. Agregar al PATH:
   - Buscar "Variables de entorno" en Windows
   - Click en "Variables de entorno"
   - En "Variables del sistema", buscar "Path"
   - Click en "Editar"
   - Click en "Nuevo"
   - Agregar: C:\Program Files\Apache\maven\bin
   - Click "Aceptar" en todas las ventanas
   - CERRAR Y REABRIR PowerShell

3. Verificar:
   mvn -version
```

### Problema 2: "No se encuentra testData.xlsx"
```
Causa: No se ha generado el Excel

Solución:
Ejecutar:
mvn clean compile
mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"

O doble clic en: generar-excel.bat
```

### Problema 3: "Email already registered"
```
Causa: El email ya existe en OpenCart

Solución:
1. Abrir: src\main\resources\testData.xlsx
2. Ir a la hoja: UsuariosRegistro
3. Cambiar los emails a únicos:
   juan.perez.2025@test.com
   maria.gonzalez.2025@test.com
4. Guardar Excel
5. Ejecutar de nuevo las pruebas
```

### Problema 4: "Login failed pero Expected Result es Success"
```
Causa: El usuario no está registrado en OpenCart

Solución:
1. Asegurarse de que RegistroUsuarioTest se ejecutó primero
2. Verificar que los emails en LoginData coincidan con UsuariosRegistro
3. Verificar que el Expected Result sea correcto

Hoja LoginData:
juan.perez@test.com | Test123! | Success  ← Debe estar registrado
invalid@test.com | wrong | Fail  ← Se espera que falle
```

### Problema 5: "Producto no encontrado"
```
Causa: El nombre del producto no coincide con OpenCart

Solución:
1. Abrir OpenCart: https://opencart.abstracta.us/
2. Buscar el producto manualmente
3. Copiar el nombre EXACTO del producto
4. Abrir: src\main\resources\testData.xlsx
5. Hoja: ProductosBusqueda
6. Actualizar el nombre del producto
7. Guardar Excel
8. Ejecutar de nuevo
```

### Problema 6: "Tests toman mucho tiempo / Timeout"
```
Causa: Conexión lenta o sitio web lento

Solución:
1. Verificar conexión a internet
2. Probar abrir manualmente: https://opencart.abstracta.us/
3. Si el sitio responde, aumentar timeouts:
   - Abrir: src\main\java\com\demoblaze\utils\WaitHelper.java
   - Cambiar línea 19: Duration.ofSeconds(10) → Duration.ofSeconds(20)
   - Guardar
   - Ejecutar de nuevo
```

---

## 📸 CAPTURAR EVIDENCIAS

### Durante la Ejecución

```
Capturas recomendadas:

1. PowerShell ejecutando mvn test
   - Capturar: Inicio de ejecución

2. Chrome con OpenCart
   - Capturar: Formulario de registro llenándose
   - Capturar: Página de login
   - Capturar: Búsqueda de producto
   - Capturar: Producto agregándose al carrito
   - Capturar: Carrito con productos

3. PowerShell con resultados
   - Capturar: Mensaje "BUILD SUCCESS"
   - Capturar: Summary con Tests run, Failures, etc.
```

### Después de la Ejecución

```
Capturas recomendadas:

1. Archivo Excel
   - Capturar: Hoja UsuariosRegistro
   - Capturar: Hoja LoginData
   - Capturar: Hoja ProductosBusqueda

2. Reportes HTML
   - Capturar: index.html con resumen
   - Capturar: Detalle de cada test

3. Log personalizado
   - Capturar: Sección de registros
   - Capturar: Sección de logins
   - Capturar: Sección de productos agregados
   - Capturar: Verificación de carrito
```

---

## 🎓 PREPARAR PRESENTACIÓN

### Demostración en Vivo

```
Orden sugerido:

1. Mostrar estructura del proyecto (5 min)
   - Carpeta pages/ → Patrón POM
   - Carpeta utils/ → Utilidades
   - Carpeta test/ → Pruebas
   - pom.xml → Dependencias

2. Mostrar Excel con datos (2 min)
   - Abrir testData.xlsx
   - Explicar las 3 hojas

3. Ejecutar pruebas en vivo (10 min)
   - Doble clic en ejecutar-pruebas.bat
   - Explicar qué hace cada test mientras se ejecuta
   - Mostrar Chrome automatizado

4. Mostrar resultados (5 min)
   - Abrir reporte HTML
   - Abrir log personalizado
   - Explicar el formato

5. Mostrar código (5 min)
   - Abrir una clase Page → Explicar POM
   - Abrir ExcelReader → Explicar Apache POI
   - Abrir un Test → Explicar Hard/Soft Assert
   - Abrir WaitHelper → Explicar esperas

6. Preguntas (3 min)
```

---

## ✅ CHECKLIST PRE-ENTREGA

```
□ Java instalado y verificado
□ Maven instalado y verificado
□ Excel generado con datos
□ Pruebas ejecutadas exitosamente al menos 1 vez
□ Reportes HTML generados
□ Logs generados con toda la información
□ Capturas de pantalla tomadas (ver EVIDENCIAS.md)
□ Documentación revisada
□ Presentación preparada
□ Demostración practicada
```

---

**¡Estás listo! 🎉**
