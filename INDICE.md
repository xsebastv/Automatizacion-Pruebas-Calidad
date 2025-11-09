# 📚 ÍNDICE DEL PROYECTO

## Guía rápida para encontrar todo lo que necesitas

---

## 🚀 PARA EMPEZAR RÁPIDO

| Necesito... | Ver archivo... |
|-------------|----------------|
| **Instrucciones paso a paso** | [INICIO_RAPIDO.md](INICIO_RAPIDO.md) |
| **Guía visual con imágenes** | [GUIA_VISUAL.md](GUIA_VISUAL.md) |
| **Generar Excel rápidamente** | Doble clic en `generar-excel.bat` |
| **Ejecutar pruebas rápidamente** | Doble clic en `ejecutar-pruebas.bat` |

---

## 📖 DOCUMENTACIÓN PRINCIPAL

### README.md
**Qué contiene:** Documentación completa del proyecto  
**Cuándo usar:** Para entender el proyecto en general  
**Secciones principales:**
- Tecnologías utilizadas
- Estructura del proyecto
- Instalación y configuración
- Ejecución de pruebas
- Interpretación de resultados
- Solución de problemas

**👉 [Ir a README.md](README.md)**

---

### ESTRATEGIA_AUTOMATIZACION.md
**Qué contiene:** Documento técnico detallado de 15+ páginas  
**Cuándo usar:** Para entender la estrategia técnica y preparar la presentación  
**Secciones principales:**
1. Información general
2. Tecnologías utilizadas
3. Estructura del proyecto
4. Patrón Page Object Model (POM)
5. Estrategia de selectores
6. Estrategia de esperas
7. Manejo de datos con Excel
8. Sistema de logging
9. Estrategia de aserciones
10. Casos de prueba implementados
11. Ejecución de pruebas
12. Mejores prácticas aplicadas
13. Desafíos y soluciones
14. Métricas y cobertura
15. Mantenimiento y extensión
16. Conclusiones

**👉 [Ir a ESTRATEGIA_AUTOMATIZACION.md](ESTRATEGIA_AUTOMATIZACION.md)**

---

### INICIO_RAPIDO.md
**Qué contiene:** Guía de 5 pasos para ejecutar el proyecto  
**Cuándo usar:** Primera vez ejecutando el proyecto  
**Pasos:**
1. Verificar prerequisitos (Java, Maven, Chrome)
2. Generar archivo Excel
3. Revisar/Editar datos de prueba
4. Ejecutar las pruebas
5. Ver resultados

**Incluye:**
- Comandos útiles
- Solución de problemas
- Consejos importantes
- Checklist pre-entrega

**👉 [Ir a INICIO_RAPIDO.md](INICIO_RAPIDO.md)**

---

### GUIA_VISUAL.md
**Qué contiene:** Guía paso a paso con ejemplos visuales  
**Cuándo usar:** Cuando necesitas instrucciones muy detalladas  
**Incluye:**
- Ejemplos de comandos con resultados esperados
- Explicación visual de qué verás en cada paso
- Formato del log explicado
- Solución de problemas detallada
- Guía para capturar evidencias
- Preparación de presentación

**👉 [Ir a GUIA_VISUAL.md](GUIA_VISUAL.md)**

---

### EVIDENCIAS.md
**Qué contiene:** Guía para capturar 40+ evidencias  
**Cuándo usar:** Al preparar la entrega final  
**Incluye:**
- Lista completa de evidencias necesarias
- Checklist de verificación
- Consejos para capturar
- Organización de archivos
- Nomenclatura sugerida

**Categorías de evidencias:**
- Configuración inicial (2 capturas)
- Archivos Excel (3 capturas)
- Estructura del proyecto (2 capturas)
- Ejecución de pruebas (6 capturas)
- Reportes generados (5 capturas)
- Logs personalizados (5 capturas)
- Código fuente (7 capturas)
- Técnicas implementadas (7 capturas)
- Documentación (3 capturas)

**👉 [Ir a EVIDENCIAS.md](EVIDENCIAS.md)**

---

