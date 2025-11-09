# ✅ CHECKLIST COMPLETO DEL PROYECTO

Usa este archivo para verificar que tienes todo listo para la entrega.

---

## 📋 FASE 1: CONFIGURACIÓN INICIAL

### Instalaciones
- [ ] ✅ Java 17 o superior instalado
  ```powershell
  java -version
  # Debe mostrar: openjdk version "17.x.x" o superior
  ```

- [ ] ✅ Maven instalado y en PATH
  ```powershell
  mvn -version
  # Debe mostrar: Apache Maven 3.x.x
  ```

- [ ] ✅ Google Chrome instalado (última versión)

- [ ] ✅ Editor de código (VS Code, IntelliJ, Eclipse)

---

## 📋 FASE 2: PROYECTO

### Archivos del Código Fuente
- [ ] ✅ `src/main/java/com/demoblaze/pages/BasePage.java`
- [ ] ✅ `src/main/java/com/demoblaze/pages/RegisterPage.java`
- [ ] ✅ `src/main/java/com/demoblaze/pages/LoginPage.java`
- [ ] ✅ `src/main/java/com/demoblaze/pages/ProductPage.java`
- [ ] ✅ `src/main/java/com/demoblaze/pages/ProductDetailPage.java`
- [ ] ✅ `src/main/java/com/demoblaze/pages/CartPage.java`

### Archivos de Utilidades
- [ ] ✅ `src/main/java/com/demoblaze/utils/Constants.java`
- [ ] ✅ `src/main/java/com/demoblaze/utils/ExcelReader.java`
- [ ] ✅ `src/main/java/com/demoblaze/utils/LogWriter.java`
- [ ] ✅ `src/main/java/com/demoblaze/utils/WaitHelper.java`
- [ ] ✅ `src/main/java/com/demoblaze/utils/ExcelDataGenerator.java`

### Archivos de Pruebas
- [ ] ✅ `src/test/java/com/demoblaze/test/BaseTest.java`
- [ ] ✅ `src/test/java/com/demoblaze/test/RegistroUsuarioTest.java`
- [ ] ✅ `src/test/java/com/demoblaze/test/LoginTest.java`
- [ ] ✅ `src/test/java/com/demoblaze/test/BusquedaYCarritoTest.java`

### Archivos de Configuración
- [ ] ✅ `pom.xml` (con todas las dependencias)
- [ ] ✅ `testng.xml` (configuración de suite)

### Scripts de Automatización
- [ ] ✅ `generar-excel.bat`
- [ ] ✅ `ejecutar-pruebas.bat`

### Documentación
- [ ] ✅ `README.md`
- [ ] ✅ `ESTRATEGIA_AUTOMATIZACION.md`
- [ ] ✅ `INICIO_RAPIDO.md`
- [ ] ✅ `GUIA_VISUAL.md`
- [ ] ✅ `EVIDENCIAS.md`
- [ ] ✅ `RESUMEN.md`
- [ ] ✅ `INDICE.md`
- [ ] ✅ `CHECKLIST.md` (este archivo)

---

## 📋 FASE 3: GENERACIÓN DE DATOS

### Excel de Datos
- [ ] ✅ Archivo `src/main/resources/testData.xlsx` generado
  ```
  Método: Doble clic en generar-excel.bat
  O ejecutar: mvn exec:java -Dexec.mainClass="com.demoblaze.utils.ExcelDataGenerator"
  ```

- [ ] ✅ Hoja "UsuariosRegistro" con datos
  ```
  Columnas esperadas:
  - First Name
  - Last Name
  - E-Mail
  - Telephone
  - Password
  
  Mínimo 3-5 usuarios
  ```

- [ ] ✅ Hoja "LoginData" con datos
  ```
  Columnas esperadas:
  - Email
  - Password
  - Expected Result
  
  Incluir casos exitosos y fallidos
  ```

- [ ] ✅ Hoja "ProductosBusqueda" con datos
  ```
  Columnas esperadas:
  - Categoria
  - SubCategoria
  - Producto
  - Cantidad
  
  Mínimo 3-5 productos
  ```

