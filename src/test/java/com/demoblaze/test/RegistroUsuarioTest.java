package com.demoblaze.test;

import com.demoblaze.pages.RegisterPage;
import com.demoblaze.utils.ExcelReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

/**
 * Test de Registro de Usuarios
 * Lee datos desde Excel y registra usuarios en OpenCart
 */
public class RegistroUsuarioTest extends BaseTest {

    private RegisterPage registerPage;
    private ExcelReader excelReader;
    private String excelPath;

    @BeforeClass
    public void setupTest() {
        registerPage = new RegisterPage(driver);
        excelPath = "src/main/resources/testData.xlsx";
        excelReader = new ExcelReader(excelPath);
        
        logWriter.logSection("PRUEBA DE REGISTRO DE USUARIOS");
    }

    @Test(priority = 1, description = "Registro de usuarios desde Excel")
    public void testRegistroUsuarios() {
        // Leer datos de usuarios desde Excel
        List<Map<String, String>> usuarios = excelReader.readUsuariosRegistro();
        
        logWriter.logMessage("Usuarios a registrar: " + usuarios.size());
        
        SoftAssert softAssert = new SoftAssert();
        
        for (Map<String, String> usuario : usuarios) {
            String firstName = usuario.get("First Name");
            String lastName = usuario.get("Last Name");
            String email = usuario.get("E-Mail");
            String telephone = usuario.get("Telephone");
            String password = usuario.get("Password");
            
            logWriter.logMessage("\n--- Registrando usuario: " + email + " ---");
            
            try {
                // Navegar a la página de registro
                registerPage.navigateToRegisterPage();
                
                // Completar el formulario
                boolean registroExitoso = registerPage.registerUser(
                    firstName, lastName, email, telephone, password
                );
                
                // Verificar resultado
                if (registroExitoso) {
                    String successMsg = registerPage.getSuccessMessage();
                    logWriter.logRegistro(email, true, successMsg);
                    softAssert.assertTrue(true, "Usuario registrado correctamente: " + email);
                } else {
                    String errorMsg = registerPage.getErrorMessage();
                    logWriter.logRegistro(email, false, errorMsg);
                    softAssert.assertTrue(false, "Error al registrar usuario: " + email + " - " + errorMsg);
                }
                
                // Esperar un poco entre registros
                Thread.sleep(1000);
                
            } catch (Exception e) {
                logWriter.logRegistro(email, false, "Excepción: " + e.getMessage());
                softAssert.fail("Excepción al registrar usuario " + email + ": " + e.getMessage());
            }
        }
        
        softAssert.assertAll();
    }
}
