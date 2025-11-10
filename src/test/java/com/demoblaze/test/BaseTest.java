package com.demoblaze.test;

import com.demoblaze.utils.ExcelDataGenerator;
import com.demoblaze.utils.LogWriter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.time.Duration;

/**
 * BaseTest - Clase Base para Todas las Pruebas Automatizadas
 * 
 * PROPÓSITO:
 * Clase padre que centraliza la configuración común para todos los tests.
 * Implementa el patrón "Test Setup" de TestNG para inicializar recursos
 * una sola vez antes de ejecutar toda la suite de pruebas.
 * 
 * RESPONSABILIDADES:
 * 1. Configuración del WebDriver (Selenium)
 * 2. Inicialización del sistema de logging
 * 3. Generación automática de datos de prueba (Excel)
 * 4. Configuración de timeouts y opciones del navegador
 * 5. Limpieza de recursos después de las pruebas
 * 
 * PATRÓN DE DISEÑO:
 * - Template Method: Define el flujo de setup/teardown
 * - Singleton: WebDriver y LogWriter compartidos por todos los tests
 * 
 * CICLO DE VIDA:
 * @BeforeSuite -> Se ejecuta UNA VEZ antes de todos los tests
 * [Ejecución de todos los tests]
 * @AfterSuite -> Se ejecuta UNA VEZ después de todos los tests
 * 
 * USO:
 * Todos los tests deben heredar de esta clase:
 * public class MiTest extends BaseTest { ... }
 * 
 * VARIABLES COMPARTIDAS:
 * - driver: Instancia de WebDriver (Chrome) accesible en todos los tests
 * - logWriter: Sistema de logging para registrar operaciones
 * 
 * @author Equipo de QA - San Buenaventura Cali
 * @version 1.0
 * @since 2025-11-10
 */
public class BaseTest {

    /**
     * WebDriver compartido por todos los tests
     * - protected: Accesible en clases hijas
     * - static: Una sola instancia para toda la suite
     * 
     * IMPORTANTE: No crear nuevas instancias de driver en los tests,
     * usar siempre esta variable heredada.
     */
    protected static WebDriver driver;
    
    /**
     * Sistema de logging compartido
     * - Genera archivos de log con timestamp
     * - Registra todas las operaciones de los tests
     * - Se cierra automáticamente en @AfterSuite
     */
    protected static LogWriter logWriter;

    /**
     * Método de configuración inicial - Se ejecuta UNA VEZ antes de todos los tests
     * 
     * ORDEN DE EJECUCIÓN:
     * 1. Generar datos de prueba (Excel)
     * 2. Configurar ChromeDriver automáticamente
     * 3. Configurar opciones del navegador
     * 4. Inicializar WebDriver
     * 5. Configurar timeouts
     * 6. Inicializar sistema de logging
     * 
     * CONFIGURACIONES APLICADAS:
     * - Navegador maximizado al iniciar
     * - Notificaciones deshabilitadas
     * - Pop-ups bloqueados
     * - Timeout implícito: 10 segundos
     * - Timeout de carga de página: 30 segundos
     * 
     * @throws Exception si hay error en la configuración inicial
     */
    @BeforeSuite
    public void setup() {
        // PASO 1: Generar archivo Excel con datos de prueba frescos
        // Esto asegura que los datos estén sincronizados para esta ejecución
        generateExcelIfNotExists();
        
        // PASO 2: Configurar ChromeDriver automáticamente
        // WebDriverManager descarga y configura el driver compatible con Chrome instalado
        WebDriverManager.chromedriver().setup();

        // PASO 3: Configurar opciones del navegador Chrome
        ChromeOptions options = new ChromeOptions();
        
        // Maximizar ventana al iniciar (mejor para visualización de elementos)
        options.addArguments("--start-maximized");
        
        // Deshabilitar notificaciones del navegador (evita pop-ups molestos)
        options.addArguments("--disable-notifications");
        
        // Deshabilitar bloqueo de pop-ups (algunos tests pueden requerir pop-ups)
        options.addArguments("--disable-popup-blocking");

        // PASO 4: Crear instancia de WebDriver con las opciones configuradas
        driver = new ChromeDriver(options);
        
        // PASO 5: Configurar timeouts globales
        
        // Timeout implícito: Tiempo de espera al buscar elementos
        // Si un elemento no existe, espera hasta 10 segundos antes de fallar
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Timeout de carga de página: Tiempo máximo para que una página cargue
        // Si la página tarda más de 30 segundos, se lanza TimeoutException
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        // PASO 6: Inicializar el sistema de logging
        // LogWriter crea un archivo de log con timestamp único
        logWriter = new LogWriter();
        
        // Registrar información inicial en el log
        logWriter.logMessage("=== INICIO DE EJECUCIÓN DE PRUEBAS ===");
        logWriter.logMessage("Navegador: Chrome");
        logWriter.logMessage("URL Base: https://opencart.abstracta.us/");
    }