### Validación de Datos
- [ ] ✅ Emails en UsuariosRegistro son únicos
- [ ] ✅ Emails en LoginData con "Success" están en UsuariosRegistro
- [ ] ✅ Nombres de productos coinciden con OpenCart
- [ ] ✅ No hay filas vacías en el Excel

---

## 📋 FASE 4: COMPILACIÓN

### Compilar el Proyecto
- [ ] ✅ Proyecto compila sin errores
  ```powershell
  mvn clean compile
  # Debe terminar en: BUILD SUCCESS
  ```

- [ ] ✅ No hay errores de dependencias
  ```powershell
  mvn dependency:tree
  # Debe mostrar árbol de dependencias completo
  ```

---

## 📋 FASE 5: EJECUCIÓN DE PRUEBAS

### Primera Ejecución
- [ ] ✅ Pruebas ejecutadas exitosamente
  ```powershell
  mvn clean test
  # O doble clic en: ejecutar-pruebas.bat
  ```

- [ ] ✅ Chrome se abrió automáticamente durante la ejecución

- [ ] ✅ Se ejecutaron los 3 tests:
  - [ ] RegistroUsuarioTest
  - [ ] LoginTest
  - [ ] BusquedaYCarritoTest

- [ ] ✅ Consola mostró "BUILD SUCCESS"

### Verificación de Funcionalidad
- [ ] ✅ Test de Registro:
  - [ ] Navegó a página de registro
  - [ ] Llenó formularios con datos del Excel
  - [ ] Mostró mensaje de éxito

- [ ] ✅ Test de Login:
  - [ ] Navegó a página de login
  - [ ] Probó credenciales del Excel
  - [ ] Validó éxitos y fallos correctamente

- [ ] ✅ Test de Búsqueda y Carrito:
  - [ ] Buscó productos del Excel
  - [ ] Agregó productos al carrito
  - [ ] Verificó productos en el carrito

---

## 📋 FASE 6: RESULTADOS Y REPORTES

### Reportes HTML de TestNG
- [ ] ✅ Carpeta `target/surefire-reports/` creada

- [ ] ✅ Archivo `index.html` existe
  ```
  Se puede abrir en navegador
  Muestra resumen de pruebas
  ```

- [ ] ✅ Archivo `emailable-report.html` existe
  ```
  Reporte compacto generado
  ```

- [ ] ✅ Archivo `testng-results.xml` existe
  ```
  Resultados en formato XML
  ```

### Contenido de Reportes
- [ ] ✅ Reporte muestra cantidad de tests ejecutados
- [ ] ✅ Reporte muestra tests pasados
- [ ] ✅ Reporte muestra tests fallidos (si hay)
- [ ] ✅ Reporte muestra tiempo de ejecución
- [ ] ✅ Reporte tiene detalles de cada test

### Logs Personalizados
- [ ] ✅ Carpeta `src/main/resources/logs/` creada

- [ ] ✅ Archivo `TestLog_YYYYMMDD_HHMMSS.txt` generado

- [ ] ✅ Log contiene encabezado con fecha

- [ ] ✅ Log contiene sección de REGISTROS:
  - [ ] Email del usuario
  - [ ] Estado (EXITOSO/FALLIDO)
  - [ ] Mensaje de resultado

- [ ] ✅ Log contiene sección de LOGINS:
  - [ ] Email usado
  - [ ] Estado (EXITOSO/FALLIDO)
  - [ ] Mensaje de resultado

- [ ] ✅ Log contiene sección de PRODUCTOS AGREGADOS:
  - [ ] Categoría
  - [ ] Subcategoría (si aplica)
  - [ ] Nombre del producto
  - [ ] Cantidad
  - [ ] Estado (EXITOSO/FALLIDO)

- [ ] ✅ Log contiene VERIFICACIÓN DE CARRITO:
  - [ ] Productos esperados
  - [ ] Productos encontrados
  - [ ] Estado (EXITOSO/FALLIDO)

