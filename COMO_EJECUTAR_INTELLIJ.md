# 🚀 Cómo Ejecutar las Pruebas en IntelliJ IDEA

## ✅ Cambios Realizados

He ajustado el proyecto para que **genere automáticamente** el archivo Excel con datos de prueba la primera vez que ejecutes cualquier test.

### 📝 Archivos Modificados:

1. **BaseTest.java**
   - ✅ Agregado método `generateExcelIfNotExists()`
   - ✅ Valida si existe `testData.xlsx` antes de cada ejecución
   - ✅ Si no existe, lo genera automáticamente

2. **ExcelDataGenerator.java**
   - ✅ Crea el directorio `src/main/resources` si no existe
   - ✅ Mejores mensajes de confirmación

---

## 🎯 Cómo Ejecutar las Pruebas

### Opción 1: Ejecutar Suite Completa (Recomendado)

1. Ve a `src/test/java/com/demoblaze/test/`
2. Click derecho en **`BaseTest.java`**
3. Selecciona **"Run 'BaseTest'"**
4. Esto ejecutará todas las pruebas en orden

### Opción 2: Ejecutar Test Individual

1. Abre el test que quieres ejecutar:
   - `RegistroUsuarioTest.java`
   - `LoginTest.java`
   - `BusquedaYCarritoTest.java`
   
2. Click derecho en el archivo
3. Selecciona **"Run 'NombreDelTest'"**

### Opción 3: Ejecutar con TestNG XML

1. Abre el archivo `testng.xml` en la raíz del proyecto
2. Click derecho en el archivo
3. Selecciona **"Run 'testng.xml'"**

---

## 📊 Qué Esperar en la Primera Ejecución

### Console Output:

```
========================================
Archivo Excel no encontrado. Generando testData.xlsx...
========================================
Directorio creado: src\main\resources
✓ Archivo Excel creado exitosamente en: src/main/resources/testData.xlsx
========================================
[main] INFO io.github.bonigarcia.wdm.WebDriverManager - Using chromedriver...
```

### Archivos Generados Automáticamente:

- ✅ `src/main/resources/testData.xlsx` - Datos de prueba
- ✅ `src/main/resources/logs/TestLog_YYYYMMDD_HHMMSS.txt` - Logs de ejecución

---

## 🔍 Solución al Error Anterior

**Error anterior:**
```
Error al leer el archivo Excel: Cannot find zip signature within the file
java.util.zip.ZipException: Cannot find zip signature within the file
```

**Causa:** El archivo `testData.xlsx` no existía o estaba corrupto.

**Solución:** Ahora el `BaseTest` detecta automáticamente si el archivo no existe y lo genera antes de ejecutar las pruebas.

---

## 📝 Verificar Ejecución Exitosa

### 1. Console debe mostrar:

```
========================================
Archivo Excel encontrado: src/main/resources/testData.xlsx
========================================

=== INICIO DE EJECUCIÓN DE PRUEBAS ===
Navegador: Chrome
URL Base: https://opencart.abstracta.us/

[TestNG] Running test...
```

### 2. Al final verás:

```
========================================
Log guardado en: src\main\resources\logs\TestLog_20251108_234031.txt
========================================

===============================================
Default Suite
Total tests run: X, Passes: X, Failures: 0, Skips: 0
===============================================

Process finished with exit code 0
```

### 3. Archivos creados:

- `src/main/resources/testData.xlsx` - Con 3 hojas: UsuariosRegistro, LoginData, ProductosBusqueda
- `src/main/resources/logs/TestLog_*.txt` - Log detallado de la ejecución

---

## 🎓 Orden de Ejecución de Tests

El archivo `testng.xml` ejecuta las pruebas en este orden:

1. **RegistroUsuarioTest** - Registra usuarios desde Excel
2. **LoginTest** - Valida login con credenciales válidas/inválidas
3. **BusquedaYCarritoTest** - Busca productos y los agrega al carrito

---

## ⚠️ Advertencias Normales (Ignorar)

Estos warnings son **normales** y **NO afectan** la ejecución:

```
ADVERTENCIA: Unable to find an exact match for CDP version 142
ERROR StatusLogger Log4j2 could not find a logging implementation
```

Son advertencias de compatibilidad de versiones pero las pruebas funcionan correctamente.

---

## 🐛 Troubleshooting

### Si el Excel no se genera automáticamente:

1. Verifica que existe el directorio `src/main/resources`
2. Ejecuta manualmente el generador:
   - Click derecho en `ExcelDataGenerator.java`
   - Selecciona **"Run 'ExcelDataGenerator.main()'"**

### Si Chrome no se abre:

1. Verifica que tienes Google Chrome instalado
2. WebDriverManager descargará automáticamente el chromedriver correcto

### Si aparecen errores de compilación:

1. Click derecho en el proyecto
2. Selecciona **"Maven"** → **"Reload Project"**
3. Espera a que descargue todas las dependencias

---

## 📚 Siguientes Pasos

1. ✅ Ejecuta las pruebas
2. ✅ Verifica los logs en `src/main/resources/logs/`
3. ✅ Revisa el archivo Excel generado en `src/main/resources/testData.xlsx`
4. 📸 Captura pantallazos para tu evidencia
5. 📄 Consulta `EVIDENCIAS.md` para documentar tus resultados

---

## 💡 Tips para IntelliJ IDEA

- **Ctrl + Shift + F10** - Ejecutar el test/clase actual
- **Shift + F10** - Re-ejecutar el último test
- **Alt + 4** - Ver panel de ejecución (Run)
- **Alt + 5** - Ver panel de debugger

---

¡Listo! Ahora puedes ejecutar las pruebas sin problemas 🚀
