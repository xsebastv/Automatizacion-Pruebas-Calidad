package com.demoblaze.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Clase para crear el archivo Excel con datos de prueba
 */
public class ExcelDataGenerator {

    public static void main(String[] args) {
        String filePath = "src/main/resources/testData.xlsx";
        
        try (Workbook workbook = new XSSFWorkbook()) {
            
            // Crear hoja de UsuariosRegistro
            createUsuariosRegistroSheet(workbook);
            
            // Crear hoja de LoginData
            createLoginDataSheet(workbook);
            
            // Crear hoja de ProductosBusqueda
            createProductosBusquedaSheet(workbook);
            
            // Guardar el archivo
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
            
            System.out.println("Archivo Excel creado exitosamente en: " + filePath);
            
        } catch (IOException e) {
            System.err.println("Error al crear el archivo Excel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createUsuariosRegistroSheet(Workbook workbook) {
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
            {"Juan", "Pérez", "juan.perez@test.com", "3001234567", "Test123!"},
            {"María", "González", "maria.gonzalez@test.com", "3007654321", "Test456!"},
            {"Carlos", "Rodríguez", "carlos.rodriguez@test.com", "3009876543", "Test789!"},
            {"Ana", "Martínez", "ana.martinez@test.com", "3005551234", "Test321!"},
            {"Luis", "García", "luis.garcia@test.com", "3008887777", "Test654!"}
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

    private static void createLoginDataSheet(Workbook workbook) {
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
            {"juan.perez@test.com", "Test123!", "Success"},
            {"maria.gonzalez@test.com", "Test456!", "Success"},
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
        
        // Agregar datos de ejemplo
        Object[][] data = {
            {"Desktops", "PC", "HP LP3065", "1"},
            {"Laptops & Notebooks", "", "MacBook", "2"},
            {"Components", "Monitors", "Apple Cinema 30", "1"},
            {"", "", "iPhone", "1"},
            {"Cameras", "", "Canon EOS 5D", "1"}
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