---

## 📋 FASE 7: VALIDACIÓN DE REQUISITOS TÉCNICOS

### Patrón Page Object Model (POM)
- [ ] ✅ Todas las páginas heredan de BasePage
- [ ] ✅ Uso de @FindBy para localizadores
- [ ] ✅ Métodos de acción en las páginas
- [ ] ✅ Métodos de validación en las páginas
- [ ] ✅ Tests no tienen localizadores directos

### Selectores
- [ ] ✅ Uso de CSS Selectors
  ```
  Ejemplo: input[name='email']
  ```

- [ ] ✅ Uso de XPath
  ```
  Ejemplo: //a[contains(text(), 'Login')]
  ```

- [ ] ✅ Selectores son claros y estables
- [ ] ✅ No hay rutas absolutas

### Esperas de Selenium
- [ ] ✅ Esperas Implícitas implementadas
  ```
  Ver: BaseTest.java línea ~30
  ```

- [ ] ✅ Esperas Explícitas implementadas
  ```
  Ver: WaitHelper.java
  - waitForElementToBeVisible
  - waitForElementToBeClickable
  - waitForPageLoad
  ```

- [ ] ✅ Esperas Personalizadas implementadas
  ```
  Ver: WaitHelper.java - customWait()
  ```

### Apache POI
- [ ] ✅ Dependencia Apache POI en pom.xml
- [ ] ✅ ExcelReader lee datos correctamente
- [ ] ✅ Lee hoja UsuariosRegistro
- [ ] ✅ Lee hoja LoginData
- [ ] ✅ Lee hoja ProductosBusqueda
- [ ] ✅ Maneja diferentes tipos de celdas

### Aserciones
- [ ] ✅ Hard Assert usado (TestNG Assert)
  ```
  Ver: LoginTest.java - testLoginCredencialesInvalidas()
  Assert.assertTrue(...)
  Assert.assertFalse(...)
  ```

- [ ] ✅ Soft Assert usado (TestNG SoftAssert)
  ```
  Ver: RegistroUsuarioTest.java - testRegistroUsuarios()
  SoftAssert softAssert = new SoftAssert();
  softAssert.assertTrue(...)
  softAssert.assertAll();
  ```

---

## 📋 FASE 8: EVIDENCIAS

### Capturas de Configuración
- [ ] 📸 Versiones de Java y Maven
- [ ] 📸 Generación del Excel ejecutándose

### Capturas de Excel
- [ ] 📸 Hoja UsuariosRegistro completa
- [ ] 📸 Hoja LoginData completa
- [ ] 📸 Hoja ProductosBusqueda completa

### Capturas de Estructura
- [ ] 📸 Estructura de carpetas del proyecto
- [ ] 📸 Dependencias en pom.xml

### Capturas de Ejecución
- [ ] 📸 Consola ejecutando mvn test
- [ ] 📸 Chrome - Formulario de registro llenándose
- [ ] 📸 Chrome - Página de login
- [ ] 📸 Chrome - Búsqueda de producto
- [ ] 📸 Chrome - Producto agregándose al carrito
- [ ] 📸 Chrome - Carrito con productos
- [ ] 📸 Consola con "BUILD SUCCESS"

### Capturas de Reportes
- [ ] 📸 Reporte TestNG - Vista general (index.html)
- [ ] 📸 Reporte TestNG - Detalle RegistroUsuarioTest
- [ ] 📸 Reporte TestNG - Detalle LoginTest
- [ ] 📸 Reporte TestNG - Detalle BusquedaYCarritoTest
- [ ] 📸 Emailable report

### Capturas de Logs
- [ ] 📸 Archivo de log generado en carpeta
- [ ] 📸 Log - Sección de registros
- [ ] 📸 Log - Sección de logins
- [ ] 📸 Log - Sección de productos agregados
- [ ] 📸 Log - Sección de verificación de carrito

