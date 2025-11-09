# ESTRATEGIA DE AUTOMATIZACIÓN - OPENCART
## Proyecto Final - Calidad y Pruebas de Software

---

## 1. INFORMACIÓN GENERAL

**Proyecto:** Automatización de Pruebas para OpenCart  
**Sitio Web:** https://opencart.abstracta.us/  
**Fecha:** Noviembre 2025  
**Framework:** Selenium WebDriver + TestNG + Maven  
**Patrón de Diseño:** Page Object Model (POM)  

---

## 2. TECNOLOGÍAS UTILIZADAS

### 2.1 Herramientas y Frameworks
- **Java 17**: Lenguaje de programación principal
- **Selenium WebDriver 4.35.0**: Automatización de navegadores
- **TestNG 7.11.0**: Framework de pruebas y aserciones
- **Maven**: Gestión de dependencias y build
- **Apache POI 5.2.5**: Lectura y escritura de archivos Excel
- **WebDriverManager 6.2.0**: Gestión automática de drivers
- **SLF4J 2.0.9**: Sistema de logging

### 2.2 Navegador
- **Chrome**: Navegador principal para ejecución de pruebas

---

## 3. ESTRUCTURA DEL PROYECTO

```
STORE_2511/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/demoblaze/
│   │   │       ├── pages/          # Clases POM
│   │   │       │   ├── BasePage.java
│   │   │       │   ├── RegisterPage.java
│   │   │       │   ├── LoginPage.java
│   │   │       │   ├── ProductPage.java
│   │   │       │   ├── ProductDetailPage.java
│   │   │       │   └── CartPage.java
│   │   │       └── utils/          # Utilidades
│   │   │           ├── Constants.java
│   │   │           ├── ExcelReader.java
│   │   │           ├── LogWriter.java
│   │   │           ├── WaitHelper.java
│   │   │           └── ExcelDataGenerator.java
│   │   └── resources/
│   │       ├── testData.xlsx       # Datos de entrada
│   │       └── logs/               # Logs de ejecución
│   └── test/
│       └── java/
│           └── com/demoblaze/test/
│               ├── BaseTest.java
│               ├── RegistroUsuarioTest.java
│               ├── LoginTest.java
│               └── BusquedaYCarritoTest.java
├── pom.xml
└── testng.xml
```

---

## 4. PATRÓN DE DISEÑO: PAGE OBJECT MODEL (POM)

### 4.1 Descripción
El patrón Page Object Model separa la lógica de las pruebas de la implementación de la interfaz de usuario, creando una capa de abstracción entre los tests y las páginas web.

### 4.2 Beneficios
- **Mantenibilidad**: Cambios en la UI solo requieren actualizar las clases Page
- **Reutilización**: Métodos compartidos entre múltiples tests
- **Legibilidad**: Tests más claros y expresivos
- **Reducción de duplicación**: Centralizamos localizadores y acciones

### 4.3 Implementación

#### BasePage.java
- Clase base para todas las páginas
- Contiene métodos comunes: clickElement, writeText, getText
- Inicializa PageFactory y WaitHelper
- Maneja navegación y operaciones JavaScript

#### Páginas Específicas
Cada página hereda de BasePage y define:
- **Localizadores**: Usando @FindBy de Selenium
- **Métodos de acción**: Interacciones con elementos
- **Métodos de validación**: Verificaciones de estado

---

## 5. ESTRATEGIA DE SELECTORES

### 5.1 Prioridad de Selectores
1. **CSS Selectors** (Primera opción)
   - Rápidos y eficientes
   - Ejemplo: `input[name='email']`, `button#login-btn`

2. **XPath** (Cuando CSS no es suficiente)
   - Para navegación compleja en el DOM
   - Búsquedas por texto: `//a[contains(text(), 'Login')]`

3. **LinkText / PartialLinkText** (Para enlaces)
   - Directo y legible
   - Ejemplo: `@FindBy(linkText = "My Account")`

### 5.2 Buenas Prácticas
- ✅ Usar atributos estables (id, name, data-*)
- ✅ Evitar selectores dependientes de estructura
- ✅ Preferir selectores únicos y específicos
- ❌ Evitar índices de array cuando sea posible
- ❌ No usar clases CSS genéricas

---

## 6. ESTRATEGIA DE ESPERAS

### 6.1 Tipos de Esperas Implementadas

#### Esperas Implícitas
```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```
- Configuradas en BaseTest
- Aplican globalmente a todas las búsquedas de elementos

#### Esperas Explícitas (WaitHelper)
```java
waitHelper.waitForElementToBeVisible(element);
waitHelper.waitForElementToBeClickable(element);
waitHelper.waitForPageLoad();
```
- Más precisas y confiables
- Esperan condiciones específicas

#### Esperas Personalizadas
```java
waitHelper.customWait(milliseconds);
```
- Para casos especiales (animaciones, alertas)

### 6.2 Mejores Prácticas
- Usar esperas explícitas para elementos críticos
- Evitar Thread.sleep() en producción (solo para debugging)
- Configurar timeouts apropiados (10-30 segundos)

