package com.fla.common.excel.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement(name="excelMap")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExcelMapper {
    @XmlElement
    private String importTemplate;
    @XmlElement
    private String exportTemplate;
    @XmlElement
    private String callbackBean;

    @XmlElement(name="sheetDef")
    private List<ExcelMapperSheetDef> sheetDefList = new ArrayList<ExcelMapperSheetDef>();    //Excel的Sheet列表

    @XmlElement(name="sheetMap")
    private List<ExcelMapperSheetMap> sheetMapList = new ArrayList<ExcelMapperSheetMap>();    //excel的Sheet映射定义

    public ExcelMapper() {
    }

    public String getImportTemplate() {
        return importTemplate;
    }

    public String getExportTemplate() {
        return exportTemplate;
    }

    public String getCallbackBean() {
        return callbackBean;
    }

    public List<ExcelMapperSheetDef> getSheetDefList() {
        return sheetDefList;
    }

    public List<ExcelMapperSheetMap> getSheetMapList() {
        return sheetMapList;
    }

    public ExcelMapperSheetDef getSheetDef(int sheetIndex){
        Map<String,ExcelMapperSheetDef> sheetDefMap = new HashMap<String, ExcelMapperSheetDef>();
        for(ExcelMapperSheetDef sheetDef : this.sheetDefList){
            sheetDefMap.put(sheetDef.getIndex().toLowerCase(),sheetDef);
        }
        String strSheetIndex = Integer.toString(sheetIndex);
        if(sheetDefMap.containsKey(strSheetIndex)){
            return sheetDefMap.get(strSheetIndex);
        }else{
            if (sheetDefMap.containsKey("any"))
                return sheetDefMap.get("any");
            else
                return null;
        }
    }

    public ExcelMapperSheetMap getSheetMap(int sheetIndex){
        Map<String,ExcelMapperSheetDef> sheetDefMap = new HashMap<String,ExcelMapperSheetDef>();
        for(ExcelMapperSheetDef sheetDef : this.sheetDefList){
            sheetDefMap.put(sheetDef.getIndex().toLowerCase(),sheetDef);
        }

        String sheetMapRef = null;
        String strSheetIndex = Integer.toString(sheetIndex);
        if(sheetDefMap.containsKey(strSheetIndex)){
            sheetMapRef = sheetDefMap.get(strSheetIndex).getSheetMapRef();
        }else{
            sheetMapRef = sheetDefMap.get("any").getSheetMapRef();
        }

        if(sheetMapRef.equalsIgnoreCase("null")) return null;

        Map<String,ExcelMapperSheetMap> sheetMaps = new HashMap<String, ExcelMapperSheetMap>();
        for(ExcelMapperSheetMap sheetMap : this.sheetMapList){
            sheetMaps.put(sheetMap.getId(),sheetMap);
        }

        return sheetMaps.get(sheetMapRef);
    }
}
