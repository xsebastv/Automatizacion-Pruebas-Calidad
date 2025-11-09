package com.demoblaze.test;

import com.demoblaze.pages.LoginPage;
import com.demoblaze.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

/**
 * Test de Inicio de Sesión
 * Lee credenciales desde Excel y valida login exitoso y fallido
 */
public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private ExcelReader excelReader;
    private String excelPath;

    @BeforeClass
    public void setupTest() {
        loginPage = new LoginPage(driver);
        excelPath = "src/main/resources/testData.xlsx";
        excelReader = new ExcelReader(excelPath);
        
        logWriter.logSection("PRUEBA DE INICIO DE SESIÓN");
    }

    @Test(priority = 1, description = "Login con diferentes credenciales desde Excel")
    public void testLoginUsuarios() {
        // Leer datos de login desde Excel
        List<Map<String, String>> credenciales = excelReader.readLoginData();
        
        logWriter.logMessage("Credenciales a probar: " + credenciales.size());
        
        SoftAssert softAssert = new SoftAssert();
        
        for (Map<String, String> credencial : credenciales) {
            String email = credencial.get("Email");
            String password = credencial.get("Password");
            String expectedResult = credencial.get("Expected Result"); // "Success" o "Fail"
            
            logWriter.logMessage("\n--- Probando login: " + email + " ---");
            
            try {
                // Navegar a la página de login
                loginPage.navigateToLoginPage();
                
                // Intentar hacer login
                boolean loginExitoso = loginPage.login(email, password);
                
                // Verificar según el resultado esperado
                if ("Success".equalsIgnoreCase(expectedResult)) {
                    if (loginExitoso) {
                        logWriter.logLogin(email, true, "Login exitoso como esperado");
                        softAssert.assertTrue(true, "Login exitoso: " + email);
                        
                        // Hacer logout para la próxima prueba
                        loginPage.logout();
                        Thread.sleep(1000);
                    } else {
                        String errorMsg = loginPage.getErrorMessage();
                        logWriter.logLogin(email, false, "Se esperaba éxito pero falló: " + errorMsg);
                        softAssert.fail("Login debería ser exitoso para: " + email);
                    }
                } else {
                    // Se espera que falle
                    if (!loginExitoso) {
                        String errorMsg = loginPage.getErrorMessage();
                        logWriter.logLogin(email, false, "Falló como esperado: " + errorMsg);
                        softAssert.assertTrue(true, "Login falló correctamente: " + email);
                    } else {
                        logWriter.logLogin(email, true, "Se esperaba fallo pero fue exitoso");
                        softAssert.fail("Login debería fallar para: " + email);
                        
                        // Hacer logout
                        loginPage.logout();
                        Thread.sleep(1000);
                    }
                }
                
                Thread.sleep(500);
                
            } catch (Exception e) {
                logWriter.logLogin(email, false, "Excepción: " + e.getMessage());
                softAssert.fail("Excepción al probar login " + email + ": " + e.getMessage());
            }
        }
        
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Validar mensaje de error con credenciales inválidas")
    public void testLoginCredencialesInvalidas() {
        logWriter.logMessage("\n--- Validando mensaje de error con credenciales inválidas ---");
        
        loginPage.navigateToLoginPage();
        loginPage.login("usuario_invalido@test.com", "password_invalido");
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), 
            "Debería mostrar mensaje de error");
        
        String errorMsg = loginPage.getErrorMessage();
        logWriter.logLogin("usuario_invalido@test.com", false, 
            "Mensaje de error mostrado: " + errorMsg);
        
        Assert.assertFalse(errorMsg.isEmpty(), 
            "El mensaje de error no debería estar vacío");
    }
}
