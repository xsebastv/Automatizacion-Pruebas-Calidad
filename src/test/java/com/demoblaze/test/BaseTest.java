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
 * Clase base para todas las pruebas
 * Configura el WebDriver y el LogWriter
 */
public class BaseTest {

    protected static WebDriver driver;
    protected static LogWriter logWriter;

    @BeforeSuite
    public void setup() {
        // Generar archivo Excel si no existe
        generateExcelIfNotExists();
        
        // Configurar el Driver
        WebDriverManager.chromedriver().setup();

        // Configurar opciones de Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        // Crear una instancia de WebDriver para Chrome
        driver = new ChromeDriver(options);
        
        // Configurar timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        // Inicializar el LogWriter
        logWriter = new LogWriter();
        logWriter.logMessage("=== INICIO DE EJECUCIÓN DE PRUEBAS ===");
        logWriter.logMessage("Navegador: Chrome");
        logWriter.logMessage("URL Base: https://opencart.abstracta.us/");
    }

    @AfterSuite
    public void tearDown() {
        // Cerrar el log
        if (logWriter != null) {
            logWriter.logMessage("=== FIN DE EJECUCIÓN DE PRUEBAS ===");
            logWriter.logMessage("Ruta del log: " + logWriter.getLogFilePath());
            logWriter.close();
            System.out.println("\n========================================");
            System.out.println("Log guardado en: " + logWriter.getLogFilePath());
            System.out.println("========================================\n");
        }

        // Cerrar el navegador
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Genera SIEMPRE el archivo Excel con datos de prueba al inicio de la suite,
     * para mantener sincronía entre Registro y Login en la misma ejecución.
     */
    private void generateExcelIfNotExists() {
        String filePath = "src/main/resources/testData.xlsx";
        File excelFile = new File(filePath);

        // Si existe, lo eliminamos para evitar datos viejos/desincronizados
        if (excelFile.exists()) {
            System.out.println("========================================");
            System.out.println("Eliminando testData.xlsx previo para regenerar datos coherentes...");
            System.out.println("========================================");
            boolean deleted = excelFile.delete();
            if (!deleted) {
                System.out.println("No se pudo eliminar el Excel previo (puede estar abierto). Intenta cerrarlo si falla la lectura.");
            }
        }

        System.out.println("========================================");
        System.out.println("Generando testData.xlsx para esta ejecución...");
        System.out.println("========================================");
        ExcelDataGenerator.main(new String[]{});

        // Verificar que se generó correctamente
        if (excelFile.exists() && excelFile.length() > 1024) {
            System.out.println("✓ Archivo Excel generado exitosamente");
            System.out.println("   Tamaño: " + excelFile.length() + " bytes");
            System.out.println("========================================");
        } else {
            System.err.println("✗ ERROR: No se pudo generar el archivo Excel correctamente");
        }
    }

    /**
     * Obtiene la instancia del driver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Obtiene la instancia del LogWriter
     */
    public LogWriter getLogWriter() {
        return logWriter;
    }
}

