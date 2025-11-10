package com.demoblaze.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase para crear el archivo Excel con datos de prueba
 */
public class ExcelDataGenerator {

    public static void main(String[] args) {
        String filePath = "src/main/resources/testData.xlsx";

        // Timestamp único para esta generación (mantener coherencia entre hojas)
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        
        // Crear el directorio si no existe
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
            System.out.println("Directorio creado: " + parentDir.getPath());
        }
        
        try (Workbook workbook = new XSSFWorkbook()) {
            
            // Crear hoja de UsuariosRegistro
            createUsuariosRegistroSheet(workbook, timestamp);
            
            // Crear hoja de LoginData
            createLoginDataSheet(workbook, timestamp);
            
            // Crear hoja de ProductosBusqueda
            createProductosBusquedaSheet(workbook);
            
            // Guardar el archivo
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
            
            System.out.println("✓ Archivo Excel creado exitosamente en: " + filePath);
            
        } catch (IOException e) {
            System.err.println("Error al crear el archivo Excel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createUsuariosRegistroSheet(Workbook workbook, String timestamp) {
        Sheet sheet = workbook.createSheet("UsuariosRegistro");
        
        // Crear estilo para el encabezado
        CellStyle headerStyle = createHeaderStyle(workbook);
        
        // Crear encabezado
        Row headerRow = sheet.createRow(0);
        String[] headers = {"First Name", "Last Name", "E-Mail", "Telephone", "Password"};
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Agregar datos de ejemplo
        Object[][] data = {
            {"Juan", "Pérez", "juan.perez+" + timestamp + "@test.com", "3001234567", "Test123!"},
            {"María", "González", "maria.gonzalez+" + timestamp + "@test.com", "3007654321", "Test456!"},
            {"Carlos", "Rodríguez", "carlos.rodriguez+" + timestamp + "@test.com", "3009876543", "Test789!"},
            {"Ana", "Martínez", "ana.martinez+" + timestamp + "@test.com", "3005551234", "Test321!"},
            {"Luis", "García", "luis.garcia+" + timestamp + "@test.com", "3008887777", "Test654!"}
        };
        
        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                row.createCell(j).setCellValue(data[i][j].toString());
            }
        }
        
        // Ajustar ancho de columnas
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static void createLoginDataSheet(Workbook workbook, String timestamp) {
        Sheet sheet = workbook.createSheet("LoginData");
        
        CellStyle headerStyle = createHeaderStyle(workbook);
        
        // Crear encabezado
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Email", "Password", "Expected Result"};
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Agregar datos de ejemplo
        Object[][] data = {
            {"juan.perez+" + timestamp + "@test.com", "Test123!", "Success"},
            {"maria.gonzalez+" + timestamp + "@test.com", "Test456!", "Success"},
            {"usuario.invalido@test.com", "password_invalido", "Fail"},
            {"", "", "Fail"},
            {"test@test.com", "wrongpassword", "Fail"}
        };
        
        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                row.createCell(j).setCellValue(data[i][j].toString());
            }
        }
        
        // Ajustar ancho de columnas
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static void createProductosBusquedaSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("ProductosBusqueda");
        
        CellStyle headerStyle = createHeaderStyle(workbook);
        
        // Crear encabezado
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Categoria", "SubCategoria", "Producto", "Cantidad"};
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Agregar datos de ejemplo - PRODUCTOS SIMPLES SIN OPCIONES COMPLEJAS
        Object[][] data = {
            {"Laptops & Notebooks", "", "MacBook", "1"},
            {"", "", "iPhone", "1"},
            {"Cameras", "", "Canon EOS 5D", "1"},
            {"Laptops & Notebooks", "Macs", "MacBook Air", "2"},
            {"Tablets", "", "Samsung Galaxy Tab 10.1", "1"}
        };
        
        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                row.createCell(j).setCellValue(data[i][j].toString());
            }
        }
        
        // Ajustar ancho de columnas
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }
}