### Capturas de Código
- [ ] 📸 BasePage.java - Métodos principales
- [ ] 📸 RegisterPage.java - Uso de @FindBy
- [ ] 📸 ExcelReader.java - Uso de Apache POI
- [ ] 📸 LogWriter.java - Métodos de logging
- [ ] 📸 WaitHelper.java - Tipos de esperas
- [ ] 📸 RegistroUsuarioTest.java - Uso de SoftAssert
- [ ] 📸 BusquedaYCarritoTest.java - Ciclo de productos

### Capturas de Técnicas
- [ ] 📸 Código con Hard Assert
- [ ] 📸 Código con Soft Assert
- [ ] 📸 Código con Espera Implícita
- [ ] 📸 Código con Espera Explícita
- [ ] 📸 Código con Espera Personalizada
- [ ] 📸 Código con CSS Selectors
- [ ] 📸 Código con XPath Selectors

### Capturas de Documentación
- [ ] 📸 README.md
- [ ] 📸 ESTRATEGIA_AUTOMATIZACION.md
- [ ] 📸 testng.xml

**Nota:** Ver [EVIDENCIAS.md](EVIDENCIAS.md) para detalles de cada captura

---

## 📋 FASE 9: ORGANIZACIÓN DE ENTREGA

### Estructura de Carpetas
- [ ] ✅ Crear carpeta `ENTREGA_FINAL/`

- [ ] ✅ Subcarpeta `codigo/`
  ```
  Copiar todo STORE_2511/
  ```

- [ ] ✅ Subcarpeta `excel/`
  ```
  Copiar: testData.xlsx
  ```

- [ ] ✅ Subcarpeta `reportes/`
  ```
  Copiar:
  - target/surefire-reports/index.html
  - target/surefire-reports/emailable-report.html
  - src/main/resources/logs/TestLog_*.txt
  ```

- [ ] ✅ Subcarpeta `evidencias/`
  ```
  Todas las capturas de pantalla
  Nombradas según EVIDENCIAS.md
  ```

- [ ] ✅ Subcarpeta `documentacion/`
  ```
  Copiar:
  - README.md
  - ESTRATEGIA_AUTOMATIZACION.md
  - INICIO_RAPIDO.md
  - Presentación (si aplica)
  ```

---

## 📋 FASE 10: PREPARACIÓN DE PRESENTACIÓN

### Revisión de Documentación
- [ ] ✅ Leer completamente ESTRATEGIA_AUTOMATIZACION.md
- [ ] ✅ Entender todos los conceptos técnicos
- [ ] ✅ Revisar RESUMEN.md para resumen ejecutivo

### Demostración Práctica
- [ ] ✅ Practicar demostración en vivo:
  - [ ] Mostrar estructura del proyecto (2 min)
  - [ ] Mostrar Excel con datos (2 min)
  - [ ] Ejecutar pruebas en vivo (10 min)
  - [ ] Mostrar reportes y logs (5 min)
  - [ ] Explicar código (5 min)

### Preparar Respuestas
- [ ] ✅ ¿Qué es el patrón POM?
  ```
  Respuesta: Ver ESTRATEGIA_AUTOMATIZACION.md sección 4
  ```

- [ ] ✅ ¿Cómo se manejan las esperas?
  ```
  Respuesta: Ver ESTRATEGIA_AUTOMATIZACION.md sección 6
  ```

- [ ] ✅ ¿Cómo se leen los datos del Excel?
  ```
  Respuesta: Ver ESTRATEGIA_AUTOMATIZACION.md sección 7
  Mostrar: ExcelReader.java
  ```

- [ ] ✅ ¿Qué diferencia hay entre Hard y Soft Assert?
  ```
  Respuesta: Ver ESTRATEGIA_AUTOMATIZACION.md sección 9
  Mostrar ejemplos en el código
  ```

- [ ] ✅ ¿Cómo se generan los logs?
  ```
  Respuesta: Ver ESTRATEGIA_AUTOMATIZACION.md sección 8
  Mostrar: LogWriter.java y log generado
  ```

