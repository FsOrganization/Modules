package com.fla.common.excel.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.fla.common.excel.entity.ExceldpResult;
import com.fla.common.excel.entity.NameValueEntry;

public interface ExceldpCallbackService {
    /**
     * 导入成功后由应用系统处理导入的数据
     * 在处理时进行数据验证
     * @param excelData 每个Sheet对应一个entry，key的名称为sheet + 以1开始的序号
     *                  值则根据配置文件中的type决定
     * @return 结果将以json的格式返回客户端
     */
    public ExceldpResult<T> afterImportExcel(List<NameValueEntry> params,Map<String, Object> excelData);

    /**
     * Excel导出时需要先获取需要导出的数据
     * @param params 由客户端调用的js传入，若客户端未设置参数，则该参数的size=0
     * @return 每个Sheet对应一个entry，key的名称为sheet + 以1开始的序号
     *                  值则根据配置文件中的type决定
     */
    Map<String,Object> beforeExportExcelGetData(List<NameValueEntry> params);

    /**
     * Excel导出时设置每个Sheet的名字，注意名字不能重复
     * @param params 由客户端调用的js传入，若客户端未设置参数，则该参数的size=0
     * @return sheet名称列表
     */
    List<String> beforeExportExcelGetSheetName(List<NameValueEntry> params);
}
