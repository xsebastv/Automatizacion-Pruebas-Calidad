package com.demoblaze.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase para escribir logs de ejecución de pruebas
 */
public class LogWriter {

    private String logFilePath;
    private BufferedWriter writer;
    private SimpleDateFormat dateFormat;

    /**
     * Constructor que crea el archivo de log con timestamp
     */
    public LogWriter() {
        dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());
        String logFileName = "TestLog_" + timestamp + ".txt";
        
        // Crear directorio de logs si no existe
        File logDir = new File("src/main/resources/logs");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        
        this.logFilePath = logDir.getPath() + File.separator + logFileName;
        
        try {
            writer = new BufferedWriter(new FileWriter(logFilePath, true));
            writeHeader();
        } catch (IOException e) {
            System.err.println("Error al crear archivo de log: " + e.getMessage());
        }
    }

    /**
     * Constructor con ruta personalizada
     */
    public LogWriter(String customPath) {
        dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        this.logFilePath = customPath;
        
        try {
            File file = new File(logFilePath);
            file.getParentFile().mkdirs();
            writer = new BufferedWriter(new FileWriter(logFilePath, true));
            writeHeader();
        } catch (IOException e) {
            System.err.println("Error al crear archivo de log: " + e.getMessage());
        }
    }

    /**
     * Escribe el encabezado del log
     */
    private void writeHeader() throws IOException {
        String separator = "========================================";
        writer.write(separator);
        writer.newLine();
        writer.write("REPORTE DE EJECUCIÓN DE PRUEBAS - OpenCart");
        writer.newLine();
        writer.write("Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        writer.newLine();
        writer.write(separator);
        writer.newLine();
        writer.newLine();
        writer.flush();
    }

    /**
     * Escribe información de registro de usuario
     */
    public void logRegistro(String email, boolean exitoso, String mensaje) {
        try {
            writer.write("[REGISTRO] ");
            writer.write(getCurrentTimestamp());
            writer.newLine();
            writer.write("  Email: " + email);
            writer.newLine();
            writer.write("  Estado: " + (exitoso ? "EXITOSO" : "FALLIDO"));
            writer.newLine();
            if (mensaje != null && !mensaje.isEmpty()) {
                writer.write("  Mensaje: " + mensaje);
                writer.newLine();
            }
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error al escribir en log: " + e.getMessage());
        }
    }

    /**
     * Escribe información de login
     */
    public void logLogin(String email, boolean exitoso, String mensaje) {
        try {
            writer.write("[LOGIN] ");
            writer.write(getCurrentTimestamp());
            writer.newLine();
            writer.write("  Email: " + email);
            writer.newLine();
            writer.write("  Estado: " + (exitoso ? "EXITOSO" : "FALLIDO"));
            writer.newLine();
            if (mensaje != null && !mensaje.isEmpty()) {
                writer.write("  Mensaje: " + mensaje);
                writer.newLine();
            }
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error al escribir en log: " + e.getMessage());
        }
    }

    /**
     * Escribe información de producto agregado al carrito
     */
    public void logProductoAgregado(String categoria, String subCategoria, String producto, 
                                     String cantidad, boolean exitoso) {
        try {
            writer.write("[PRODUCTO AGREGADO] ");
            writer.write(getCurrentTimestamp());
            writer.newLine();
            writer.write("  Categoría: " + categoria);
            writer.newLine();
            if (subCategoria != null && !subCategoria.isEmpty()) {
                writer.write("  Subcategoría: " + subCategoria);
                writer.newLine();
            }
            writer.write("  Producto: " + producto);
            writer.newLine();
            writer.write("  Cantidad: " + cantidad);
            writer.newLine();
            writer.write("  Estado: " + (exitoso ? "EXITOSO" : "FALLIDO"));
            writer.newLine();
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error al escribir en log: " + e.getMessage());
        }
    }

    /**
     * Escribe información de verificación de carrito
     */
    public void logVerificacionCarrito(int productosEsperados, int productosEncontrados, 
                                        boolean exitoso) {
        try {
            writer.write("[VERIFICACIÓN CARRITO] ");
            writer.write(getCurrentTimestamp());
            writer.newLine();
            writer.write("  Productos Esperados: " + productosEsperados);
            writer.newLine();
            writer.write("  Productos Encontrados: " + productosEncontrados);
            writer.newLine();
            writer.write("  Estado: " + (exitoso ? "EXITOSO" : "FALLIDO"));
            writer.newLine();
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error al escribir en log: " + e.getMessage());
        }
    }

    /**
     * Escribe un mensaje genérico en el log
     */
    public void logMessage(String message) {
        try {
            writer.write(getCurrentTimestamp() + " - " + message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error al escribir en log: " + e.getMessage());
        }
    }

    /**
     * Escribe un separador de secciones
     */
    public void logSection(String sectionName) {
        try {
            writer.newLine();
            writer.write("========== " + sectionName + " ==========");
            writer.newLine();
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error al escribir en log: " + e.getMessage());
        }
    }

    /**
     * Obtiene el timestamp actual formateado
     */
    private String getCurrentTimestamp() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    /**
     * Cierra el writer
     */
    public void close() {
        try {
            if (writer != null) {
                writer.write("\n========== FIN DEL REPORTE ==========\n");
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Error al cerrar el log: " + e.getMessage());
        }
    }

    /**
     * Obtiene la ruta del archivo de log
     */
    public String getLogFilePath() {
        return logFilePath;
    }
}