### Material de Presentación
- [ ] ✅ Laptop con proyecto funcionando
- [ ] ✅ Internet disponible (para OpenCart)
- [ ] ✅ Backup del proyecto en USB
- [ ] ✅ PDF de documentación (opcional)
- [ ] ✅ Diapositivas (opcional)

---

## 📋 FASE 11: VERIFICACIÓN FINAL

### Funcionalidad
- [ ] ✅ Ejecutar pruebas una última vez
- [ ] ✅ Verificar que todo pasa (o entender por qué falla)
- [ ] ✅ Revisar que los logs tienen toda la información
- [ ] ✅ Confirmar que los reportes se generan correctamente

### Código
- [ ] ✅ Código está limpio y comentado
- [ ] ✅ No hay código comentado innecesario
- [ ] ✅ No hay errores de compilación
- [ ] ✅ No hay warnings importantes

### Documentación
- [ ] ✅ Todos los archivos .md están completos
- [ ] ✅ No hay errores de ortografía en documentos
- [ ] ✅ Todos los enlaces funcionan
- [ ] ✅ Ejemplos de código están correctos

### Entregables
- [ ] ✅ Carpeta ENTREGA_FINAL completa
- [ ] ✅ Todas las evidencias capturadas
- [ ] ✅ Excel con datos incluido
- [ ] ✅ Reportes incluidos
- [ ] ✅ Logs incluidos

---

## 📋 CHECKLIST DE CUMPLIMIENTO DE RÚBRICA

### Archivos Excel usados (5 pts)
- [ ] ✅ testData.xlsx creado y funcional
- [ ] ✅ 3 hojas con datos correctos
- [ ] ✅ Incluido en la entrega

### Evidencias de ejecución (5 pts)
- [ ] ✅ Capturas de pantalla (40+)
- [ ] ✅ Reportes HTML de TestNG
- [ ] ✅ Logs personalizados
- [ ] ✅ Todo organizado en carpeta

### Documento de estrategia (10 pts)
- [ ] ✅ ESTRATEGIA_AUTOMATIZACION.md completo
- [ ] ✅ 15+ páginas de contenido
- [ ] ✅ Todas las secciones cubiertas
- [ ] ✅ Bien estructurado

### Entregables estructurados (10 pts)
- [ ] ✅ Carpeta ENTREGA_FINAL organizada
- [ ] ✅ Código fuente completo
- [ ] ✅ Documentación incluida
- [ ] ✅ Estructura clara

### Presentación y defensa (70 pts)
- [ ] ✅ Demostración preparada
- [ ] ✅ Explicaciones técnicas listas
- [ ] ✅ Código entendido
- [ ] ✅ Respuestas preparadas

---

## ✅ CHECKLIST FINAL ANTES DE ENTREGAR

```
□ He ejecutado las pruebas exitosamente
□ Tengo todas las evidencias capturadas
□ El Excel tiene datos válidos
□ Los logs muestran toda la información requerida
□ Los reportes se generan correctamente
□ He revisado toda la documentación
□ Entiendo el código que escribí
□ Puedo explicar el patrón POM
□ Puedo explicar las esperas de Selenium
□ Puedo explicar Hard vs Soft Assert
□ Puedo demostrar el proyecto en vivo
□ Tengo backup del proyecto
□ La carpeta ENTREGA_FINAL está completa
□ Estoy listo para la presentación
```

---

## 🎉 ¡FELICITACIONES!

Si marcaste todas las casillas, estás completamente listo para entregar y presentar tu proyecto.

**Puntos clave para recordar en la presentación:**

1. **Patrón POM**: Separación de lógica de prueba y página
2. **Excel con Apache POI**: Data-driven testing
3. **Logs personalizados**: Registro detallado de ejecución
4. **Esperas**: Implícitas, Explícitas, Personalizadas
5. **Aserciones**: Hard (detiene) vs Soft (acumula)
6. **Código limpio**: Comentado y bien estructurado

---

**¡Mucha suerte en tu presentación! 🚀🎓**