---

## 7. MANEJO DE DATOS CON EXCEL

### 7.1 Estructura del Archivo testData.xlsx

#### Hoja: UsuariosRegistro
| First Name | Last Name | E-Mail | Telephone | Password |
|------------|-----------|--------|-----------|----------|
| Juan | Pérez | juan.perez@test.com | 3001234567 | Test123! |

#### Hoja: LoginData
| Email | Password | Expected Result |
|-------|----------|-----------------|
| juan.perez@test.com | Test123! | Success |
| invalid@test.com | wrong | Fail |

#### Hoja: ProductosBusqueda
| Categoria | SubCategoria | Producto | Cantidad |
|-----------|--------------|----------|----------|
| Desktops | PC | HP LP3065 | 1 |

### 7.2 Lectura de Datos
- **ExcelReader.java**: Lee hojas y devuelve List<Map<String, String>>
- Maneja diferentes tipos de celdas (String, Numeric, Boolean)
- Filtra filas vacías automáticamente

### 7.3 Generación de Datos
- **ExcelDataGenerator.java**: Crea el archivo con datos de ejemplo
- Ejecutar antes de las pruebas para generar testData.xlsx

---

## 8. SISTEMA DE LOGGING

### 8.1 Implementación
- **LogWriter.java**: Escribe logs en archivos de texto
- Formato: `TestLog_YYYYMMDD_HHMMSS.txt`
- Ubicación: `src/main/resources/logs/`

### 8.2 Información Registrada
- ✅ Resultado de registros (exitoso/fallido)
- ✅ Resultado de logins con mensajes de error
- ✅ Productos agregados al carrito (categoría, subcategoría, producto, cantidad)
- ✅ Verificación de productos en el carrito
- ✅ Timestamps de cada operación

### 8.3 Ejemplo de Log
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

---

## 9. ESTRATEGIA DE ASERCIONES

### 9.1 Hard Assertions (Assert)
- Detienen la ejecución al primer fallo
- Usadas para validaciones críticas
- Ejemplo: `Assert.assertTrue(loginExitoso)`

### 9.2 Soft Assertions (SoftAssert)
- Acumulan fallos y los reportan al final
- Usadas en loops para validar múltiples items
- Permiten completar todas las validaciones
```java
SoftAssert softAssert = new SoftAssert();
softAssert.assertTrue(condition, message);
softAssert.assertAll(); // Reporta todos los fallos
```

### 9.3 Estrategia de Uso
- **Hard Assert**: Login crítico, prerequisitos
- **Soft Assert**: Validación de múltiples productos, registros en batch

---

## 10. CASOS DE PRUEBA IMPLEMENTADOS

### 10.1 Test 1: Registro de Usuarios
**Clase:** RegistroUsuarioTest.java  
**Objetivo:** Validar el registro de usuarios con datos desde Excel

**Flujo:**
1. Leer datos de la hoja "UsuariosRegistro"
2. Para cada usuario:
   - Navegar a página de registro
   - Completar formulario
   - Aceptar política de privacidad
   - Hacer clic en Continue
   - Verificar mensaje de éxito
3. Registrar resultado en el log

**Validaciones:**
- ✅ Mensaje de éxito visible
- ✅ Registro de cada resultado en log

---

### 10.2 Test 2: Inicio de Sesión
**Clase:** LoginTest.java  
**Objetivo:** Validar login exitoso y manejo de errores

**Flujo:**
1. Leer datos de la hoja "LoginData"
2. Para cada credencial:
   - Navegar a página de login
   - Ingresar email y password
   - Hacer clic en Login
   - Validar según "Expected Result"
   - Logout si fue exitoso
3. Registrar resultado en el log

**Validaciones:**
- ✅ Login exitoso con credenciales válidas
- ✅ Mensaje de error con credenciales inválidas
- ✅ Comparación con resultado esperado

---

### 10.3 Test 3: Búsqueda y Agregado al Carrito
**Clase:** BusquedaYCarritoTest.java  
**Objetivo:** Buscar productos y agregarlos al carrito

**Flujo:**
1. Leer datos de la hoja "ProductosBusqueda"
2. Para cada producto:
   - Buscar por categoría/subcategoría o nombre
   - Verificar que aparece en resultados
   - Hacer clic en el producto
   - Establecer cantidad
   - Agregar al carrito
   - Verificar mensaje de éxito
3. Registrar cada producto agregado en el log

**Validaciones:**
- ✅ Producto encontrado en resultados
- ✅ Mensaje de éxito al agregar
- ✅ Log con categoría, subcategoría, producto y cantidad

---

### 10.4 Test 4: Verificación del Carrito
**Clase:** BusquedaYCarritoTest.java  
**Método:** testVerificarProductosEnCarrito()  
**Objetivo:** Verificar que los productos agregados están en el carrito

**Flujo:**
1. Navegar al carrito
2. Obtener lista de productos en el carrito
3. Para cada producto esperado:
   - Verificar que está presente
   - Obtener y verificar cantidad
