# 📸 GUÍA PARA CAPTURAR EVIDENCIAS

Esta guía te ayudará a capturar las evidencias necesarias para la entrega final del proyecto.

---

## 📋 Evidencias Requeridas

### 1. CONFIGURACIÓN INICIAL

#### 1.1 Versiones Instaladas
**Captura:** Ventana de PowerShell mostrando:
```powershell
java -version
mvn -version
```
**Archivo:** `evidencias/01_versiones.png`

---

### 2. ARCHIVO EXCEL GENERADO

#### 2.1 Generación del Excel
**Captura:** Ventana de terminal/PowerShell ejecutando:
```powershell
mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"
```
**Mostrar:** Mensaje de éxito
**Archivo:** `evidencias/02_generacion_excel.png`

#### 2.2 Archivo Excel - Hoja UsuariosRegistro
**Captura:** Excel abierto mostrando la hoja "UsuariosRegistro"
**Mostrar:** Todos los campos (First Name, Last Name, E-Mail, Telephone, Password)
**Archivo:** `evidencias/03_excel_usuarios.png`

#### 2.3 Archivo Excel - Hoja LoginData
**Captura:** Excel abierto mostrando la hoja "LoginData"
**Mostrar:** Email, Password, Expected Result
**Archivo:** `evidencias/04_excel_login.png`

#### 2.4 Archivo Excel - Hoja ProductosBusqueda
**Captura:** Excel abierto mostrando la hoja "ProductosBusqueda"
**Mostrar:** Categoria, SubCategoria, Producto, Cantidad
**Archivo:** `evidencias/05_excel_productos.png`

---

### 3. ESTRUCTURA DEL PROYECTO

#### 3.1 Estructura de Carpetas
**Captura:** Explorador de Windows o VS Code mostrando la estructura completa
**Mostrar:** 
- src/main/java/com/demoblaze/pages/
- src/main/java/com/demoblaze/utils/
- src/test/java/com/demoblaze/test/
**Archivo:** `evidencias/06_estructura_proyecto.png`

#### 3.2 Dependencias en pom.xml
**Captura:** Archivo pom.xml abierto mostrando las dependencias
**Mostrar:** Selenium, TestNG, Apache POI
**Archivo:** `evidencias/07_pom_dependencias.png`

---

### 4. EJECUCIÓN DE PRUEBAS

#### 4.1 Inicio de Ejecución
**Captura:** Terminal ejecutando `mvn clean test`
**Mostrar:** Inicio del proceso de compilación y ejecución
**Archivo:** `evidencias/08_inicio_pruebas.png`

#### 4.2 Chrome Ejecutando Pruebas
**Captura:** Navegador Chrome con OpenCart durante la ejecución
**Sugerencias de capturas:**
- Formulario de registro siendo llenado
- Página de login
- Búsqueda de producto
- Producto agregándose al carrito
- Vista del carrito con productos
**Archivos:** 
- `evidencias/09_test_registro.png`
- `evidencias/10_test_login.png`
- `evidencias/11_test_busqueda.png`
- `evidencias/12_test_carrito.png`

#### 4.3 Consola con Resultados
**Captura:** Terminal mostrando el resumen final de las pruebas
**Mostrar:** Tests run, Failures, Errors, Skipped
**Archivo:** `evidencias/13_resumen_consola.png`

---

### 5. REPORTES GENERADOS

#### 5.1 Reporte HTML de TestNG - Vista General
**Captura:** Navegador mostrando `target/surefire-reports/index.html`
**Mostrar:** Resumen de tests ejecutados, pasados, fallidos
**Archivo:** `evidencias/14_reporte_testng_general.png`

#### 5.2 Reporte HTML - Detalles de RegistroUsuarioTest
**Captura:** Detalles del test de registro en el reporte HTML
**Archivo:** `evidencias/15_reporte_registro.png`

#### 5.3 Reporte HTML - Detalles de LoginTest
**Captura:** Detalles del test de login en el reporte HTML
**Archivo:** `evidencias/16_reporte_login.png`

#### 5.4 Reporte HTML - Detalles de BusquedaYCarritoTest
**Captura:** Detalles del test de búsqueda y carrito en el reporte HTML
**Archivo:** `evidencias/17_reporte_busqueda_carrito.png`

