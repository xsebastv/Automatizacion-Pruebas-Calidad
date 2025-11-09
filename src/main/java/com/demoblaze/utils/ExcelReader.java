package com.demoblaze.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase utilitaria para leer datos desde archivos Excel usando Apache POI
 */
public class ExcelReader {

    private String filePath;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Lee datos de una hoja específica y los devuelve como lista de mapas
     * @param sheetName nombre de la hoja a leer
     * @return Lista de mapas donde cada mapa representa una fila (clave = nombre columna, valor = dato)
     */
    public List<Map<String, String>> readSheet(String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            
            if (sheet == null) {
                System.err.println("La hoja '" + sheetName + "' no existe en el archivo.");
                return data;
            }
            
            // Obtener nombres de columnas de la primera fila
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                System.err.println("La hoja está vacía.");
                return data;
            }
            
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValueAsString(cell));
            }
            
            // Leer datos de las filas subsecuentes
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String value = (cell != null) ? getCellValueAsString(cell) : "";
                    rowData.put(headers.get(j), value);
                }
                
                // Solo agregar filas que no estén completamente vacías
                if (!isRowEmpty(rowData)) {
                    data.add(rowData);
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error al leer el archivo Excel: " + e.getMessage());
            e.printStackTrace();
        }
        
        return data;
    }

    /**
     * Convierte el valor de una celda a String
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // Convertir número a string sin notación científica
                    double numValue = cell.getNumericCellValue();
                    if (numValue == (long) numValue) {
                        return String.valueOf((long) numValue);
                    } else {
                        return String.valueOf(numValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    /**
     * Verifica si una fila está completamente vacía
     */
    private boolean isRowEmpty(Map<String, String> rowData) {
        for (String value : rowData.values()) {
            if (value != null && !value.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Lee datos de usuarios para registro
     */
    public List<Map<String, String>> readUsuariosRegistro() {
        return readSheet("UsuariosRegistro");
    }

    /**
     * Lee datos de login
     */
    public List<Map<String, String>> readLoginData() {
        return readSheet("LoginData");
    }

    /**
     * Lee datos de productos para búsqueda
     */
    public List<Map<String, String>> readProductosBusqueda() {
        return readSheet("ProductosBusqueda");
    }
}
