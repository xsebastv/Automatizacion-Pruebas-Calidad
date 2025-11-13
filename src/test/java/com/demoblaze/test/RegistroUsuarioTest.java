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

    @BeforeClass
    public void setupTest() {
        registerPage = new RegisterPage(driver);
        String excelPath = "src/main/resources/testData.xlsx";
        excelReader = new ExcelReader(excelPath);
        
        logWriter.logSection("PRUEBA DE REGISTRO DE USUARIOS");
    }

    @Test(priority = 1, description = "Registro de usuarios desde Excel")
    public void testRegistroUsuarios() {
        // Leer datos de usuarios desde Excel
        List<Map<String, String>> usuarios = excelReader.readUsuariosRegistro();
        
        logWriter.logMessage("\n========================================");
        logWriter.logMessage("Usuarios a registrar: " + usuarios.size());
        logWriter.logMessage("========================================");
        
        SoftAssert softAssert = new SoftAssert();
        int registrosExitosos = 0;
        int registrosFallidos = 0;
        
        for (int i = 0; i < usuarios.size(); i++) {
            Map<String, String> usuario = usuarios.get(i);
            String firstName = usuario.get("First Name");
            String lastName = usuario.get("Last Name");
            String email = usuario.get("E-Mail");
            String telephone = usuario.get("Telephone");
            String password = usuario.get("Password");
            
            logWriter.logMessage("\n--- Usuario " + (i + 1) + "/" + usuarios.size() + ": " + email + " ---");
            System.out.println("\n=== Registrando usuario " + (i + 1) + "/" + usuarios.size() + ": " + email + " ===");
            
            try {
                // Navegar a la página de registro
                registerPage.navigateToRegisterPage();
                Thread.sleep(300);
                
                // Completar el formulario y enviar
                boolean registroExitoso = registerPage.registerUser(
                    firstName, lastName, email, telephone, password
                );
                
                // Verificar resultado
                if (registroExitoso) {
                    String successMsg = registerPage.getSuccessMessage();
                    logWriter.logRegistro(email, true, "✓ Registro exitoso: " + successMsg);
                    System.out.println("✓ Registro exitoso para: " + email);
                    softAssert.assertTrue(true, "Usuario registrado correctamente: " + email);
                    registrosExitosos++;
                } else {
                    String errorMsg = registerPage.getErrorMessage();
                    
                    // Verificar si el error es porque el usuario ya existe (esperado en re-ejecuciones)
                    if (errorMsg.contains("E-Mail Address is already registered")) {
                        logWriter.logRegistro(email, false, "⚠ Usuario ya existe (puede ser normal): " + errorMsg);
                        System.out.println("⚠ Usuario ya existe: " + email);
                        // No falla el test si el usuario ya existe
                        registrosFallidos++;
                    } else {
                        logWriter.logRegistro(email, false, "✗ Error inesperado: " + errorMsg);
                        System.out.println("✗ Error al registrar: " + email + " - " + errorMsg);
                        softAssert.fail("Error al registrar usuario: " + email + " - " + errorMsg);
                        registrosFallidos++;
                    }
                }
                
                // Esperar entre registros
                Thread.sleep(800);
                
            } catch (Exception e) {
                logWriter.logRegistro(email, false, "✗ Excepción: " + e.getMessage());
                System.out.println("✗ Excepción al registrar: " + email);
                e.printStackTrace();
                softAssert.fail("Excepción al registrar usuario " + email + ": " + e.getMessage());
                registrosFallidos++;
            }
        }
        
        // Resumen final
        logWriter.logMessage("\n========================================");
        logWriter.logMessage("RESUMEN DE REGISTRO DE USUARIOS");
        logWriter.logMessage("Total usuarios: " + usuarios.size());
        logWriter.logMessage("Registros exitosos: " + registrosExitosos);
        logWriter.logMessage("Registros fallidos: " + registrosFallidos);
        logWriter.logMessage("========================================");
        
        System.out.println("\n========================================");
        System.out.println("RESUMEN: " + registrosExitosos + " exitosos, " + registrosFallidos + " fallidos de " + usuarios.size() + " totales");
        System.out.println("========================================");
        
        softAssert.assertAll();
    }
}
