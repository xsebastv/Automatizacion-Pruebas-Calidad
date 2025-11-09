# 🛒 Proyecto de Automatización - OpenCart

Automatización de pruebas para la tienda OpenCart utilizando Selenium WebDriver, TestNG y el patrón Page Object Model (POM).

---

## 🚀 INICIO RÁPIDO

### ¿Primera vez aquí? Empieza por:

1. **📚 [INDICE.md](INDICE.md)** - Navegación completa del proyecto
2. **⚡ [INICIO_RAPIDO.md](INICIO_RAPIDO.md)** - Guía de 5 pasos
3. **✅ [CHECKLIST.md](CHECKLIST.md)** - Lista de verificación completa

### ¿Quieres ejecutar rápido?

```bash
# Paso 1: Generar Excel
Doble clic en: generar-excel.bat

# Paso 2: Ejecutar pruebas
Doble clic en: ejecutar-pruebas.bat
```

---

## 📋 Contenido

- [Tecnologías](#tecnologías)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Ejecución de Pruebas](#ejecución-de-pruebas)
- [Resultados](#resultados)
- [Documentación](#documentación)

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

## ⚙️ Instalación

### Prerequisitos

1. **Java 17 o superior**
   ```bash
   java -version
   ```

2. **Maven**
   ```bash
   mvn -version
   ```

3. **Chrome Browser** (última versión)

### Pasos de Instalación

1. **Clonar o descargar el proyecto**

2. **Instalar dependencias**
   ```bash
   mvn clean install
   ```

## 🔧 Configuración

### 1. Generar el Archivo Excel con Datos de Prueba

Ejecutar el generador de datos de Excel:

```bash
mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"
```

Esto creará el archivo `src/main/resources/testData.xlsx` con tres hojas:

#### 📊 UsuariosRegistro
| First Name | Last Name | E-Mail | Telephone | Password |
|------------|-----------|--------|-----------|----------|
| Juan | Pérez | juan.perez@test.com | 3001234567 | Test123! |
| María | González | maria.gonzalez@test.com | 3007654321 | Test456! |
| ... | ... | ... | ... | ... |

#### 📊 LoginData
| Email | Password | Expected Result |
|-------|----------|-----------------|
| juan.perez@test.com | Test123! | Success |
| usuario.invalido@test.com | password_invalido | Fail |
| ... | ... | ... |

#### 📊 ProductosBusqueda
| Categoria | SubCategoria | Producto | Cantidad |
|-----------|--------------|----------|----------|
| Desktops | PC | HP LP3065 | 1 |
| Laptops & Notebooks | | MacBook | 2 |
| ... | ... | ... | ... |

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
```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Ejecutar una Clase de Test Específica
```bash
mvn test -Dtest=RegistroUsuarioTest
mvn test -Dtest=LoginTest
mvn test -Dtest=BusquedaYCarritoTest
```

## 📊 Resultados

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

## 🔍 Solución de Problemas

### Error: "No se encuentra el archivo Excel"
```bash
# Generar el archivo Excel
mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"
```

### Error: "WebDriver no encontrado"
- WebDriverManager descarga automáticamente el driver
- Asegúrate de tener conexión a internet
- Chrome debe estar instalado

### Los tests fallan por timeout
- Verificar conexión a internet
- El sitio https://opencart.abstracta.us/ debe estar disponible
- Aumentar los timeouts en WaitHelper si es necesario

### Problemas con Maven
```bash
# Limpiar y recompilar
mvn clean install -U
```

## 📚 Documentación Adicional

- **ESTRATEGIA_AUTOMATIZACION.md**: Documento completo con la estrategia de automatización
- **JavaDoc**: Comentarios en el código fuente
- **Reportes TestNG**: target/surefire-reports/

## 👥 Equipo

Proyecto desarrollado para el curso de Calidad y Pruebas de Software

## 📄 Licencia

Proyecto educativo - Universidad

---

## 🎓 Notas Importantes

1. **Primer Registro**: Los emails deben ser únicos. Si ya existe un usuario registrado, cambia el email en el Excel.

2. **Login**: Asegúrate de que los usuarios en LoginData estén previamente registrados si el Expected Result es "Success".

3. **Productos**: Los nombres de productos en el Excel deben coincidir exactamente con los del sitio OpenCart.

4. **Logs**: Revisa los logs después de cada ejecución en `src/main/resources/logs/` para detalles de la ejecución.

5. **Reportes**: Los reportes HTML de TestNG proporcionan información visual clara de los resultados.

---

**¡Disfruta automatizando! 🚀**
