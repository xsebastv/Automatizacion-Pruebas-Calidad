#  Proyecto Final de Automatizaci�n - OpenCart

**Proyecto Transversal: Calidad de Software Aplicada a Sistemas de Dominio Espec�fico**

Automatizaci�n de pruebas para la tienda demo de OpenCart (https://opencart.abstracta.us/) utilizando Selenium WebDriver, TestNG y Page Object Model (POM).

##  Equipo de Desarrollo

- **Juan Sebasti�n R�os**
- **Fabi�n Saavedra**
- **Jhonatan Velasco**
- **Ian Marco Arango**

**Materia**: Calidad y Pruebas de Software  
**Objetivo**: Desarrollar un conjunto de pruebas automatizadas sobre OpenCart utilizando buenas pr�cticas de automatizaci�n, evaluando capacidad t�cnica en dise�o de pruebas, estructura de framework, selectores, sincronizaci�n, validaciones y manejo de datos externos.

---

##  �C�mo funciona este proyecto?

Este framework de automatizaci�n est� dise�ado para ejecutar pruebas end-to-end sobre la tienda demo de OpenCart (https://opencart.abstracta.us/) de forma **completamente autom�tica** y **data-driven** (guiado por datos de Excel).

###  Flujo de Ejecuci�n Autom�tico

1. **Inicio de Suite** (`BaseTest.java`)
   -  Configura Chrome con WebDriverManager (descarga driver autom�ticamente)
   -  Genera archivo Excel `testData.xlsx` con datos �nicos (emails con timestamp)
   -  Inicializa sistema de logs personalizados

2. **Prueba 1: Registro de Usuarios** (`RegistroUsuarioTest.java`)
   -  Lee hoja "UsuariosRegistro" del Excel
   -  Navega a p�gina de registro
   -  Completa formulario para cada usuario
   -  Valida mensajes de �xito/error
   -  Registra resultados en log

3. **Prueba 2: Login** (`LoginTest.java`)
   -  Lee hoja "LoginData" con credenciales y resultado esperado
   -  Intenta login para cada fila
   -  Compara resultado real vs esperado (Success/Fail)
   -  Valida mensajes de error en casos negativos

4. **Prueba 3: B�squeda y Carrito** (`BusquedaYCarritoTest.java`)
   -  Lee hoja "ProductosBusqueda" con productos a buscar
   -  Busca cada producto (por categor�a y/o nombre)
   -  Agrega al carrito con cantidad espec�fica
   -  **Completa autom�ticamente opciones requeridas** (color, fecha, etc.)
   -  Verifica que todos los productos est�n en el carrito

5. **Fin de Suite**
   -  Genera reporte TestNG con estad�sticas
   -  Cierra log con resumen de ejecuci�n
   -  Cierra navegador

###  Caracter�sticas Clave

- **Data-Driven**: Todos los datos vienen de Excel, f�cil de modificar sin tocar c�digo
- **Page Object Model**: Separaci�n clara entre l�gica de prueba y elementos de p�gina
- **Auto-Regeneraci�n**: Excel se crea autom�ticamente con emails �nicos cada ejecuci�n
- **Smart Options**: Productos con opciones (Canon: color, HP: fecha) se completan solos
- **Logs Detallados**: Archivo de texto con timestamp de cada operaci�n
- **Soft + Hard Asserts**: Valida m�ltiples items sin detener ejecuci�n completa

---

##  C�mo Ejecutar las Pruebas (IntelliJ IDEA)

### Opci�n 1: Suite Completa (Recomendado)

1. **Abrir el proyecto** en IntelliJ IDEA
2. Verificar que Project SDK sea **Java 17** (File  Project Structure  Project)
3. Localizar archivo `testng.xml` en la ra�z del proyecto
4. **Click derecho** en `testng.xml`  **Run ''testng.xml''**
5. Ver ejecuci�n en panel Run (navegador Chrome se abre autom�ticamente)

### Opci�n 2: Test Individual

Para ejecutar solo un test espec�fico:

1. Navegar a `src/test/java/com/demoblaze/test/`
2. Abrir el test deseado:
   - `RegistroUsuarioTest.java`  Solo registro
   - `LoginTest.java`  Solo login
   - `BusquedaYCarritoTest.java`  Solo b�squeda y carrito
3. **Click derecho** en la clase  **Run ''[NombreTest]''**

### Opci�n 3: Desde Terminal (Maven)

```powershell
# Suite completa
mvn test

# Test espec�fico
mvn test -Dtest=LoginTest

# Con reportes detallados
mvn clean test
```

###  Qu� Esperar Durante la Ejecuci�n

**En la consola ver�s:**
```
========================================
Eliminando testData.xlsx previo para regenerar datos coherentes...
========================================
Generando testData.xlsx para esta ejecuci�n...
 Archivo Excel generado exitosamente
   Tama�o: 5385 bytes
========================================
[main] INFO io.github.bonigarcia.wdm.WebDriverManager - Using chromedriver...
```

**En Chrome ver�s:**
- Navegaci�n autom�tica a OpenCart
- Formularios llen�ndose solos
- Productos siendo agregados al carrito
- Todo sin intervenci�n manual

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

###  D�nde Ver los Resultados

1. **Logs Personalizados**: `src/main/resources/logs/TestLog_[fecha].txt`
   - Detalle de cada registro, login y producto agregado
   
2. **Reporte TestNG**: `target/surefire-reports/index.html`
   - Abrir en navegador para ver estad�sticas visuales
   
3. **Consola de IntelliJ**: Panel Run
   - Resultado inmediato de cada test ( verde o  rojo)

---

##  Tecnolog�as Utilizadas

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
    testData.xlsx       # Generado autom�ticamente
    logs/               # Logs de ejecuci�n
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

El archivo `testData.xlsx` se crea autom�ticamente al iniciar la suite. Se fuerza regeneraci�n cada vez para garantizar:
- Correos �nicos para registro (timestamp en el email)
- Coincidencia entre usuarios registrados y filas de Login marcadas como `Success`

Hojas generadas:

####  UsuariosRegistro
| First Name | Last Name | E-Mail (�nico) | Telephone | Password |
|------------|-----------|----------------|-----------|----------|
| Juan | P�rez | juan.perez+YYYYMMDD_HHMMSS@test.com | 3001234567 | Test123! |
| Mar�a | Gonz�lez | maria.gonzalez+YYYYMMDD_HHMMSS@test.com | 3007654321 | Test456! |
| Carlos | Rodr�guez | carlos.rodriguez+TIMESTAMP@test.com | 3009876543 | Test789! |
| Ana | Mart�nez | ana.martinez+TIMESTAMP@test.com | 3005551234 | Test321! |
| Luis | Garc�a | luis.garcia+TIMESTAMP@test.com | 3008887777 | Test654! |

####  LoginData
| Email | Password | Expected Result |
|-------|----------|-----------------|
| juan.perez+TIMESTAMP@test.com | Test123! | Success |
| maria.gonzalez+TIMESTAMP@test.com | Test456! | Success |
| usuario.invalido@test.com | password_invalido | Fail |
| (vac�o) | (vac�o) | Fail |
| test@test.com | wrongpassword | Fail |

####  ProductosBusqueda
| Categoria | SubCategoria | Producto | Cantidad |
|-----------|--------------|----------|----------|
| Desktops | PC | HP LP3065 | 1 |
| Laptops & Notebooks | (vac�o) | MacBook | 2 |
| Components | Monitors | Apple Cinema 30 | 1 |
| (vac�o) | (vac�o) | iPhone | 1 |
| Cameras | (vac�o) | Canon EOS 5D | 1 |

Los productos con opciones (ej. Canon EOS 5D: color; HP LP3065: fecha) se rellenan autom�ticamente en el c�digo (`ProductDetailPage`) sin necesidad de columnas extra.

---

##  Resultados

**Logs Personalizados**: `src/main/resources/logs/TestLog_[fecha].txt`
- Detalle de cada registro, login y producto agregado con timestamps

**Reporte TestNG**: `target/surefire-reports/index.html`
- Estad�sticas visuales y resultados completos

---

##  Requisitos del Proyecto (Cumplimiento)

### Tecnolog�as Implementadas
-  Java 17 + Selenium WebDriver 4.35.0
-  Maven + Apache POI 5.2.5
-  Patr�n Page Object Model (6 clases)
-  TestNG 7.11.0

### Casos de Prueba
1.  Registro de Usuario (Excel  Formulario  Validaci�n)
2.  Inicio de Sesi�n (Credenciales v�lidas/inv�lidas)
3.  B�squeda y Carrito (Ciclo de productos)
4.  Verificaci�n de Carrito (Comparaci�n con Excel)

### Requisitos T�cnicos
-  POM completo en `pages/`
-  Selectores CSS y XPath estables
-  Esperas: Impl�citas, Expl�citas, Personalizadas
-  Apache POI para lectura de Excel
-  Hard Assert y Soft Assert
-  Logs con timestamp autom�tico

### Entregables
-  Repositorio Git completo
-  Archivos Excel (generaci�n autom�tica)
-  Logs y reportes TestNG
-  Documentaci�n (README + estrategia)

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
