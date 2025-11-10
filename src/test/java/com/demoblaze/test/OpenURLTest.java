package com.demoblaze.test;

import com.demoblaze.pages.BasePage;
import com.demoblaze.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * OpenURLTest - Prueba de Apertura y Validación de URL
 * 
 * PROPÓSITO:
 * Test básico que verifica la correcta carga de la aplicación OpenCart.
 * Este test sirve como "smoke test" inicial para validar que la aplicación
 * está accesible y responde correctamente.
 * 
 * FUNCIONALIDAD:
 * 1. Abre la URL base de OpenCart
 * 2. Verifica que la página carga correctamente
 * 3. Valida que la URL actual coincide con la esperada
 * 4. Captura el título de la página
 * 5. Registra toda la información en el log
 * 
 * VALIDACIONES:
 * - URL contiene el dominio correcto (opencart.abstracta.us)
 * - La página responde sin errores
 * - El título se obtiene correctamente
 * 
 * DEPENDENCIAS:
 * - BasePage: Para navegación básica
 * - Constants: Para obtener la URL base
 * - LogWriter: Para registro de operaciones
 * 
 * USO:
 * mvn test -Dtest=OpenURLTest
 * 
 * @author Equipo de QA - San Buenaventura Cali
 * @version 1.0
 * @since 2025-11-10
 */
public class OpenURLTest extends BaseTest{

    /**
     * Test principal de apertura de URL
     * 
     * Flujo:
     * 1. Registra el inicio de la prueba en el log
     * 2. Crea una instancia de BasePage para navegación
     * 3. Navega a la URL base definida en Constants
     * 4. Espera 2 segundos para que la página cargue completamente
     * 5. Obtiene la URL actual y el título de la página
     * 6. Registra la información capturada
     * 7. Valida que la URL contiene el dominio esperado
     * 8. Maneja cualquier excepción y registra errores
     * 
     * @throws Exception si hay error al cargar la página o en la validación
     */
    @Test(description = "Abrir la URL de OpenCart y verificar que carga correctamente")
    public void OpenUrl(){
        // Registrar inicio de prueba en el log con sección específica
        logWriter.logSection("PRUEBA DE APERTURA DE URL");
        logWriter.logMessage("URL objetivo: " + Constants.BASE_URL);
        
        // Mostrar información en consola para seguimiento en tiempo real
        System.out.println("\n========================================");
        System.out.println("=== PRUEBA DE APERTURA DE URL ===");
        System.out.println("========================================");
        System.out.println("URL: " + Constants.BASE_URL);
        System.out.println("========================================\n");
        
        try {
            // Crear instancia de BasePage que contiene métodos de navegación básicos
            BasePage basePage = new BasePage(driver);
            
            // Navegar a la URL base de OpenCart
            logWriter.logMessage("→ Navegando a la página principal...");
            System.out.println("→ Navegando a la página principal...");
            basePage.navigateTo(Constants.BASE_URL);
            
            // Esperar 2 segundos para asegurar que la página cargue completamente
            // Esto es importante para páginas con contenido dinámico
            Thread.sleep(2000);
            
            // Capturar información de la página cargada
            String currentUrl = driver.getCurrentUrl();  // URL actual del navegador
            String pageTitle = driver.getTitle();         // Título de la página HTML
            
            // Registrar información capturada en el log
            logWriter.logMessage("✓ Página cargada exitosamente");
            logWriter.logMessage("   URL actual: " + currentUrl);
            logWriter.logMessage("   Título: " + pageTitle);
            
            // Mostrar misma información en consola
            System.out.println("✓ Página cargada exitosamente");
            System.out.println("   URL actual: " + currentUrl);
            System.out.println("   Título: " + pageTitle);
            
            // VALIDACIÓN CRÍTICA: Verificar que estamos en el dominio correcto
            // Si la URL no contiene 'opencart.abstracta.us', el test falla
            Assert.assertTrue(currentUrl.contains("opencart.abstracta.us"), 
                "La URL debería contener 'opencart.abstracta.us'");
            
            // Registrar resultado exitoso
            logWriter.logMessage("✓ Validación exitosa - URL correcta");
            System.out.println("✓ Validación exitosa - URL correcta\n");
            
            // Marcar fin exitoso de la prueba
            logWriter.logMessage("========================================");
            logWriter.logMessage("PRUEBA COMPLETADA EXITOSAMENTE");
            logWriter.logMessage("========================================");
            
        } catch (Exception e) {
            // Capturar cualquier error durante la ejecución
            logWriter.logMessage("✗ ERROR: " + e.getMessage());
            System.err.println("✗ ERROR: " + e.getMessage());
            e.printStackTrace();  // Imprimir stack trace completo para debugging
            
            // Fallar el test con mensaje descriptivo
            Assert.fail("Error al abrir la URL: " + e.getMessage());
        }
    }

}
