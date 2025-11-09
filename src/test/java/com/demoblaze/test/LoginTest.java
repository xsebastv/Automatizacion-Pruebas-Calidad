package com.demoblaze.test;

import com.demoblaze.pages.LoginPage;
import com.demoblaze.pages.RegisterPage;
import com.demoblaze.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

/**
 * Test de Inicio de Sesión
 * Primero registra usuarios y luego valida login exitoso y fallido
 */
public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private RegisterPage registerPage;
    private ExcelReader excelReader;

    @BeforeClass
    public void setupTest() {
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        String excelPath = "src/main/resources/testData.xlsx";
        excelReader = new ExcelReader(excelPath);
        
        logWriter.logSection("PRUEBA DE INICIO DE SESIÓN");
    }

    @Test(priority = 1, description = "Registrar usuarios válidos para las pruebas de login")
    public void testRegistrarUsuariosParaLogin() {
        logWriter.logMessage("\n=== REGISTRANDO USUARIOS PARA PRUEBAS DE LOGIN ===");
        
        // Leer solo los primeros 2 usuarios del registro para hacer login después
        List<Map<String, String>> usuarios = excelReader.readUsuariosRegistro();
        
        SoftAssert softAssert = new SoftAssert();
        
        // Registrar solo los primeros 2 usuarios
        for (int i = 0; i < Math.min(2, usuarios.size()); i++) {
            Map<String, String> usuario = usuarios.get(i);
            String firstName = usuario.get("First Name");
            String lastName = usuario.get("Last Name");
            String email = usuario.get("E-Mail");
            String telephone = usuario.get("Telephone");
            String password = usuario.get("Password");
            
            logWriter.logMessage("\n--- Registrando usuario: " + email + " ---");
            
            try {
                registerPage.navigateToRegisterPage();
                boolean registroExitoso = registerPage.registerUser(
                    firstName, lastName, email, telephone, password
                );
                
                if (registroExitoso) {
                    logWriter.logMessage("✓ Usuario registrado exitosamente: " + email);
                } else {
                    logWriter.logMessage("⚠ Usuario ya existe o error en registro: " + email);
                }
                
                Thread.sleep(1000);
                
            } catch (Exception e) {
                logWriter.logMessage("⚠ Error registrando usuario: " + e.getMessage());
            }
        }
        
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Login con diferentes credenciales desde Excel", dependsOnMethods = "testRegistrarUsuariosParaLogin")
    public void testLoginUsuarios() {
        logWriter.logMessage("\n=== INICIANDO PRUEBAS DE LOGIN ===");
        
        // Leer datos de usuarios registrados para hacer login exitoso
        List<Map<String, String>> usuarios = excelReader.readUsuariosRegistro();
        
        // Leer datos de login que incluyen casos inválidos
        List<Map<String, String>> credenciales = excelReader.readLoginData();
        
        logWriter.logMessage("Credenciales a probar: " + credenciales.size());
        
        SoftAssert softAssert = new SoftAssert();
        
        for (Map<String, String> credencial : credenciales) {
            String email = credencial.get("Email");
            String password = credencial.get("Password");
            String expectedResult = credencial.get("Expected Result"); // "Success" o "Fail"
            
            // Si el email es de tipo "Success", usar el email real generado
            if ("Success".equalsIgnoreCase(expectedResult)) {
                // Buscar el usuario correspondiente en la lista de usuarios registrados
                for (Map<String, String> usuario : usuarios) {
                    String realEmail = usuario.get("E-Mail");
                    if (email.contains("juan.perez") && realEmail.contains("juan.perez")) {
                        email = realEmail;
                        password = usuario.get("Password");
                        break;
                    } else if (email.contains("maria.gonzalez") && realEmail.contains("maria.gonzalez")) {
                        email = realEmail;
                        password = usuario.get("Password");
                        break;
                    }
                }
            }
            
            logWriter.logMessage("\n--- Probando login: " + email + " ---");
            
            try {
                // Asegurarse de que no haya sesión activa antes de hacer login
                System.out.println("\n[Pre-Login] Verificando estado de sesión...");
                if (loginPage.isUserLoggedIn()) {
                    logWriter.logMessage("   ⚠ Sesión activa detectada, haciendo logout primero...");
                    System.out.println("⚠ Sesión activa encontrada, cerrando...");
                    loginPage.logout();
                    Thread.sleep(3000); // Espera de 3 segundos para que cookies se borren completamente
                }
                
                // Navegar a la página de login
                System.out.println("[Login] Navegando a página de login...");
                loginPage.navigateToLoginPage();
                Thread.sleep(1000); // Espera mayor para que cargue la página
                
                // Intentar hacer login
                System.out.println("[Login] Intentando login con: " + email);
                boolean loginExitoso = loginPage.login(email, password);
                
                // Verificar según el resultado esperado
                if ("Success".equalsIgnoreCase(expectedResult)) {
                    if (loginExitoso) {
                        logWriter.logLogin(email, true, "✓ Login exitoso como esperado");
                        System.out.println("✓ Login exitoso para: " + email);
                        softAssert.assertTrue(true, "Login exitoso: " + email);
                        
                        // Hacer logout para la próxima prueba
                        System.out.println("[Post-Login] Haciendo logout...");
                        loginPage.logout();
                        Thread.sleep(3000); // Espera de 3 segundos para que cookies se borren completamente
                        
                        // Verificar que logout fue exitoso
                        System.out.println("[Post-Logout] Verificando que sesión está cerrada...");
                        Thread.sleep(1000);
                        
                    } else {
                        String errorMsg = loginPage.getErrorMessage();
                        logWriter.logLogin(email, false, "✗ Se esperaba éxito pero falló: " + errorMsg);
                        System.out.println("✗ ERROR: Login falló para: " + email);
                        System.out.println("   Mensaje de error: " + errorMsg);
                        softAssert.fail("Login debería ser exitoso para: " + email);
                    }
                } else {
                    // Se espera que falle
                    if (!loginExitoso) {
                        String errorMsg = loginPage.getErrorMessage();
                        logWriter.logLogin(email, false, "Falló como esperado: " + errorMsg);
                        softAssert.assertTrue(true, "Login falló correctamente: " + email);
                    } else {
                        // Login exitoso cuando debería fallar
                        String currentUrl = loginPage.getCurrentUrl();
                        logWriter.logLogin(email, true, "⚠ PROBLEMA: Se esperaba fallo pero fue exitoso. URL: " + currentUrl);
                        System.out.println("⚠ PROBLEMA: Login exitoso para credencial inválida: " + email);
                        System.out.println("   URL actual: " + currentUrl);
                        System.out.println("   My Account visible: " + loginPage.isUserLoggedIn());
                        
                        softAssert.fail("Login debería fallar para: " + email);
                        
                        // Hacer logout de emergencia
                        loginPage.logout();
                        Thread.sleep(1500);
                    }
                }
                
                Thread.sleep(500);
                
            } catch (Exception e) {
                logWriter.logLogin(email, false, "Excepción: " + e.getMessage());
                softAssert.fail("Excepción al probar login " + email + ": " + e.getMessage());
                
                // Intentar logout de emergencia
                try {
                    loginPage.logout();
                } catch (Exception ex) {
                    // Ignorar errores de logout de emergencia
                }
            }
        }
        
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Validar mensaje de error con credenciales inválidas", dependsOnMethods = "testLoginUsuarios")
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
