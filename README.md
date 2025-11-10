#  Proyecto Final de Automatización - OpenCart

**Proyecto Transversal: Calidad de Software Aplicada a Sistemas de Dominio Específico**

Automatización de pruebas para la tienda demo de OpenCart (https://opencart.abstracta.us/) utilizando Selenium WebDriver, TestNG y Page Object Model (POM).

##  Equipo de Desarrollo

- **Juan Sebastián Ríos**
- **Fabián Saavedra**
- **Jhonatan Velasco**
- **Ian Marco Arango**

**Materia**: Calidad y Pruebas de Software  
**Objetivo**: Desarrollar un conjunto de pruebas automatizadas sobre OpenCart utilizando buenas prácticas de automatización, evaluando capacidad técnica en diseño de pruebas, estructura de framework, selectores, sincronización, validaciones y manejo de datos externos.

---

##  ¿Cómo funciona este proyecto?

Este framework de automatización está diseñado para ejecutar pruebas end-to-end sobre la tienda demo de OpenCart (https://opencart.abstracta.us/) de forma **completamente automática** y **data-driven** (guiado por datos de Excel).

###  Flujo de Ejecución Automático

1. **Inicio de Suite** (`BaseTest.java`)
   -  Configura Chrome con WebDriverManager (descarga driver automáticamente)
   -  Genera archivo Excel `testData.xlsx` con datos únicos (emails con timestamp)
   -  Inicializa sistema de logs personalizados

2. **Prueba 1: Registro de Usuarios** (`RegistroUsuarioTest.java`)
   -  Lee hoja "UsuariosRegistro" del Excel
   -  Navega a página de registro
   -  Completa formulario para cada usuario
   -  Valida mensajes de éxito/error
   -  Registra resultados en log

3. **Prueba 2: Login** (`LoginTest.java`)
   -  **Registra primero 2 usuarios** para tener credenciales válidas
   -  Lee hoja "LoginData" con credenciales y resultado esperado
   -  **Sincroniza emails reales** de usuarios registrados con los casos de éxito
   -  Intenta login para cada fila (casos exitosos + casos inválidos)
   -  Compara resultado real vs esperado (Success/Fail)
   -  Valida mensajes de error en casos negativos

4. **Prueba 3: Búsqueda y Carrito** (`BusquedaYCarritoTest.java`)
   -  Lee hoja "ProductosBusqueda" con productos a buscar
   -  Busca cada producto (por categoría y/o nombre)
   -  Agrega al carrito con cantidad específica
   -  **Completa automáticamente opciones requeridas** (color, fecha, etc.)
   -  Verifica que todos los productos estén en el carrito

5. **Fin de Suite**
   -  Genera reporte TestNG con estadísticas
   -  Cierra log con resumen de ejecución
   -  Cierra navegador

###  Características Clave

- **Data-Driven**: Todos los datos vienen de Excel, fácil de modificar sin tocar código
- **Page Object Model**: Separación clara entre lógica de prueba y elementos de página
- **Auto-Regeneración**: Excel se crea automáticamente con emails únicos cada ejecución
- **Smart Options**: Productos con opciones (Canon: color, HP: fecha) se completan solos
- **Logs Detallados**: Archivo de texto con timestamp de cada operación
- **Soft + Hard Asserts**: Valida múltiples items sin detener ejecución completa

---

##  Cómo Ejecutar las Pruebas (IntelliJ IDEA)

### Opción 1: Suite Completa (Recomendado)

1. **Abrir el proyecto** en IntelliJ IDEA
2. Verificar que Project SDK sea **Java 17** (File  Project Structure  Project)
3. Localizar archivo `testng.xml` en la raíz del proyecto
4. **Click derecho** en `testng.xml`  **Run 'testng.xml'**
5. Ver ejecución en panel Run (navegador Chrome se abre automáticamente)

### Opción 2: Test Individual

Para ejecutar solo un test específico:

1. Navegar a `src/test/java/com/demoblaze/test/`
2. Abrir el test deseado:
   - `RegistroUsuarioTest.java`  Solo registro
   - `LoginTest.java`  Solo login
   - `BusquedaYCarritoTest.java`  Solo búsqueda y carrito
3. **Click derecho** en la clase  **Run '[NombreTest]'**

### Opción 3: Desde Terminal (Maven)

```powershell
# Suite completa
mvn test

# Test específico
mvn test -Dtest=LoginTest

# Con reportes detallados
mvn clean test
```

###  Qué Esperar Durante la Ejecución

**En la consola verás:**
```
========================================
Eliminando testData.xlsx previo para regenerar datos coherentes...
========================================
Generando testData.xlsx para esta ejecución...
 Archivo Excel generado exitosamente
   Tamaño: 5385 bytes
========================================
[main] INFO io.github.bonigarcia.wdm.WebDriverManager - Using chromedriver...
```

**En Chrome verás:**
- Navegación automática a OpenCart
- Formularios llenándose solos
- Productos siendo agregados al carrito
- Todo sin intervención manual

**Al terminar:**
```
========================================
Log guardado en: src\main\resources\logs\TestLog_20251109_120530.txt
========================================

===============================================
OpenCart Test Suite
Total tests run: 3, Passes: 3, Failures: 0, Skips: 0
===============================================
```

###  Dónde Ver los Resultados

1. **Logs Personalizados**: `src/main/resources/logs/TestLog_[fecha].txt`
   - Detalle de cada registro, login y producto agregado
   
2. **Reporte TestNG**: `target/surefire-reports/index.html`
   - Abrir en navegador para ver estadísticas visuales
   
3. **Consola de IntelliJ**: Panel Run
   - Resultado inmediato de cada test ( verde o  rojo)

---

##  Tecnologías Utilizadas

- **Java 17**
- **Selenium WebDriver 4.35.0**
- **TestNG 7.11.0**
- **Maven**
- **Apache POI 5.2.5** (Manejo de Excel)
- **WebDriverManager 6.2.0**
- **Chrome Browser**

##  Estructura del Proyecto

```
STORE_2511/
├── 📄 pom.xml                          # Configuración Maven (dependencias)
├── 📄 testng.xml                       # Suite de pruebas TestNG
├── 📘 README.md                        # Esta guía
├── 📚 INDICE_DOCUMENTACION.md          # Índice completo de documentación
├── 📖 GUIA_COMPLETA.md                 # Guía maestra (600+ líneas)
├── 📊 DOCUMENTACION_PRUEBAS.md         # Especificaciones técnicas
├── 📈 RESUMEN_EJECUTIVO.md             # Vista ejecutiva
├── 📝 EJEMPLOS_LOGS.md                 # Ejemplos de logs detallados
│
├── src/
│   ├── main/
│   │   ├── java/com/demoblaze/
│   │   │   ├── 📄 pages/               # Page Object Model (10 clases)
│   │   │   │   ├── BasePage.java       # Clase base con métodos comunes
│   │   │   │   ├── HomePage.java       # Página principal + navegación
│   │   │   │   ├── RegisterPage.java   # Formulario de registro
│   │   │   │   ├── LoginPage.java      # Autenticación y logout
│   │   │   │   ├── ProductsPage.java   # Lista de productos
│   │   │   │   ├── ProductDetailPage.java    # Detalles y agregar al carrito
│   │   │   │   ├── ProductsDetallePage.java  # Variante de detalles
│   │   │   │   ├── ProductPage.java    # Gestión de productos
│   │   │   │   ├── CartPage.java       # Carrito de compras
│   │   │   │   └── CheckoutPage.java   # Proceso de checkout
│   │   │   │
│   │   │   └── 🛠️ utils/              # Utilidades (5 clases)
│   │   │       ├── Constants.java      # Constantes globales (URLs, timeouts)
│   │   │       ├── LogWriter.java      # Sistema de logging a Excel
│   │   │       ├── WaitHelper.java     # Esperas personalizadas
│   │   │       ├── ExcelReader.java    # Lectura de datos de Excel
│   │   │       └── ExcelDataGenerator.java  # Generación automática de datos
│   │   │
│   │   └── resources/
│   │       ├── testData.xlsx           # Datos de prueba (generado auto)
│   │       ├── OpenURL_Test.xlsx       # Log de prueba URL
│   │       ├── RegistroUsuarios_Test.xlsx   # Log de registros
│   │       ├── Login_Test.xlsx         # Log de autenticación
│   │       ├── BusquedaCarrito_Test.xlsx    # Log de búsquedas
│   │       └── Compra_Test.xlsx        # Log de compras
│   │
│   └── test/
│       └── java/com/demoblaze/test/
│           ├── ✅ BaseTest.java        # Clase base (DOCUMENTADO)
│           ├── ✅ OpenURLTest.java     # Test URL (DOCUMENTADO)
│           ├── RegistroUsuarioTest.java    # Test de registro
│           ├── LoginTest.java          # Test de autenticación
│           ├── BusquedaYCarritoTest.java   # Test de búsqueda
│           └── ComprarTest.java        # Test de compra E2E
│
└── target/
    ├── surefire-reports/               # Reportes de ejecución Maven
    │   ├── index.html                  # Reporte principal HTML
    │   ├── emailable-report.html       # Reporte enviable
    │   └── testng-results.xml          # Resultados XML
    └── classes/                        # Clases compiladas
```

###  Descripción de Carpetas y Archivos

#### 📄 **Archivos de Configuración**
- **pom.xml**: Define todas las dependencias (Selenium, TestNG, POI) y plugins de Maven
- **testng.xml**: Define la suite de pruebas y el orden de ejecución

#### 📚 **Archivos de Documentación**
- **README.md**: Esta guía de inicio rápido
- **INDICE_DOCUMENTACION.md**: Índice maestro que explica CADA archivo del proyecto
- **GUIA_COMPLETA.md**: Guía completa con ejemplos de código (600+ líneas)
- **DOCUMENTACION_PRUEBAS.md**: Especificaciones técnicas de cada test
- **RESUMEN_EJECUTIVO.md**: Vista ejecutiva con métricas
- **EJEMPLOS_LOGS.md**: Ejemplos de logs generados por cada test

#### 📄 **pages/** - Page Object Model
Cada clase representa una página de la aplicación:
- **BasePage.java**: Métodos comunes (click, sendKeys, waits)
- **HomePage.java**: Navegación por categorías, búsqueda
- **RegisterPage.java**: Formulario de registro, validaciones
- **LoginPage.java**: Login, logout, verificación de sesión
- **ProductsPage.java**: Lista de productos, filtros
- **ProductDetailPage.java**: Agregar al carrito con cantidades
- **CartPage.java**: Gestión del carrito, totales
- **CheckoutPage.java**: Proceso de compra completo

#### 🛠️ **utils/** - Utilidades
Clases de soporte:
- **Constants.java**: URLs, timeouts, configuraciones globales
- **LogWriter.java**: Sistema de logging a archivos Excel con timestamps
- **WaitHelper.java**: Esperas implícitas, explícitas y personalizadas
- **ExcelReader.java**: Lectura de datos de testData.xlsx
- **ExcelDataGenerator.java**: Generación automática de datos únicos por ejecución

#### 📊 **resources/** - Recursos y Logs
- **testData.xlsx**: Datos de prueba generados automáticamente con timestamp
- **[NombreTest]_Test.xlsx**: Logs de ejecución de cada test con:
  - Timestamp de cada paso
  - Mensajes con emojis (✅ ❌ ⚠️ ℹ️)
  - Estado de ejecución
  - Detalles de cada operación

#### 🧪 **test/** - Casos de Prueba
- **BaseTest.java**: Configuración de WebDriver, LogWriter, generación de datos
- **OpenURLTest.java**: Validación de URL y carga de aplicación
- **RegistroUsuarioTest.java**: Registro de 5 usuarios, validaciones
- **LoginTest.java**: Login exitoso, credenciales inválidas
- **BusquedaYCarritoTest.java**: Búsqueda y agregado al carrito
- **ComprarTest.java**: Proceso completo de compra E2E

#### 📈 **target/** - Resultados de Compilación y Ejecución
- **surefire-reports/**: Reportes HTML y XML de TestNG
- **classes/**: Clases Java compiladas

---

##  ¿Qué Hace Cada Componente?

###  Page Objects - Representación de Páginas

#### **BasePage.java** - Clase Base
**Funcionalidad:**
- Métodos comunes para todas las páginas
- Gestión de navegación y elementos
- Implementación de esperas

**Métodos principales:**
```java
navigateTo(url)              // Navegar a una URL
clickElement(locator)        // Click en elemento
sendKeys(locator, text)      // Escribir texto
waitForElement(locator)      // Esperar elemento
isElementVisible(locator)    // Verificar visibilidad
```

#### **HomePage.java** - Página Principal
**Funcionalidad:**
- Navegación por categorías
- Búsqueda de productos
- Links de login/registro

**Métodos principales:**
```java
selectCategory(category)     // Seleccionar categoría
searchProduct(productName)   // Buscar producto
clickLogin()                 // Ir a login
clickSignUp()                // Ir a registro
isUserLoggedIn()            // Verificar sesión
```

#### **RegisterPage.java** - Registro de Usuarios
**Funcionalidad:**
- Formulario de registro completo
- Validaciones de campos
- Mensajes de éxito/error

**Métodos principales:**
```java
registerUser(userData)           // Registrar usuario completo
fillRegistrationForm(data)       // Llenar formulario
acceptPrivacyPolicy()            // Aceptar políticas
getSuccessMessage()              // Obtener mensaje éxito
getErrorMessage()                // Obtener mensaje error
```

#### **LoginPage.java** - Autenticación
**Funcionalidad:**
- Login con credenciales
- Validación de sesión
- Logout y limpieza de cookies

**Métodos principales:**
```java
login(email, password)       // Iniciar sesión
isUserLoggedIn()            // Verificar sesión activa
logout()                    // Cerrar sesión
getErrorMessage()           // Obtener mensaje error
```

#### **ProductDetailPage.java** - Detalles de Producto
**Funcionalidad:**
- Ver detalles del producto
- Agregar al carrito con cantidades
- Manejo de opciones (color, talla, etc.)

**Métodos principales:**
```java
addToCartWithQuantity(qty)    // Agregar con cantidad específica
getProductName()              // Obtener nombre producto
getProductPrice()             // Obtener precio
getProductDescription()       // Obtener descripción
```

#### **CartPage.java** - Carrito de Compras
**Funcionalidad:**
- Listar productos en carrito
- Calcular totales
- Proceder al checkout

**Métodos principales:**
```java
getCartItemCount()              // Contar productos
isProductInCart(productName)    // Verificar producto
getCartTotal()                  // Obtener total
proceedToCheckout()             // Ir a checkout
removeProduct(productName)      // Eliminar producto
```

#### **CheckoutPage.java** - Proceso de Compra
**Funcionalidad:**
- Formulario de facturación completo
- **Auto-selección de región con JavaScript**
- Confirmación de orden

**Métodos principales:**
```java
fillBillingDetails(data)     // Llenar datos facturación
completeCheckout()           // Completar compra
selectPaymentMethod(method)  // Seleccionar método pago
confirmOrder()               // Confirmar orden
getOrderNumber()             // Obtener número de orden
```

**Característica especial:**
```java
// Auto-selección de región usando JavaScript
// Evita problemas con dropdowns complejos
String script = "document.querySelector('#input-payment-zone').value = '...';";
((JavascriptExecutor) driver).executeScript(script);
```

###  Utils - Utilidades del Framework

#### **Constants.java** - Constantes Globales
**Funcionalidad:**
- URLs de la aplicación
- Timeouts configurables
- Rutas de archivos

**Constantes principales:**
```java
BASE_URL                    // URL base de la aplicación
DEFAULT_TIMEOUT             // Timeout por defecto
EXPLICIT_WAIT              // Espera explícita
TEST_DATA_FILE             // Ruta del Excel
LOG_DIRECTORY              // Directorio de logs
```

#### **LogWriter.java** - Sistema de Logging
**Funcionalidad:**
- Registro de pasos de prueba en Excel
- Timestamps automáticos
- Formato con emojis y colores

**Métodos principales:**
```java
logMessage(message)         // Log mensaje simple
logSuccess(message)         // Log éxito (✅)
logError(message)           // Log error (❌)
logWarning(message)         // Log warning (⚠️)
logInfo(message)            // Log información (ℹ️)
closeLog()                  // Cerrar archivo log
```

**Formato del log:**
```
| Timestamp | Paso | Estado | Mensaje |
|-----------|------|--------|---------|
| 13:02:30  | 1    | ✅     | Login exitoso |
| 13:02:35  | 2    | ℹ️     | Agregando 2× iMac |
| 13:02:40  | 3    | ✅     | Producto agregado |
```

#### **WaitHelper.java** - Gestión de Esperas
**Funcionalidad:**
- Esperas implícitas y explícitas
- Condiciones personalizadas
- Polling configurable

**Métodos principales:**
```java
waitForElementVisible(locator)      // Esperar visible
waitForElementClickable(locator)    // Esperar clickeable
waitForElementPresent(locator)      // Esperar presente
waitForPageLoad()                   // Esperar carga página
waitForAjax()                       // Esperar AJAX
customWait(condition, timeout)      // Espera personalizada
```

**Tipos de waits:**
- **Implicit Wait**: 10 segundos por defecto
- **Explicit Wait**: Hasta 30 segundos
- **Fluent Wait**: Con polling personalizado

#### **ExcelReader.java** - Lectura de Datos
**Funcionalidad:**
- Leer datos de testData.xlsx
- Convertir a estructuras de datos
- Validación de formato

**Métodos principales:**
```java
readUserData()              // Leer datos de usuarios
readProductData()           // Leer datos de productos
readTestData(sheetName)     // Leer hoja específica
getCellValue(row, col)      // Obtener valor de celda
```

**Estructura esperada del Excel:**
```
Sheet: UserData
| Email | Password | FirstName | LastName |

Sheet: ProductData
| ProductName | Quantity | Category |
```

#### **ExcelDataGenerator.java** - Generación de Datos
**Funcionalidad:**
- Generación automática de testData.xlsx
- **Datos únicos por ejecución (timestamp)**
- Múltiples hojas de datos

**Estrategia:**
```
1. Generar timestamp único (yyyyMMdd_HHmmss)
2. Crear 5 usuarios con emails únicos
3. Generar datos de facturación aleatorios
4. Crear datos de productos de prueba
5. Guardar en src/main/resources/testData.xlsx
```

**¿Por qué timestamp en emails?**
- ✅ Evita conflictos entre ejecuciones
- ✅ Permite ejecuciones paralelas
- ✅ Facilita debugging (identificar ejecución)
- ✅ Emails únicos garantizados

**Hojas generadas:**
```
UsuariosRegistro: 5 usuarios únicos
BillingData: 5 direcciones completas
ProductData: Productos de prueba
```

###  Tests - Casos de Prueba

#### **BaseTest.java** - Configuración Base
**Funcionalidad:**
- Setup de WebDriver (Chrome)
- Inicialización de LogWriter
- Generación de testData.xlsx
- TearDown y limpieza

**Proceso de setup (6 pasos):**
```
1. Generar datos de prueba (Excel)
2. Configurar ChromeOptions
3. Inicializar WebDriver con WebDriverManager
4. Configurar timeouts (implícitos/explícitos)
5. Inicializar LogWriter
6. Maximizar ventana
```

#### **OpenURLTest.java** - Test de URL
**Funcionalidad:**
- Validar apertura de la aplicación
- Verificar URL correcta
- Capturar título de página

**Flujo:**
```
1. Navegar a BASE_URL
2. Esperar carga completa
3. Verificar URL esperada
4. Capturar título
5. Log de resultados
```

#### **RegistroUsuarioTest.java** - Test de Registro
**Funcionalidad:**
- Registrar 5 usuarios en batch
- Validar mensajes de éxito
- Verificar duplicados (caso negativo)
- Gestionar sesiones post-registro

**Flujo:**
```
1. Leer usuarios de Excel
2. Para cada usuario:
   - Navegar a página registro
   - Llenar formulario
   - Aceptar políticas
   - Enviar
   - Validar mensaje
   - Hacer logout
3. Log de resumen
```

#### **LoginTest.java** - Test de Autenticación
**Funcionalidad:**
- Login con credenciales válidas
- Validar sesiones activas
- Probar credenciales inválidas
- Logout completo

**Casos cubiertos:**
```
✅ Login exitoso (2 usuarios)
❌ Credenciales inválidas
❌ Campos vacíos
❌ Password incorrecta
✅ Logout y limpieza de cookies
```

#### **BusquedaYCarritoTest.java** - Test de Búsqueda
**Funcionalidad:**
- Búsqueda de productos por nombre
- Búsqueda por categoría
- Agregar al carrito con cantidades
- Validar totales

**Flujo:**
```
1. Leer productos de Excel
2. Para cada producto:
   - Buscar por categoría (opcional)
   - Buscar por nombre
   - Ir a detalles
   - Agregar cantidad especificada
3. Ir al carrito
4. Validar todos los productos
5. Verificar total
```

#### **ComprarTest.java** - Test de Compra E2E
**Funcionalidad:**
- Proceso completo de compra
- Login → Productos → Carrito → Checkout
- Validación de cantidades específicas
- Llenado automático de formulario

**Flujo completo:**
```
1. Login con usuario válido
2. Navegar a categoría Laptops
3. Agregar 2× iMac al carrito
4. Navegar a categoría Laptops nuevamente
5. Agregar 3× MacBook al carrito
6. Ir al carrito y verificar:
   - 2× iMac presentes
   - 3× MacBook presentes
   - Total correcto
7. Proceder a checkout
8. Llenar datos de facturación
9. Seleccionar región automáticamente
10. Confirmar orden
11. Validar orden completada
12. Log detallado de cada paso
```

**Características especiales:**
- ✅ Cantidades exactas: 2 iMac + 3 MacBook
- ✅ Auto-selección de región con JavaScript
- ✅ Logging completo en `Compra_Test.xlsx`
- ✅ Validaciones exhaustivas en cada paso

---

##  Documentación del Código

###  Estado de Documentación

Este proyecto cuenta con **documentación completa** en todos los niveles:

#### ✅ **Código Completamente Documentado** (2 archivos)
- **BaseTest.java**: 120+ líneas de Javadoc explicando:
  - Propósito de la clase base
  - Proceso de inicialización en 6 pasos
  - Gestión del ciclo de vida de tests
  - Estrategia de generación de datos
  
- **OpenURLTest.java**: 80+ líneas de Javadoc explicando:
  - Propósito del test
  - Flujo de ejecución paso a paso
  - Validaciones implementadas
  - Dependencias y uso

#### 📝 **Estándar de Documentación Aplicado**

Todos los archivos documentados siguen este estándar:

```java
/**
 * ClassName - Descripción Breve
 * 
 * PROPÓSITO:
 *   Para qué sirve esta clase
 * 
 * FUNCIONALIDAD:
 *   Qué hace exactamente
 * 
 * DEPENDENCIAS:
 *   Qué clases o recursos utiliza
 * 
 * USO:
 *   Cómo utilizarla (con ejemplos)
 * 
 * @author Team
 * @version 1.0
 * @since 2025-11-10
 */
```

**Ejemplo real de BaseTest.java:**
```java
/**
 * BaseTest - Clase Base para Todas las Pruebas
 * 
 * PROPÓSITO:
 *   Centralizar toda la configuración común que necesitan todos los tests,
 *   evitando duplicación de código y facilitando mantenimiento.
 * 
 * RESPONSABILIDADES:
 *   1. Configuración del WebDriver (Chrome con opciones optimizadas)
 *   2. Inicialización del sistema de logging (LogWriter)
 *   3. Generación automática de datos de prueba (testData.xlsx)
 *   4. Gestión de timeouts y esperas
 * 
 * PATRÓN DE DISEÑO:
 *   - Template Method: Define esqueleto de configuración
 *   - Singleton-like: Una sola instancia de driver compartida
 * 
 * CICLO DE VIDA:
 *   @BeforeSuite (setup) -> [Tests hijos] -> @AfterSuite (tearDown)
 * ...
 */
```

###  Documentación Disponible

####  Para Desarrolladores
- **GUIA_COMPLETA.md** (600+ líneas):
  - Estructura completa del proyecto
  - Explicación detallada de cada clase
  - Ejemplos de código funcionales
  - Guías de ejecución

- **DOCUMENTACION_PRUEBAS.md** (300+ líneas):
  - Especificaciones técnicas de cada test
  - Flujos de prueba paso a paso
  - Validaciones implementadas
  - Datos de prueba utilizados

####  Para QA/Testers
- **EJEMPLOS_LOGS.md** (400+ líneas):
  - Ejemplos de logs de cada test
  - Cómo interpretar los resultados
  - Debugging de pruebas fallidas
  - Formato de archivos Excel

####  Para Gerentes/Stakeholders
- **RESUMEN_EJECUTIVO.md** (200+ líneas):
  - Vista ejecutiva del proyecto
  - Métricas de cobertura
  - Resultados de ejecución
  - Recomendaciones

####  Índice Completo
- **INDICE_DOCUMENTACION.md** (800+ líneas):
  - Índice maestro de toda la documentación
  - Explicación de para qué sirve cada archivo
  - Guía de uso por tipo de usuario
  - Comandos rápidos

###  Cómo Navegar la Documentación

1. **¿Primera vez?** → Empieza con **GUIA_COMPLETA.md**
2. **¿Necesitas specs técnicas?** → Lee **DOCUMENTACION_PRUEBAS.md**
3. **¿Debugging de tests?** → Consulta **EJEMPLOS_LOGS.md**
4. **¿Reporte ejecutivo?** → Revisa **RESUMEN_EJECUTIVO.md**
5. **¿Buscar algo específico?** → Usa **INDICE_DOCUMENTACION.md**

---

##  Excel y Datos de Prueba

El archivo `testData.xlsx` se crea automáticamente al iniciar la suite. Se fuerza regeneración cada vez para garantizar:
- Correos únicos para registro (timestamp en el email)
- Coincidencia entre usuarios registrados y filas de Login marcadas como `Success`

Hojas generadas:

####  UsuariosRegistro
| First Name | Last Name | E-Mail (único) | Telephone | Password |
|------------|-----------|----------------|-----------|----------|
| Juan | Pérez | juan.perez+YYYYMMDD_HHMMSS@test.com | 3001234567 | Test123! |
| María | González | maria.gonzalez+YYYYMMDD_HHMMSS@test.com | 3007654321 | Test456! |
| Carlos | Rodríguez | carlos.rodriguez+TIMESTAMP@test.com | 3009876543 | Test789! |
| Ana | Martínez | ana.martinez+TIMESTAMP@test.com | 3005551234 | Test321! |
| Luis | García | luis.garcia+TIMESTAMP@test.com | 3008887777 | Test654! |

####  LoginData
| Email | Password | Expected Result |
|-------|----------|-----------------|
| juan.perez+TIMESTAMP@test.com | Test123! | Success |
| maria.gonzalez+TIMESTAMP@test.com | Test456! | Success |
| usuario.invalido@test.com | password_invalido | Fail |
| (vacío) | (vacío) | Fail |
| test@test.com | wrongpassword | Fail |

####  ProductosBusqueda
| Categoria | SubCategoria | Producto | Cantidad |
|-----------|--------------|----------|----------|
| Desktops | PC | HP LP3065 | 1 |
| Laptops & Notebooks | (vacío) | MacBook | 2 |
| Components | Monitors | Apple Cinema 30 | 1 |
| (vacío) | (vacío) | iPhone | 1 |
| Cameras | (vacío) | Canon EOS 5D | 1 |

Los productos con opciones (ej. Canon EOS 5D: color; HP LP3065: fecha) se rellenan automáticamente en el código (`ProductDetailPage`) sin necesidad de columnas extra.

---

##  Resultados

**Logs Personalizados**: `src/main/resources/logs/TestLog_[fecha].txt`
- Detalle de cada registro, login y producto agregado con timestamps

**Reporte TestNG**: `target/surefire-reports/index.html`
- Estadísticas visuales y resultados completos

---

##  Requisitos del Proyecto (Cumplimiento)

### Tecnologías Implementadas
-  Java 17 + Selenium WebDriver 4.35.0
-  Maven + Apache POI 5.2.5
-  Patrón Page Object Model (6 clases)
-  TestNG 7.11.0

### Casos de Prueba
1.  Registro de Usuario (Excel  Formulario  Validación)
2.  Inicio de Sesión (Credenciales válidas/inválidas)
3.  Búsqueda y Carrito (Ciclo de productos)
4.  Verificación de Carrito (Comparación con Excel)

### Requisitos Técnicos
-  POM completo en `pages/`
-  Selectores CSS y XPath estables
-  Esperas: Implícitas, Explícitas, Personalizadas
-  Apache POI para lectura de Excel
-  Hard Assert y Soft Assert
-  Logs con timestamp automático

### Entregables
-  Repositorio Git completo
-  Archivos Excel (generación automática)
-  Logs y reportes TestNG
-  Documentación (README + estrategia)

---

##  Equipo de Desarrollo

**Proyecto Final - Calidad y Pruebas de Software**

- Juan Sebastián Ríos
- Fabián Saavedra
- Jhonatan Velasco
- Ian Marco Arango

---

**Universidad San Buenaventura Cali | 2025**

---