### RESUMEN.md
**Qué contiene:** Resumen ejecutivo del proyecto completo  
**Cuándo usar:** Para una vista general rápida  
**Incluye:**
- Estructura completa del proyecto
- Características implementadas
- Tecnologías y dependencias
- Cumplimiento de requisitos
- Estadísticas del proyecto
- Checklist final

**👉 [Ir a RESUMEN.md](RESUMEN.md)**

---

## 💻 CÓDIGO FUENTE

### Páginas (POM)

#### BasePage.java
**Ubicación:** `src/main/java/com/demoblaze/pages/BasePage.java`  
**Propósito:** Clase base para todas las páginas  
**Métodos principales:**
- `navigateTo()` - Navegar a URL
- `clickElement()` - Click con espera
- `writeText()` - Escribir texto con espera
- `getText()` - Obtener texto con espera
- `isElementDisplayed()` - Verificar visibilidad

---

#### RegisterPage.java
**Ubicación:** `src/main/java/com/demoblaze/pages/RegisterPage.java`  
**Propósito:** Página de registro de usuarios  
**Métodos principales:**
- `navigateToRegisterPage()` - Ir a registro
- `fillRegistrationForm()` - Llenar formulario
- `acceptPrivacyPolicy()` - Aceptar política
- `clickContinue()` - Continuar registro
- `isSuccessMessageDisplayed()` - Verificar éxito
- `registerUser()` - Registro completo

---

#### LoginPage.java
**Ubicación:** `src/main/java/com/demoblaze/pages/LoginPage.java`  
**Propósito:** Página de inicio de sesión  
**Métodos principales:**
- `navigateToLoginPage()` - Ir a login
- `enterEmail()` - Ingresar email
- `enterPassword()` - Ingresar password
- `clickLogin()` - Hacer login
- `login()` - Login completo
- `isLoginSuccessful()` - Verificar login
- `logout()` - Cerrar sesión

---

#### ProductPage.java
**Ubicación:** `src/main/java/com/demoblaze/pages/ProductPage.java`  
**Propósito:** Página de búsqueda y productos  
**Métodos principales:**
- `navigateToHomePage()` - Ir a inicio
- `searchProduct()` - Buscar producto
- `searchByCategory()` - Buscar por categoría
- `isProductDisplayed()` - Verificar producto
- `clickOnProduct()` - Click en producto
- `addProductToCartFromList()` - Agregar al carrito
- `getProductCount()` - Contar productos

---

#### ProductDetailPage.java
**Ubicación:** `src/main/java/com/demoblaze/pages/ProductDetailPage.java`  
**Propósito:** Página de detalle de producto  
**Métodos principales:**
- `getProductName()` - Obtener nombre
- `setQuantity()` - Establecer cantidad
- `clickAddToCart()` - Agregar al carrito
- `addToCartWithQuantity()` - Agregar con cantidad
- `isSuccessMessageDisplayed()` - Verificar éxito
- `goToShoppingCart()` - Ir al carrito

---

#### CartPage.java
**Ubicación:** `src/main/java/com/demoblaze/pages/CartPage.java`  
**Propósito:** Página del carrito de compras  
**Métodos principales:**
- `navigateToCart()` - Ir al carrito
- `getCartItemCount()` - Contar items
- `isCartEmpty()` - Verificar si está vacío
- `isProductInCart()` - Verificar producto
- `getProductNamesInCart()` - Obtener nombres
- `getProductQuantity()` - Obtener cantidad
- `verifyProductsInCart()` - Verificar múltiples

---

### Utilidades

#### Constants.java
**Ubicación:** `src/main/java/com/demoblaze/utils/Constants.java`  
**Propósito:** Constantes del proyecto  
**Contiene:**
- `BASE_URL` - URL base de OpenCart

---

#### ExcelReader.java
**Ubicación:** `src/main/java/com/demoblaze/utils/ExcelReader.java`  
**Propósito:** Leer datos de archivos Excel  
**Métodos principales:**
- `readSheet()` - Leer hoja específica
- `readUsuariosRegistro()` - Leer usuarios
- `readLoginData()` - Leer datos de login
- `readProductosBusqueda()` - Leer productos
- `getCellValueAsString()` - Convertir celda a string

---

