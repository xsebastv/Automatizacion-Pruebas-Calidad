package com.demoblaze.test;

import com.demoblaze.utils.LogWriter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

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

