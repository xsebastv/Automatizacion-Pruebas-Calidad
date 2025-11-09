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
 src/main/java/com/demoblaze/
    pages/              # Page Object Model (6 clases)
    utils/              # Utilidades (5 clases)
 src/main/resources/
    testData.xlsx       # Generado automáticamente
    logs/               # Logs de ejecución
 src/test/java/com/demoblaze/test/
    BaseTest.java
    RegistroUsuarioTest.java
    LoginTest.java
    BusquedaYCarritoTest.java
 pom.xml
 testng.xml
 README.md
```

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
