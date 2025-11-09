# 🛒 Proyecto de Automatización - OpenCart

Automatización de pruebas para la tienda demo de OpenCart usando Selenium WebDriver, TestNG y Page Object Model (POM). Este README concentra ahora TODA la información esencial (se eliminaron archivos Markdown redundantes por limpieza).

---

## 🚀 Inicio Rápido (IntelliJ IDEA)

1. Abrir el proyecto en IntelliJ IDEA.
2. Verificar Java y Maven (IntelliJ ya trae soporte integrado):
   - Project SDK: Java 17
3. Ejecutar la suite completa:
   - Abrir `testng.xml` → Click derecho → Run.
4. Revisar resultados: carpeta `src/main/resources/logs/` y `target/surefire-reports/`.

El archivo Excel de datos (`testData.xlsx`) se REGENERA automáticamente al inicio de cada ejecución (dentro de `BaseTest`). No necesitas pasos manuales previos.

---

## 📋 Contenido

- [Tecnologías](#tecnologías)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Instalación mínima](#instalación-mínima)
- [Excel y Datos de Prueba](#excel-y-datos-de-prueba)
- [Ejecución de Pruebas](#ejecución-de-pruebas)
- [Casos Cubiertos](#casos-de-prueba)
- [Logs y Reportes](#logs-y-reportes)
- [Troubleshooting](#troubleshooting)
- [Notas Importantes](#notas-importantes)

## 🚀 Tecnologías

- **Java 17**
- **Selenium WebDriver 4.35.0**
- **TestNG 7.11.0**
- **Maven**
- **Apache POI 5.2.5** (Manejo de Excel)
- **WebDriverManager 6.2.0**
- **Chrome Browser**

## 📁 Estructura del Proyecto

```
STORE_2511/
├── src/
│   ├── main/
│   │   ├── java/com/demoblaze/
│   │   │   ├── pages/              # Page Object Model
│   │   │   │   ├── BasePage.java
│   │   │   │   ├── RegisterPage.java
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── ProductPage.java
│   │   │   │   ├── ProductDetailPage.java
│   │   │   │   └── CartPage.java
│   │   │   └── utils/              # Utilidades
│   │   │       ├── Constants.java
│   │   │       ├── ExcelReader.java
│   │   │       ├── LogWriter.java
│   │   │       ├── WaitHelper.java
│   │   │       └── ExcelDataGenerator.java
│   │   └── resources/
│   │       ├── testData.xlsx       # Datos de entrada (se genera)
│   │       └── logs/               # Logs de ejecución
│   └── test/
│       └── java/com/demoblaze/test/
│           ├── BaseTest.java
│           ├── RegistroUsuarioTest.java
│           ├── LoginTest.java
│           └── BusquedaYCarritoTest.java
├── pom.xml
├── testng.xml
├── ESTRATEGIA_AUTOMATIZACION.md
└── README.md
```

## ⚙️ Instalación Mínima

Requisitos:
- Java 17 (el SDK configurado en IntelliJ)
- Chrome instalado (WebDriverManager descarga el driver automáticamente)

Desde terminal (opcional):
```powershell
mvn -version
java -version
```
Descarga de dependencias se hace sola al abrir el proyecto o al correr la primera prueba.

## 📊 Excel y Datos de Prueba

El archivo `testData.xlsx` se crea automáticamente al iniciar la suite. Se fuerza regeneración cada vez para garantizar:
- Correos únicos para registro (timestamp en el email)
- Coincidencia entre usuarios registrados y filas de Login marcadas como `Success`

Hojas generadas:

#### 📊 UsuariosRegistro
| First Name | Last Name | E-Mail (único) | Telephone | Password |
|------------|-----------|----------------|-----------|----------|
| Juan | Pérez | juan.perez+YYYYMMDD_HHMMSS@test.com | 3001234567 | Test123! |
| María | González | maria.gonzalez+YYYYMMDD_HHMMSS@test.com | 3007654321 | Test456! |
| Carlos | Rodríguez | carlos.rodriguez+TIMESTAMP@test.com | 3009876543 | Test789! |
| Ana | Martínez | ana.martinez+TIMESTAMP@test.com | 3005551234 | Test321! |
| Luis | García | luis.garcia+TIMESTAMP@test.com | 3008887777 | Test654! |

#### 📊 LoginData
| Email | Password | Expected Result |
|-------|----------|-----------------|
| juan.perez+TIMESTAMP@test.com | Test123! | Success |
| maria.gonzalez+TIMESTAMP@test.com | Test456! | Success |
| usuario.invalido@test.com | password_invalido | Fail |
| (vacío) | (vacío) | Fail |
| test@test.com | wrongpassword | Fail |

#### 📊 ProductosBusqueda
| Categoria | SubCategoria | Producto | Cantidad |
|-----------|--------------|----------|----------|
| Desktops | PC | HP LP3065 | 1 |
| Laptops & Notebooks | (vacío) | MacBook | 2 |
| Components | Monitors | Apple Cinema 30 | 1 |
| (vacío) | (vacío) | iPhone | 1 |
| Cameras | (vacío) | Canon EOS 5D | 1 |

Los productos con opciones (ej. Canon EOS 5D: color; HP LP3065: fecha) se rellenan automáticamente en el código (`ProductDetailPage`) sin necesidad de columnas extra.

### 2. Personalizar Datos (Opcional)

Puedes editar el archivo `testData.xlsx` con Excel para agregar, modificar o eliminar datos de prueba.

## ▶️ Ejecución de Pruebas

### Compilar el Proyecto
```bash
mvn clean compile
```

### Ejecutar Todas las Pruebas
```bash
mvn test
```

### Ejecutar con TestNG Suite
```powershell
mvn test -DsuiteXmlFile=testng.xml
```

### Ejecutar una Clase de Test Específica
```bash
mvn test -Dtest=RegistroUsuarioTest
mvn test -Dtest=LoginTest
mvn test -Dtest=BusquedaYCarritoTest
```

## 📊 Logs y Reportes

### Reportes de TestNG

Los reportes se generan automáticamente en:
```
target/surefire-reports/
├── index.html                    # Reporte principal
├── emailable-report.html         # Reporte para enviar por email
└── testng-results.xml            # Resultados en XML
```

Para ver los reportes:
```bash
# En Windows PowerShell
start target/surefire-reports/index.html

# O abrir manualmente en el navegador
```

### Logs Personalizados

Los logs de ejecución se guardan en:
```
src/main/resources/logs/TestLog_YYYYMMDD_HHMMSS.txt
```

**Contenido del Log:**
- ✅ Resultado de registros (exitoso/fallido)
- ✅ Resultado de logins con mensajes
- ✅ Productos agregados: categoría, subcategoría, producto, cantidad
- ✅ Verificación de productos en el carrito
- ✅ Timestamps de cada operación

**Ejemplo:**
```
[REGISTRO] 08/11/2025 14:30:45
  Email: juan.perez@test.com
  Estado: EXITOSO
  Mensaje: Your Account Has Been Created!

[PRODUCTO AGREGADO] 08/11/2025 14:32:10
  Categoría: Desktops
  Subcategoría: PC
  Producto: HP LP3065
  Cantidad: 1
  Estado: EXITOSO
```

## 📝 Casos de Prueba

### 1️⃣ Registro de Usuarios (RegistroUsuarioTest)
- Lee usuarios desde Excel (UsuariosRegistro)
- Completa el formulario de registro
- Verifica mensaje de éxito
- Registra resultado en el log

### 2️⃣ Inicio de Sesión (LoginTest)
- Lee credenciales desde Excel (LoginData)
- Valida login exitoso y fallido
- Compara con resultado esperado
- Maneja errores de credenciales inválidas

### 3️⃣ Búsqueda y Agregado al Carrito (BusquedaYCarritoTest)
- Lee productos desde Excel (ProductosBusqueda)
- Busca productos por categoría/nombre
- Verifica que aparecen en resultados
- Agrega al carrito con cantidad específica
- Registra cada producto en el log

### 4️⃣ Verificación del Carrito
- Verifica que todos los productos agregados están en el carrito
- Compara cantidad esperada vs encontrada
- Registra resultado de verificación

## 🎯 Características Principales

### ✅ Page Object Model (POM)
- Separación clara entre lógica de prueba y página
- Fácil mantenimiento y escalabilidad
- Código reutilizable

### ✅ Manejo de Esperas
- **Esperas Implícitas**: Configuradas globalmente
- **Esperas Explícitas**: Para elementos específicos
- **Esperas Personalizadas**: Para casos especiales

### ✅ Aserciones
- **Hard Assert**: Para validaciones críticas
- **Soft Assert**: Para validar múltiples items sin detener la ejecución

### ✅ Selectores Robustos
- CSS Selectors como primera opción
- XPath para búsquedas complejas
- Selectores estables y mantenibles

### ✅ Data-Driven Testing
- Datos de prueba en Excel
- Fácil de actualizar sin modificar código
- Múltiples conjuntos de datos

## 🔍 Troubleshooting

### Error: "No se encuentra el archivo Excel"
Se regenera solo al inicio. Si persiste, verifica permisos y carpeta `src/main/resources/`.

### Error: "WebDriver no encontrado"
WebDriverManager maneja todo. Verifica conexión y que Chrome esté instalado.

### Tests fallan por timeout
Sitio lento o cortes. Ajustar en `WaitHelper` o reintentar.

### Problemas con Maven
```powershell
mvn clean install -U
```

## 📚 Documentación Adicional
Se ha mantenido únicamente este README y la estrategia completa en `ESTRATEGIA_AUTOMATIZACION.md` para consulta extendida.

## 👥 Equipo

Proyecto desarrollado para el curso de Calidad y Pruebas de Software

## 📄 Licencia

Proyecto educativo - Universidad

---

## 🎓 Notas Importantes

1. Registro y Login sincronizados por regeneración automática del Excel.
2. Emails generados con timestamp evitando duplicados en ejecuciones consecutivas.
3. Productos con opciones se completan automáticamente (no ampliar Excel).
4. Logs detallados para evidencia académica.
5. Reportes TestNG en `target/surefire-reports/`.

---

**¡Listo para ejecutar y documentar! 🚀**
