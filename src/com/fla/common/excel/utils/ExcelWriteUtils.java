package com.fla.common.excel.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.fla.common.excel.entity.ExcelMapper;
import com.fla.common.excel.entity.ExcelMapperAssociation;
import com.fla.common.excel.entity.ExcelMapperCellResult;
import com.fla.common.excel.entity.ExcelMapperCollection;
import com.fla.common.excel.entity.ExcelMapperColumnResult;
import com.fla.common.excel.entity.ExcelMapperSheetDef;
import com.fla.common.excel.entity.ExcelMapperSheetMap;
import com.fla.common.excel.util.Reflections;

public class ExcelWriteUtils {
    public static void exportExcel(ExcelMapper excelMapper, Workbook wbTarget, Map exportData, List sheetNames) {
        int numberOfTemplateSheets = wbTarget.getNumberOfSheets();

        int numberOfExportSheets = exportData.size();
        for (int i = 1; i <= numberOfExportSheets; i++) {
            ExcelMapperSheetDef sheetDef = excelMapper.getSheetDef(i);

            if (sheetDef == null) {
                continue;
            }
            Sheet currentSheet = wbTarget.cloneSheet(sheetDef.getExportTemplateSheetIndex() - 1);

            ExcelMapperSheetMap sheetMap = excelMapper.getSheetMap(i);
            if (sheetMap != null) {
                String sheetType = sheetMap.getType();
                Object dataOfExportSheet = exportData.get(String.format("sheet%d", i));
                for (ExcelMapperCellResult cellResultMapper : sheetMap.getCellResultList()) {
                    String property = cellResultMapper.getProperty();

                    Object cellValue = null;
                    if (sheetType.equalsIgnoreCase("map")) {
                        cellValue = ((Map) dataOfExportSheet).get(property);
                    } else {
                        cellValue = Reflections.invokeGetter(dataOfExportSheet, property);

                    }

                    String cellDef = cellResultMapper.getCell();
                    String[] cellDefTokenArray = cellDef.split(":");
                    int colIndex = parseColNumber(cellDefTokenArray[0]);
                    int rowIndex = Integer.parseInt(cellDefTokenArray[1]) - 1;

                    Cell cell = currentSheet.getRow(rowIndex).getCell(colIndex);
                    writeCellValue(cell, cellValue);
                }

                for (ExcelMapperAssociation associationMapper : sheetMap.getAssociationList()) {
                    String property = associationMapper.getProperty();
                    Object associationData = null;
                    if (sheetType.equalsIgnoreCase("map")) {
                        associationData = ((Map) dataOfExportSheet).get(property);
                    } else {
                        associationData = Reflections.invokeGetter(dataOfExportSheet, property);
                    }
                    writeExcelAssociation(currentSheet, associationData, associationMapper);
                }

                ExcelMapperCollection collectionMapper = sheetMap.getCollection();
                if (collectionMapper != null) {
                    String collectionProperty = collectionMapper.getProperty();
                    Object collectionData = null;
                    if (sheetType.equalsIgnoreCase("map")) {
                        collectionData = ((Map) dataOfExportSheet).get(collectionProperty);
                    } else {
                        collectionData = Reflections.invokeGetter(dataOfExportSheet, collectionProperty);
                    }
                    writeExcelCollection(currentSheet, collectionData, collectionMapper);
                }
            }
        }

        for (int i = numberOfTemplateSheets; i > 0; i--) {
            wbTarget.removeSheetAt(i - 1);
        }

        for (int i = 0; i < sheetNames.size(); i++) {
            wbTarget.setSheetName(i, (String) sheetNames.get(i));
        }
    }

    public static void writeExcelAssociation(Sheet sheet, Object assocationData, ExcelMapperAssociation assocationMapper) {
        String associationType = assocationMapper.getType();
        for (ExcelMapperCellResult cellMapper : assocationMapper.getCellResultList()) {
            String property = cellMapper.getProperty();

            String cellDef = cellMapper.getCell();
            String[] cellDefTokenArray = cellDef.split(":");
            int colIndex = parseColNumber(cellDefTokenArray[0]);
            int rowIndex = Integer.parseInt(cellDefTokenArray[1]) - 1;
            Cell cell = sheet.getRow(rowIndex).getCell(colIndex);

            Object cellValue = null;
            if (associationType.equalsIgnoreCase("map")) {
                cellValue = ((Map) assocationData).get(property);
            } else {
                cellValue = Reflections.invokeGetter(assocationData, property);
            }
            writeCellValue(cell, cellValue);
        }
    }

    public static void writeExcelCollection(Sheet sheet, Object collectionData, ExcelMapperCollection collectionMapper) {
        int lastRowNum = sheet.getLastRowNum();
        for (int i = collectionMapper.getStartrow(); i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            sheet.removeRow(row);
        }

        List collectionDataList = (List) collectionData;
        int collectionDataSize = collectionDataList.size();
        int startRowNum = collectionMapper.getStartrow();
        for (int i = startRowNum; i < startRowNum + collectionDataSize - 1; i++) {
            Row row = sheet.createRow(i);
            Row templateRow = sheet.getRow(startRowNum - 1);
            row.setRowStyle(templateRow.getRowStyle());

            int firstCellNum = templateRow.getFirstCellNum();
            int lastCellNum = templateRow.getLastCellNum();
            for (int j = firstCellNum; j < lastCellNum; j++) {
                Cell cell = row.createCell(j);
                Cell templateCell = templateRow.getCell(j);
                cell.setCellStyle(templateCell.getCellStyle());
            }
        }

        String collectionType = collectionMapper.getType();
        for (int i = 0; i < collectionDataList.size(); i++) {
            Object rowData = collectionDataList.get(i);
            for (ExcelMapperColumnResult columnMapper : collectionMapper.getColumnResultList()) {
                String columnProperty = columnMapper.getProperty();
                Object cellValue = null;
                if (collectionType.equalsIgnoreCase("map")) {
                    cellValue = ((Map) rowData).get(columnProperty);
                } else {
                    cellValue = Reflections.invokeGetter(rowData, columnProperty);
                }

                int colIndex = parseColNumber(columnMapper.getColumn());
                int rowIndex = collectionMapper.getStartrow() + i - 1;
                Cell cell = sheet.getRow(rowIndex).getCell(colIndex);

                writeCellValue(cell, cellValue);
            }
        }
    }

    public static void writeCellValue(Cell cell, Object cellValue) {
        if(cellValue == null){ cellValue = new String();}

        Class clazz = cellValue.getClass();
        if (clazz == Boolean.class) {
            cell.setCellValue((Boolean) cellValue);
        } else if (clazz == Integer.class) {
            cell.setCellValue((Integer) cellValue);
        } else if (clazz == Double.class) {
            cell.setCellValue((Double) cellValue);
        } else if (clazz == Float.class) {
            cell.setCellValue((Float) cellValue);
        } else if (clazz == String.class) {
            cell.setCellValue((String) cellValue);
        } else if (clazz == Date.class) {
            cell.setCellValue((Date) cellValue);
        } else if(clazz == BigDecimal.class){
            cell.setCellValue(((BigDecimal)cellValue).doubleValue());
        }
    }

    /**
     * Excel列号转为0开始的整型,不区分大小写
     * 如 A->0  AA->26
     */
    private static int parseColNumber(String text) {
        int result = 0;
        char[] charArray = text.toUpperCase().toCharArray();
        for (int i = 1; i <= text.length(); i++) {
            result = result * (i - 1) * 26 + (int) charArray[i - 1] - (int) 'A' + 1;
        }
        return result - 1;
    }
}
