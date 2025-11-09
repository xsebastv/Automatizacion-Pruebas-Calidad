# 🚀 GUÍA DE INICIO RÁPIDO

## Pasos para ejecutar el proyecto

### ✅ PASO 1: Verificar Prerequisitos

1. **Java 17 o superior instalado**
   ```powershell
   java -version
   ```
   Si no está instalado, descarga desde: https://adoptium.net/

2. **Maven instalado**
   ```powershell
   mvn -version
   ```
   Si no está instalado, descarga desde: https://maven.apache.org/download.cgi

3. **Chrome Browser** (última versión instalada)

---

### ✅ PASO 2: Generar Archivo Excel

**Opción A - Usando el script (Recomendado):**
```
Doble clic en: generar-excel.bat
```

**Opción B - Manualmente con Maven:**
```powershell
mvn clean compile
mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"
```

**Resultado esperado:**
- Se crea el archivo: `src\main\resources\testData.xlsx`
- Mensaje: "Archivo Excel creado exitosamente"

---

### ✅ PASO 3: Revisar/Editar Datos de Prueba (Opcional)

1. Abre el archivo: `src\main\resources\testData.xlsx`
2. Revisa las 3 hojas:
   - **UsuariosRegistro**: Datos para registrar usuarios
   - **LoginData**: Credenciales para login
   - **ProductosBusqueda**: Productos para buscar y agregar al carrito
3. Modifica los datos si lo deseas
4. Guarda el archivo

---

### ✅ PASO 4: Ejecutar las Pruebas

**Opción A - Usando el script (Recomendado):**
```
Doble clic en: ejecutar-pruebas.bat
```

**Opción B - Manualmente con Maven:**
```powershell
mvn clean test
```

**Lo que verás:**
- Chrome se abrirá automáticamente
- Las pruebas se ejecutarán una por una
- Al finalizar, se generarán los reportes

---

### ✅ PASO 5: Ver Resultados

Los resultados se generan automáticamente en dos formatos:

#### 1. Reportes HTML de TestNG
📁 Ubicación: `target\surefire-reports\index.html`

**Abrir en navegador:**
```powershell
start target\surefire-reports\index.html
```

**Contenido:**
- Resumen de pruebas (Passed/Failed)
- Tiempo de ejecución
- Detalles de cada test
- Stack traces de errores

#### 2. Logs Personalizados
📁 Ubicación: `src\main\resources\logs\TestLog_YYYYMMDD_HHMMSS.txt`

**Contenido:**
- ✅ Registros exitosos/fallidos con emails
- ✅ Logins exitosos/fallidos con credenciales
- ✅ Productos agregados (categoría, subcategoría, producto, cantidad)
- ✅ Verificación final del carrito

**Ver el último log:**
```powershell
# Listar logs
dir "src\main\resources\logs\"

# Abrir el más reciente
notepad "src\main\resources\logs\TestLog_YYYYMMDD_HHMMSS.txt"
```

---

## 🎯 Estructura de Ejecución

```
1. RegistroUsuarioTest
   └─ Lee UsuariosRegistro del Excel
   └─ Registra cada usuario
   └─ Verifica mensaje de éxito
   └─ Escribe resultado en log

2. LoginTest
   └─ Lee LoginData del Excel
   └─ Prueba cada credencial
   └─ Valida según Expected Result
   └─ Escribe resultado en log

3. BusquedaYCarritoTest
   └─ Lee ProductosBusqueda del Excel
   └─ Para cada producto:
      ├─ Busca el producto
      ├─ Verifica que aparece
      ├─ Agrega al carrito
      └─ Escribe en log (categoría, subcategoría, producto, cantidad)
   └─ Verifica todos los productos en el carrito
   └─ Escribe resultado de verificación
```

---

## 🔥 Comandos Útiles

### Ver estructura del proyecto
```powershell
tree /F /A
```

### Limpiar proyecto (eliminar compilaciones previas)
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

### Ver dependencias del proyecto
```powershell
mvn dependency:tree
```

---

## ❓ Solución de Problemas

### ❌ "mvn no se reconoce como comando"
**Solución:** Maven no está en el PATH
1. Descarga Maven: https://maven.apache.org/download.cgi
2. Descomprime en C:\Program Files\Apache\maven
3. Agrega al PATH: `C:\Program Files\Apache\maven\bin`
4. Reinicia PowerShell y prueba: `mvn -version`

### ❌ "No se encuentra testData.xlsx"
**Solución:** Generar el archivo Excel
```powershell
mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"
```

### ❌ "Tests fallan por timeout"
**Solución 1:** Verificar conexión a internet
**Solución 2:** Verificar que el sitio está disponible: https://opencart.abstracta.us/
**Solución 3:** Aumentar timeouts en `src\main\java\com\demoblaze\utils\WaitHelper.java`

### ❌ "Email already registered"
**Solución:** El email ya existe en OpenCart
1. Abre `src\main\resources\testData.xlsx`
2. Cambia los emails en la hoja "UsuariosRegistro"
3. Usa emails únicos: `usuario123@test.com`

### ❌ "Login failed pero Expected Result es Success"
**Solución:** El usuario no está registrado
1. Verifica que el usuario existe (ejecuta primero RegistroUsuarioTest)
2. O cambia "Expected Result" a "Fail" en la hoja LoginData

---

## 📊 Entregables del Proyecto

Para la entrega final, incluir:

✅ **1. Código fuente completo**
   - Proyecto STORE_2511 completo

✅ **2. Archivos Excel**
   - `src\main\resources\testData.xlsx`

✅ **3. Evidencias de ejecución**
   - Screenshots de las pruebas ejecutándose
   - Reporte HTML: `target\surefire-reports\index.html`
   - Log: `src\main\resources\logs\TestLog_*.txt`

✅ **4. Documento de estrategia**
   - `ESTRATEGIA_AUTOMATIZACION.md`

✅ **5. README**
   - `README.md`
   - `INICIO_RAPIDO.md` (este archivo)

---

## 💡 Consejos

1. **Primera ejecución:** Ejecuta las pruebas en orden (generar Excel → ejecutar pruebas)

2. **Emails únicos:** Asegúrate de usar emails únicos en UsuariosRegistro

3. **Login Data:** Los usuarios en LoginData con "Success" deben estar registrados primero

4. **Productos:** Verifica que los nombres de productos en Excel coincidan con OpenCart

5. **Logs detallados:** Siempre revisa los logs en `src\main\resources\logs\` para detalles

6. **Chrome actualizado:** Mantén Chrome actualizado (WebDriverManager lo maneja automáticamente)

---

## 🎓 Documentación Adicional

- **README.md**: Documentación completa del proyecto
- **ESTRATEGIA_AUTOMATIZACION.md**: Estrategia detallada de automatización
- **Comentarios en código**: Cada clase tiene JavaDoc

---

**¡Listo para automatizar! 🚀**