#### 5.5 Emailable Report
**Captura:** Vista del `emailable-report.html`
**Archivo:** `evidencias/18_emailable_report.png`

---

### 6. LOGS PERSONALIZADOS

#### 6.1 Archivo de Log Generado
**Captura:** Explorador de Windows mostrando `src/main/resources/logs/`
**Mostrar:** Archivo TestLog_*.txt generado con timestamp
**Archivo:** `evidencias/19_archivo_log.png`

#### 6.2 Contenido del Log - Registros
**Captura:** Log abierto mostrando la sección de registros
**Mostrar:** 
```
[REGISTRO] DD/MM/YYYY HH:MM:SS
  Email: juan.perez@test.com
  Estado: EXITOSO
  Mensaje: Your Account Has Been Created!
```
**Archivo:** `evidencias/20_log_registros.png`

#### 6.3 Contenido del Log - Logins
**Captura:** Log mostrando la sección de logins
**Mostrar:** Logins exitosos y fallidos
**Archivo:** `evidencias/21_log_logins.png`

#### 6.4 Contenido del Log - Productos Agregados
**Captura:** Log mostrando productos agregados al carrito
**Mostrar:** 
```
[PRODUCTO AGREGADO] DD/MM/YYYY HH:MM:SS
  Categoría: Desktops
  Subcategoría: PC
  Producto: HP LP3065
  Cantidad: 1
  Estado: EXITOSO
```
**Archivo:** `evidencias/22_log_productos.png`

#### 6.5 Contenido del Log - Verificación de Carrito
**Captura:** Log mostrando la verificación final del carrito
**Mostrar:** Productos esperados vs encontrados
**Archivo:** `evidencias/23_log_verificacion.png`

---

### 7. CÓDIGO FUENTE

#### 7.1 BasePage.java
**Captura:** Código de la clase BasePage
**Mostrar:** Métodos principales y patrón POM
**Archivo:** `evidencias/24_codigo_basepage.png`

#### 7.2 RegisterPage.java
**Captura:** Código mostrando localizadores con @FindBy
**Archivo:** `evidencias/25_codigo_registerpage.png`

#### 7.3 ExcelReader.java
**Captura:** Código mostrando uso de Apache POI
**Archivo:** `evidencias/26_codigo_excelreader.png`

#### 7.4 LogWriter.java
**Captura:** Código mostrando métodos de logging
**Archivo:** `evidencias/27_codigo_logwriter.png`

#### 7.5 WaitHelper.java
**Captura:** Código mostrando diferentes tipos de esperas
**Archivo:** `evidencias/28_codigo_waithelper.png`

#### 7.6 RegistroUsuarioTest.java
**Captura:** Código del test mostrando uso de SoftAssert
**Archivo:** `evidencias/29_codigo_test_registro.png`

#### 7.7 BusquedaYCarritoTest.java
**Captura:** Código mostrando el ciclo de productos
**Archivo:** `evidencias/30_codigo_test_busqueda.png`

---

### 8. VALIDACIONES Y ASERCIONES

#### 8.1 Hard Assert
**Captura:** Código mostrando uso de Assert (Hard)
**Ejemplo en LoginTest:**
```java
Assert.assertTrue(loginExitoso, "Login exitoso: " + email);
```
**Archivo:** `evidencias/31_hard_assert.png`

#### 8.2 Soft Assert
**Captura:** Código mostrando uso de SoftAssert
**Ejemplo:**
```java
SoftAssert softAssert = new SoftAssert();
softAssert.assertTrue(condition, message);
softAssert.assertAll();
```
**Archivo:** `evidencias/32_soft_assert.png`

---

### 9. ESPERAS DE SELENIUM

#### 9.1 Esperas Implícitas
**Captura:** Código en BaseTest.java mostrando configuración
**Archivo:** `evidencias/33_espera_implicita.png`

#### 9.2 Esperas Explícitas
**Captura:** Código en WaitHelper.java mostrando WebDriverWait
**Archivo:** `evidencias/34_espera_explicita.png`

#### 9.3 Esperas Personalizadas
**Captura:** Código mostrando customWait
**Archivo:** `evidencias/35_espera_personalizada.png`

---

### 10. SELECTORES