#### ExcelDataGenerator.java
**Ubicación:** `src/main/java/com/demoblaze/utils/ExcelDataGenerator.java`  
**Propósito:** Generar archivo Excel con datos de prueba  
**Métodos principales:**
- `main()` - Ejecuta la generación
- `createUsuariosRegistroSheet()` - Crea hoja de usuarios
- `createLoginDataSheet()` - Crea hoja de login
- `createProductosBusquedaSheet()` - Crea hoja de productos

---

#### LogWriter.java
**Ubicación:** `src/main/java/com/demoblaze/utils/LogWriter.java`  
**Propósito:** Escribir logs personalizados  
**Métodos principales:**
- `logRegistro()` - Registrar resultado de registro
- `logLogin()` - Registrar resultado de login
- `logProductoAgregado()` - Registrar producto agregado
- `logVerificacionCarrito()` - Registrar verificación
- `logMessage()` - Mensaje genérico
- `logSection()` - Separador de secciones
- `close()` - Cerrar el log

---

#### WaitHelper.java
**Ubicación:** `src/main/java/com/demoblaze/utils/WaitHelper.java`  
**Propósito:** Manejar esperas de Selenium  
**Métodos principales:**
- `waitForElementToBeVisible()` - Espera visible
- `waitForElementToBeClickable()` - Espera clickeable
- `setImplicitWait()` - Espera implícita
- `waitForPageLoad()` - Espera carga de página
- `customWait()` - Espera personalizada
- `waitForTitleContains()` - Espera título
- `waitForUrlContains()` - Espera URL

---

### Pruebas

#### BaseTest.java
**Ubicación:** `src/test/java/com/demoblaze/test/BaseTest.java`  
**Propósito:** Clase base para todas las pruebas  
**Funciones:**
- Configuración de WebDriver
- Inicialización de LogWriter
- Setup antes de suite
- Teardown después de suite

---

#### RegistroUsuarioTest.java
**Ubicación:** `src/test/java/com/demoblaze/test/RegistroUsuarioTest.java`  
**Propósito:** Pruebas de registro de usuarios  
**Tests:**
- `testRegistroUsuarios()` - Registra usuarios desde Excel
**Usa:**
- Soft Assert
- ExcelReader
- LogWriter

---

#### LoginTest.java
**Ubicación:** `src/test/java/com/demoblaze/test/LoginTest.java`  
**Propósito:** Pruebas de inicio de sesión  
**Tests:**
- `testLoginUsuarios()` - Prueba logins desde Excel
- `testLoginCredencialesInvalidas()` - Valida errores
**Usa:**
- Soft Assert (test 1)
- Hard Assert (test 2)
- ExcelReader
- LogWriter

---

#### BusquedaYCarritoTest.java
**Ubicación:** `src/test/java/com/demoblaze/test/BusquedaYCarritoTest.java`  
**Propósito:** Pruebas de búsqueda y carrito  
**Tests:**
- `testBuscarYAgregarProductos()` - Busca y agrega productos
- `testVerificarProductosEnCarrito()` - Verifica carrito
**Usa:**
- Soft Assert
- ExcelReader
- LogWriter
- Múltiples páginas POM

---

## 📊 ARCHIVOS DE DATOS

### testData.xlsx
**Ubicación:** `src/main/resources/testData.xlsx`  
**Generado por:** ExcelDataGenerator.java  
**Hojas:**

#### 1. UsuariosRegistro
```
Columnas:
- First Name
- Last Name
- E-Mail
- Telephone
- Password

Usado por: RegistroUsuarioTest.java
```

#### 2. LoginData
```
Columnas:
- Email
- Password
- Expected Result

Usado por: LoginTest.java
```

#### 3. ProductosBusqueda
```
Columnas:
- Categoria
- SubCategoria
- Producto
- Cantidad

Usado por: BusquedaYCarritoTest.java
```

---

## 📝 LOGS

### TestLog_YYYYMMDD_HHMMSS.txt
**Ubicación:** `src/main/resources/logs/`  
**Generado por:** LogWriter.java  
**Secciones:**
- Encabezado con fecha
- Registros de usuarios
- Logins
- Productos agregados
- Verificación de carrito
- Footer