4. Comparar cantidad esperada vs encontrada
5. Registrar resultado en el log

**Validaciones:**
- ✅ Todos los productos agregados están presentes
- ✅ Cantidades correctas
- ✅ Log de verificación final

---

## 11. EJECUCIÓN DE PRUEBAS

### 11.1 Prerequisitos
1. Generar el archivo Excel con datos:
```bash
mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"
```

2. Compilar el proyecto:
```bash
mvn clean compile
```

### 11.2 Ejecutar Pruebas

**Opción 1: Ejecutar todas las pruebas**
```bash
mvn test
```

**Opción 2: Ejecutar con TestNG XML**
```bash
mvn test -DsuiteXmlFile=testng.xml
```

**Opción 3: Ejecutar clase específica**
```bash
mvn test -Dtest=RegistroUsuarioTest
```

### 11.3 Reportes Generados
- **Surefire Reports**: `target/surefire-reports/`
  - index.html (Reporte HTML)
  - testng-results.xml (Resultados XML)
  - emailable-report.html (Reporte por email)

- **Logs Personalizados**: `src/main/resources/logs/`
  - TestLog_YYYYMMDD_HHMMSS.txt

---

## 12. MEJORES PRÁCTICAS APLICADAS

### 12.1 Código Limpio
- ✅ Nombres descriptivos de métodos y variables
- ✅ Métodos pequeños con responsabilidad única
- ✅ Comentarios JavaDoc en clases y métodos públicos
- ✅ Constantes en lugar de strings hardcodeadas

### 12.2 Mantenibilidad
- ✅ Separación de responsabilidades (POM)
- ✅ Clases de utilidad reutilizables
- ✅ Configuración centralizada
- ✅ No hay rutas absolutas

### 12.3 Confiabilidad
- ✅ Esperas apropiadas
- ✅ Manejo de excepciones
- ✅ Validaciones completas
- ✅ Logs detallados para debugging

### 12.4 Escalabilidad
- ✅ Fácil agregar nuevas páginas
- ✅ Fácil agregar nuevos tests
- ✅ Datos externos (Excel)
- ✅ Configuración flexible

---

## 13. DESAFÍOS Y SOLUCIONES

### 13.1 Sincronización
**Desafío:** Elementos dinámicos que cargan en diferentes tiempos  
**Solución:** Implementación de WaitHelper con múltiples tipos de esperas

### 13.2 Manejo de Datos
**Desafío:** Gestionar múltiples conjuntos de datos de prueba  
**Solución:** Apache POI con ExcelReader que devuelve estructuras Map

### 13.3 Logging Completo
**Desafío:** Registrar toda la información requerida  
**Solución:** LogWriter con métodos específicos para cada tipo de operación

### 13.4 Selectores Dinámicos
**Desafío:** Algunos elementos tienen IDs o clases dinámicas  
**Solución:** Uso de XPath con contains() y búsqueda por texto

---

## 14. MÉTRICAS Y COBERTURA

### 14.1 Cobertura de Funcionalidades
- ✅ Registro de usuarios: 100%
- ✅ Inicio de sesión: 100%
- ✅ Búsqueda de productos: 100%
- ✅ Agregado al carrito: 100%
- ✅ Verificación de carrito: 100%

### 14.2 Tipos de Validación
- ✅ Validaciones funcionales (aserciones)
- ✅ Validaciones de UI (elementos visibles)
- ✅ Validaciones de datos (comparación con Excel)
- ✅ Validaciones de flujo (secuencias completas)

---

## 15. MANTENIMIENTO Y EXTENSIÓN

### 15.1 Agregar Nuevas Pruebas
1. Crear nueva clase Page en `pages/` si es necesaria
2. Crear clase Test en `test/` extendiendo BaseTest
3. Agregar datos en Excel si se requieren
4. Actualizar testng.xml con la nueva clase

### 15.2 Modificar Localizadores
1. Localizar la clase Page correspondiente
2. Actualizar el @FindBy del elemento
3. No es necesario cambiar los tests

### 15.3 Agregar Nuevos Tipos de Log
1. Agregar método en LogWriter.java
2. Llamar desde los tests donde se necesite

---

## 16. CONCLUSIONES

### 16.1 Objetivos Logrados
✅ Implementación completa del patrón POM  
✅ Uso exhaustivo de Apache POI para manejo de Excel  
✅ Sistema robusto de logging  
✅ Aplicación de todos los tipos de esperas de Selenium  
✅ Uso de Hard y Soft Assertions  
✅ Código mantenible y escalable  

### 16.2 Beneficios del Framework
- Fácil de entender y mantener
- Reutilizable para otros proyectos
- Logs detallados para debugging
- Reportes completos de TestNG
- Datos separados del código

### 16.3 Recomendaciones Futuras
- Integrar con CI/CD (Jenkins, GitHub Actions)
- Agregar pruebas de rendimiento
- Implementar pruebas de regresión visual
- Agregar soporte para múltiples navegadores
- Implementar Pattern de Factory para Pages

---

**Fin del Documento**
