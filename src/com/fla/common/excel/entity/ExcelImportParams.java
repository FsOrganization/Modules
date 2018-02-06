package com.fla.common.excel.entity;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public class ExcelImportParams {
    private String excelMapper;
    private List<NameValueEntry> queryParams;
    private MultipartFile excelfile;

    public ExcelImportParams() {}

	public String getExcelMapper() {
		return excelMapper;
	}

	public void setExcelMapper(String excelMapper) {
		this.excelMapper = excelMapper;
	}

	public List<NameValueEntry> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(List<NameValueEntry> queryParams) {
		this.queryParams = queryParams;
	}

	public MultipartFile getExcelfile() {
		return excelfile;
	}

	public void setExcelfile(MultipartFile excelfile) {
		this.excelfile = excelfile;
	}

}
