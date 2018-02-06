package com.fla.common.excel.entity;

import java.util.List;

public class ExcelExportParams {
    private String excelMapper;
    private String targetFileName;
    private List<NameValueEntry> queryParams;

    public ExcelExportParams() {
    }

    public String getExcelMapper() {
        return excelMapper;
    }

    public void setExcelMapper(String excelMapper) {
        this.excelMapper = excelMapper;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }

    public List<NameValueEntry> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<NameValueEntry> queryParams) {
        this.queryParams = queryParams;
    }
}
