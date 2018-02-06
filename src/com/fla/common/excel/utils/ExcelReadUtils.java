package com.fla.common.excel.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.fla.common.excel.entity.ExcelMapper;
import com.fla.common.excel.entity.ExcelMapperAssociation;
import com.fla.common.excel.entity.ExcelMapperCellResult;
import com.fla.common.excel.entity.ExcelMapperCollection;
import com.fla.common.excel.entity.ExcelMapperColumnResult;
import com.fla.common.excel.entity.ExcelMapperSheetMap;
import com.fla.common.excel.util.Reflections;

public class ExcelReadUtils {
	public static <T> T parseExcel(ExcelMapper excelMapper, Workbook workbook)
			throws Exception {
		HashMap<String, Object> resultExcel = new HashMap<String, Object>();
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			ExcelMapperSheetMap sheetMap = excelMapper.getSheetMap(i + 1);
			Object sheetResult = null;
			if (sheetMap != null) {
				sheetResult = parseExcelSheet(sheetMap, workbook, i);
			}
			resultExcel.put(String.format("sheet%d", i + 1), sheetResult);
		}

		return (T) resultExcel;
	}

	private static <T> T parseExcelSheet(ExcelMapperSheetMap sheetMapper,
			Workbook workbook, int sheetIndex) throws Exception {
		String sheetType = sheetMapper.getType();
		boolean isMapType = sheetType.equalsIgnoreCase("map");
		Object resultExcelSheet = null;
		if (isMapType) {
			resultExcelSheet = new HashMap<String, Object>();
		} else {
			resultExcelSheet = Class.forName(sheetType).newInstance();
		}

		Sheet sheet = workbook.getSheetAt(sheetIndex);

		List<ExcelMapperCellResult> cellResultMapperList = sheetMapper
				.getCellResultList();
		for (ExcelMapperCellResult cellResultMapper : cellResultMapperList) {
			String property = cellResultMapper.getProperty();
			Object sheetCellValue = parseExcelCell(cellResultMapper, sheet);
			if (isMapType) {
				((Map) resultExcelSheet).put(property, sheetCellValue);
			} else {
				Reflections.setFieldValue(resultExcelSheet, property,
						sheetCellValue);
			}
		}

		List<ExcelMapperAssociation> associationMapperList = sheetMapper
				.getAssociationList();
		for (ExcelMapperAssociation associationMapper : associationMapperList) {
			String property = associationMapper.getProperty();
			Object resultAssociation = parseExcelAssociation(associationMapper,
					sheet);
			if (isMapType) {
				((Map) resultExcelSheet).put(property, resultAssociation);
			} else {
				Reflections.setFieldValue(resultExcelSheet, property,
						resultAssociation);
			}
		}

		ExcelMapperCollection collectionMapper = sheetMapper.getCollection();
		if (collectionMapper != null) {
			String collectionProperty = collectionMapper.getProperty();
			Object resultCollection = parseExcelCollection(collectionMapper,
					sheet);
			if (isMapType) {
				((Map) resultExcelSheet).put(collectionProperty,
						resultCollection);
			} else {
				Reflections.setFieldValue(resultExcelSheet, collectionProperty,
						resultCollection);
			}
		}

		return (T) resultExcelSheet;
	}

	private static Object parseExcelCell(
			ExcelMapperCellResult cellResultMapper, Sheet sheet)
			throws Exception {
		String cell = cellResultMapper.getCell();
		String[] cellTokenArray = cell.split(":");
		int colIndex = parseColNumber(cellTokenArray[0]);
		int rowIndex = Integer.parseInt(cellTokenArray[1]) - 1;

		Object sheetCellValue = null;
		try {
			Cell sheetCell = sheet.getRow(rowIndex).getCell(colIndex);
			sheetCellValue = getCellValue(sheetCell);

			String javaType = cellResultMapper.getJavaType();
			if (javaType != null) {
				sheetCellValue = typeConvert(sheetCellValue, javaType);
			}
		} catch (Exception e) {
			// throw new
			// Exception(String.format("读取页[%s]的单元格[%s]时数据异常.",sheet.getSheetName(),cell));
			sheetCellValue = null;
		}
		return sheetCellValue;
	}

	private static Object parseExcelAssociation(
			ExcelMapperAssociation associationMapper, Sheet sheet)
			throws Exception {
		String associationType = associationMapper.getType();
		boolean isMapType = associationType.equalsIgnoreCase("map");
		Object resultAssociation = null;
		if (isMapType) {
			resultAssociation = new HashMap<String, Object>();
		} else {
			resultAssociation = Class.forName(associationType).newInstance();
		}
		List<ExcelMapperCellResult> cellResultMapperList = associationMapper
				.getCellResultList();
		for (ExcelMapperCellResult cellResultMapper : cellResultMapperList) {
			String property = cellResultMapper.getProperty();
			Object sheetCellValue = parseExcelCell(cellResultMapper, sheet);
			if (isMapType) {
				((Map) resultAssociation).put(property, sheetCellValue);
			} else {
				Reflections.setFieldValue(resultAssociation, property,
						sheetCellValue);
			}
		}

		return resultAssociation;
	}

	private static Object parseExcelCollection(
			ExcelMapperCollection collectionMapper, Sheet sheet)
			throws Exception {
		List resultCollection = new ArrayList();

		int startRow = collectionMapper.getStartrow() - 1;
		int lastRow = sheet.getLastRowNum();
		for (int i = startRow; i <= lastRow; i++) {
			String collectionMapperType = collectionMapper.getType();
			boolean isMapType = collectionMapperType.equalsIgnoreCase("map");
			Object resultRow = null;
			if (isMapType) {
				resultRow = new HashMap<String, Object>();
			} else {
				resultRow = Class.forName(collectionMapperType).newInstance();
			}

			List<ExcelMapperColumnResult> columnResultMapperList = collectionMapper
					.getColumnResultList();
			for (ExcelMapperColumnResult columnResultMapper : columnResultMapperList) {
				String property = columnResultMapper.getProperty();
				Object sheetCellValue = parseExcelColumn(columnResultMapper,
						sheet, i);
				if (isMapType) {
					((Map) resultRow).put(property, sheetCellValue);
				} else {
					try {
						Reflections.setFieldValue(resultRow, property,
								sheetCellValue);
					} catch (Exception e) {
						if (e instanceof IllegalArgumentException) {
							Class<?> propertyClass = Reflections
									.getAccessibleField(resultRow, property)
									.getType();
							if (propertyClass.equals(String.class))
								Reflections.setFieldValue(resultRow, property,
										String.valueOf(sheetCellValue));
						} else
							throw new RuntimeException(e);
					}
				}
			}

			resultCollection.add(resultRow);
		}

		return resultCollection;
	}

	private static Object parseExcelColumn(
			ExcelMapperColumnResult columnResultMapper, Sheet sheet, int rownum)
			throws Exception {
		String column = columnResultMapper.getColumn();
		int colIndex = parseColNumber(column);

		Object sheetCellValue = null;
		try {
			Cell sheetCell = sheet.getRow(rownum).getCell(colIndex);
			sheetCellValue = getCellValue(sheetCell);

			String javaType = columnResultMapper.getJavaType();
			if (javaType != null) {
				sheetCellValue = typeConvert(sheetCellValue, javaType);
			}

		} catch (Exception e) {
			// throw new
			// Exception(String.format("读取页[%s]的单元格[%s:%d]时数据异常.",sheet.getSheetName(),column,rownum+1));
			sheetCellValue = null;
		}

		return sheetCellValue;
	}

	/**
	 * Excel列号转为0开始的整型,不区分大小写 如 A->0 AA->26
	 */
	private static int parseColNumber(String text) {
		int result = 0;
		char[] charArray = text.toUpperCase().toCharArray();
		for (int i = 1; i <= text.length(); i++) {
			result = result * (i - 1) * 26 + (int) charArray[i - 1] - (int) 'A'
					+ 1;
		}
		return result - 1;
	}

	private static Object getCellValue(Cell sheetCell) {
		Object sheetCellValue = null;

		switch (sheetCell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			sheetCellValue = sheetCell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(sheetCell)) {
				sheetCellValue = sheetCell.getDateCellValue();
			} else {
				sheetCellValue = sheetCell.getNumericCellValue();
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			sheetCellValue = sheetCell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_BLANK:
			sheetCellValue = null;
			break;
		case Cell.CELL_TYPE_FORMULA:
			switch (sheetCell.getCachedFormulaResultType()) {
			case Cell.CELL_TYPE_NUMERIC:
				sheetCellValue = sheetCell.getNumericCellValue();
				break;
			case Cell.CELL_TYPE_STRING:
				sheetCellValue = sheetCell.getStringCellValue();
				break;
			}
			break;
		case Cell.CELL_TYPE_ERROR:
			sheetCellValue = sheetCell.getErrorCellValue();
			break;
		}

		return sheetCellValue;
	}

	/**
	 * 转换单元格的数据类型
	 */
	private static Object typeConvert(Object source, String targetType) {
		Object result = source;

		if ("date".equalsIgnoreCase(targetType)) {
			if (!(result instanceof Date)) {
				result = DateUtil.getJavaDate((Double) source);
			}
		} else if ("decimal".equalsIgnoreCase(targetType)) {
			result = BigDecimal.valueOf((Double) source);
		} else if ("string".equalsIgnoreCase(targetType)) {
			if (result != null
					&& (result instanceof Double || result instanceof Float)) {
				result = String.format("%.0f", result);
			}
		} else {
			Class targetClass = String.class;
			if ("int".equalsIgnoreCase(targetType)) {
				targetClass = Integer.class;
			} else if ("integer".equalsIgnoreCase(targetType)) {
				targetClass = Integer.class;
			} else if ("long".equalsIgnoreCase(targetType)) {
				targetClass = Long.class;
			} else if ("float".equalsIgnoreCase(targetType)) {
				targetClass = Float.class;
			} else if ("double".equalsIgnoreCase(targetType)) {
				targetClass = Double.class;
			}
			result = ConvertUtils.convert(source.toString(), targetClass);
		}

		return result;
	}
}
