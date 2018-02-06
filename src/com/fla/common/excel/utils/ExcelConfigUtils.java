package com.fla.common.excel.utils;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.fla.common.excel.entity.ExcelMapper;
import com.fla.common.excel.util.JaxbMapper;

public class ExcelConfigUtils {
	public static ExcelMapper generateExcelMapper(String excelMapperFile)
			throws Exception {
		ExcelMapper result = null;
		InputStream isExcelMapper = null;

		try 
		{
			isExcelMapper = ExcelConfigUtils.class.getResourceAsStream(String.format("/exceldp/mapper/%s.xml", excelMapperFile));
			String strExcelMapper = IOUtils.toString(isExcelMapper, "utf-8");
			result = (ExcelMapper) JaxbMapper.fromXml(strExcelMapper,ExcelMapper.class);
		} finally {
			IOUtils.closeQuietly(isExcelMapper);
		}

		return result;
	}
}