#### 10.1 CSS Selectors
**Captura:** Código mostrando @FindBy con CSS
**Ejemplo:**
```java
@FindBy(css = "input[name='email']")
private WebElement emailInput;
```
**Archivo:** `evidencias/36_css_selectors.png`

#### 10.2 XPath Selectors
**Captura:** Código mostrando uso de XPath
**Archivo:** `evidencias/37_xpath_selectors.png`

---

### 11. DOCUMENTACIÓN

#### 11.1 README.md
**Captura:** Archivo README.md mostrando estructura
**Archivo:** `evidencias/38_readme.png`

#### 11.2 ESTRATEGIA_AUTOMATIZACION.md
**Captura:** Documento de estrategia mostrando secciones principales
**Archivo:** `evidencias/39_estrategia.png`

#### 11.3 testng.xml
**Captura:** Archivo de configuración de TestNG
**Archivo:** `evidencias/40_testng_xml.png`

---

## 📝 CHECKLIST DE EVIDENCIAS

Usar esta lista para verificar que tienes todas las capturas:

### Configuración
- [ ] 01 - Versiones de Java y Maven
- [ ] 02 - Generación del Excel

### Excel
- [ ] 03 - Hoja UsuariosRegistro
- [ ] 04 - Hoja LoginData
- [ ] 05 - Hoja ProductosBusqueda

### Estructura
- [ ] 06 - Estructura del proyecto
- [ ] 07 - Dependencias en pom.xml

### Ejecución
- [ ] 08 - Inicio de pruebas
- [ ] 09 - Test de registro ejecutándose
- [ ] 10 - Test de login ejecutándose
- [ ] 11 - Test de búsqueda ejecutándose
- [ ] 12 - Test de carrito ejecutándose
- [ ] 13 - Resumen en consola

### Reportes
- [ ] 14 - Reporte TestNG general
- [ ] 15 - Reporte de registro
- [ ] 16 - Reporte de login
- [ ] 17 - Reporte de búsqueda y carrito
- [ ] 18 - Emailable report

### Logs
- [ ] 19 - Archivo de log generado
- [ ] 20 - Log de registros
- [ ] 21 - Log de logins
- [ ] 22 - Log de productos
- [ ] 23 - Log de verificación

### Código
- [ ] 24 - BasePage.java
- [ ] 25 - RegisterPage.java
- [ ] 26 - ExcelReader.java
- [ ] 27 - LogWriter.java
- [ ] 28 - WaitHelper.java
- [ ] 29 - RegistroUsuarioTest.java
- [ ] 30 - BusquedaYCarritoTest.java

### Técnicas
- [ ] 31 - Hard Assert
- [ ] 32 - Soft Assert
- [ ] 33 - Espera Implícita
- [ ] 34 - Espera Explícita
- [ ] 35 - Espera Personalizada
- [ ] 36 - CSS Selectors
- [ ] 37 - XPath Selectors

### Documentación
- [ ] 38 - README.md
- [ ] 39 - ESTRATEGIA_AUTOMATIZACION.md
- [ ] 40 - testng.xml

---

## 💡 CONSEJOS PARA CAPTURAR

1. **Calidad:** Usa alta resolución (1920x1080 mínimo)
2. **Claridad:** Asegúrate de que el texto sea legible
3. **Contexto:** Incluye barras de título y navegación cuando sea relevante
4. **Completo:** No cortes información importante
5. **Organización:** Nombra los archivos según la convención sugerida
6. **Formato:** PNG preferiblemente (mejor calidad que JPG para texto)

---

## 📦 ORGANIZACIÓN DE ENTREGA

```
ENTREGA_FINAL/
├── evidencias/
│   ├── 01_versiones.png
│   ├── 02_generacion_excel.png
│   ├── ...
│   └── 40_testng_xml.png
├── codigo/
│   └── STORE_2511/
│       └── [proyecto completo]
├── excel/
│   └── testData.xlsx
├── reportes/
│   ├── index.html
│   ├── emailable-report.html
│   └── TestLog_*.txt
└── documentacion/
    ├── README.md
    ├── ESTRATEGIA_AUTOMATIZACION.md
    ├── INICIO_RAPIDO.md
    └── PRESENTACION.pdf (si aplica)
```

---

**¡Buena suerte con tu proyecto! 🎓**