---

## 📄 REPORTES

### Reportes TestNG
**Ubicación:** `target/surefire-reports/`  
**Archivos principales:**

#### index.html
- Reporte principal HTML
- Vista general de todos los tests
- Detalles por clase y método

#### emailable-report.html
- Reporte compacto
- Fácil de enviar por email

#### testng-results.xml
- Resultados en formato XML
- Para integración con otras herramientas

---

## ⚙️ CONFIGURACIÓN

### pom.xml
**Ubicación:** Raíz del proyecto  
**Contiene:**
- Información del proyecto
- Dependencias (Selenium, TestNG, Apache POI)
- Plugins (Maven Surefire)
- Configuración de compilación

---

### testng.xml
**Ubicación:** Raíz del proyecto  
**Contiene:**
- Suite de pruebas
- Orden de ejecución
- Clases de test a ejecutar

---

## 🔧 SCRIPTS

### generar-excel.bat
**Ubicación:** Raíz del proyecto  
**Función:** Genera el archivo testData.xlsx  
**Uso:** Doble clic en el archivo  
**Ejecuta:** ExcelDataGenerator.java

---

### ejecutar-pruebas.bat
**Ubicación:** Raíz del proyecto  
**Función:** Compila y ejecuta todas las pruebas  
**Uso:** Doble clic en el archivo  
**Ejecuta:** `mvn clean test`

---

## 🎯 FLUJO DE TRABAJO

```
1. PREPARACIÓN
   ├─ Verificar Java, Maven, Chrome → GUIA_VISUAL.md
   ├─ Generar Excel → generar-excel.bat
   └─ Revisar datos → testData.xlsx

2. EJECUCIÓN
   ├─ Ejecutar pruebas → ejecutar-pruebas.bat
   └─ Ver ejecución → Chrome automático

3. RESULTADOS
   ├─ Ver reportes HTML → target/surefire-reports/index.html
   └─ Ver logs → src/main/resources/logs/TestLog_*.txt

4. ENTREGA
   ├─ Capturar evidencias → EVIDENCIAS.md
   ├─ Preparar presentación → ESTRATEGIA_AUTOMATIZACION.md
   └─ Verificar checklist → RESUMEN.md
```

---

## 🆘 AYUDA RÁPIDA

| Si tienes problema con... | Ver sección en... |
|----------------------------|-------------------|
| Maven no reconocido | GUIA_VISUAL.md → Problema 1 |
| Excel no se encuentra | GUIA_VISUAL.md → Problema 2 |
| Email ya registrado | GUIA_VISUAL.md → Problema 3 |
| Login falla | GUIA_VISUAL.md → Problema 4 |
| Producto no encontrado | GUIA_VISUAL.md → Problema 5 |
| Timeout | GUIA_VISUAL.md → Problema 6 |

---

## 📧 CONTACTO Y SOPORTE

Para dudas sobre el proyecto:
1. Consulta la documentación relevante según la tabla anterior
2. Revisa la sección de solución de problemas
3. Verifica que seguiste todos los pasos

---

## ✅ CHECKLIST RÁPIDO

```
Antes de ejecutar:
□ Java instalado
□ Maven instalado
□ Chrome actualizado
□ Excel generado

Durante la ejecución:
□ Chrome se abre
□ Pruebas se ejecutan
□ Consola muestra progreso

Después de ejecutar:
□ Reportes HTML generados
□ Logs creados
□ Todo pasó o sabes por qué falló

Para la entrega:
□ Evidencias capturadas
□ Documentación lista
□ Presentación preparada
```

---

## 🎓 PARA LA PRESENTACIÓN

Documentos clave a revisar:
1. **ESTRATEGIA_AUTOMATIZACION.md** - Contenido técnico detallado
2. **RESUMEN.md** - Resumen ejecutivo
3. **README.md** - Información general

Preparar:
- [ ] Demostración en vivo (GUIA_VISUAL.md)
- [ ] Explicación del código (Índice de Código Fuente arriba)
- [ ] Mostrar reportes y logs
- [ ] Respuestas a preguntas frecuentes

---

**¡Todo está organizado y listo para usar! 🚀**
