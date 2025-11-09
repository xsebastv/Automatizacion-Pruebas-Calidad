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
     * Genera el archivo Excel con datos de prueba si no existe o está corrupto
     */
    private void generateExcelIfNotExists() {
        String filePath = "src/main/resources/testData.xlsx";
        File excelFile = new File(filePath);
        
        boolean needsGeneration = false;
        
        // Verificar si el archivo no existe
        if (!excelFile.exists()) {
            System.out.println("========================================");
            System.out.println("Archivo Excel no encontrado. Generando testData.xlsx...");
            System.out.println("========================================");
            needsGeneration = true;
        } 
        // Verificar si el archivo existe pero está vacío o corrupto
        else if (excelFile.length() < 1024) {
            System.out.println("========================================");
            System.out.println("Archivo Excel corrupto o vacío. Regenerando testData.xlsx...");
            System.out.println("========================================");
            excelFile.delete(); // Eliminar archivo corrupto
            needsGeneration = true;
        } 
        else {
            System.out.println("========================================");
            System.out.println("✓ Archivo Excel válido encontrado: " + filePath);
            System.out.println("   Tamaño: " + excelFile.length() + " bytes");
            System.out.println("========================================");
        }
        
        // Generar el archivo si es necesario
        if (needsGeneration) {
            ExcelDataGenerator.main(new String[]{});
            
            // Verificar que se generó correctamente
            if (excelFile.exists() && excelFile.length() > 1024) {
                System.out.println("✓ Archivo Excel generado exitosamente");
                System.out.println("   Tamaño: " + excelFile.length() + " bytes");
            } else {
                System.err.println("✗ ERROR: No se pudo generar el archivo Excel correctamente");
            }
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