    /**
     * Método de limpieza - Se ejecuta UNA VEZ después de todos los tests
     * 
     * PROPÓSITO:
     * Liberar recursos y cerrar conexiones abiertas durante las pruebas.
     * Es crítico para evitar procesos huérfanos del navegador.
     * 
     * ORDEN DE LIMPIEZA:
     * 1. Cerrar y guardar el archivo de log
     * 2. Cerrar el navegador y liberar recursos
     * 
     * IMPORTANTE:
     * - Este método SIEMPRE se ejecuta, incluso si los tests fallan
     * - Usa verificación null-safe para evitar NullPointerException
     * 
     * @throws Exception si hay error al cerrar recursos (no crítico)
     */
    @AfterSuite
    public void tearDown() {
        // PASO 1: Cerrar el sistema de logging
        if (logWriter != null) {
            // Registrar fin de ejecución en el log
            logWriter.logMessage("=== FIN DE EJECUCIÓN DE PRUEBAS ===");
            logWriter.logMessage("Ruta del log: " + logWriter.getLogFilePath());
            
            // Cerrar el archivo de log (flush y close del BufferedWriter)
            logWriter.close();
            
            // Informar al usuario dónde encontrar el log
            System.out.println("\n========================================");
            System.out.println("Log guardado en: " + logWriter.getLogFilePath());
            System.out.println("========================================\n");
        }

        // PASO 2: Cerrar el navegador y liberar recursos
        if (driver != null) {
            // quit() cierra todas las ventanas y finaliza la sesión de WebDriver
            // Diferente de close() que solo cierra la ventana actual
            driver.quit();
        }
    }

    /**
     * Genera archivo Excel con datos de prueba frescos
     * 
     * ESTRATEGIA:
     * - SIEMPRE elimina el Excel existente
     * - SIEMPRE genera uno nuevo con datos frescos
     * - Garantiza sincronización entre Registro y Login
     * 
     * IMPORTANCIA:
     * Si usáramos un Excel estático, los tests de Login podrían fallar
     * porque buscarían usuarios que ya existen de ejecuciones anteriores.
     * Regenerar el Excel con timestamps únicos evita este problema.
     * 
     * CONTENIDO GENERADO:
     * - Hoja 1: UsuariosRegistro (5 usuarios con emails únicos)
     * - Hoja 2: ProductosBusqueda (5 productos con cantidades)
     * - Hoja 3: LoginData (casos válidos e inválidos)
     * 
     * UBICACIÓN: src/main/resources/testData.xlsx
     * 
     * @see ExcelDataGenerator para detalles de generación
     */
    private void generateExcelIfNotExists() {
        String filePath = "src/main/resources/testData.xlsx";
        File excelFile = new File(filePath);

        // Si existe el archivo de una ejecución anterior, eliminarlo
        // Esto previene inconsistencias de datos entre ejecuciones
        if (excelFile.exists()) {
            System.out.println("========================================");
            System.out.println("Eliminando testData.xlsx previo para regenerar datos coherentes...");
            System.out.println("========================================");
            boolean deleted = excelFile.delete();
            if (!deleted) {
                // Advertencia si no se puede eliminar (puede estar abierto en Excel)
                System.out.println("No se pudo eliminar el Excel previo (puede estar abierto). Intenta cerrarlo si falla la lectura.");
            }
        }

        // Generar nuevo archivo Excel con datos frescos
        System.out.println("========================================");
        System.out.println("Generando testData.xlsx para esta ejecución...");
        System.out.println("========================================");
        
        // Llamar al generador de datos
        // ExcelDataGenerator.main() crea el archivo con timestamps únicos
        ExcelDataGenerator.main(new String[]{});

        // Verificar que el archivo se generó correctamente
        // Verificamos existencia Y tamaño mínimo (>1KB para asegurar que no está vacío)
        if (excelFile.exists() && excelFile.length() > 1024) {
            System.out.println("✓ Archivo Excel generado exitosamente");
            System.out.println("   Tamaño: " + excelFile.length() + " bytes");
            System.out.println("========================================");
        } else {
            // Error crítico: sin datos no se pueden ejecutar los tests
            System.err.println("✗ ERROR: No se pudo generar el archivo Excel correctamente");
        }
    }

    /**
     * Getter para obtener la instancia del WebDriver
     * 
     * USO:
     * WebDriver myDriver = getDriver();
     * 
     * @return Instancia compartida de WebDriver (Chrome)
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Getter para obtener la instancia del LogWriter
     * 
     * USO:
     * LogWriter logger = getLogWriter();
     * logger.logMessage("Mi mensaje");
     * 
     * @return Instancia compartida de LogWriter
     */
    public LogWriter getLogWriter() {
        return logWriter;
    }
}

