package com.fla.common.excel.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExcelMapperSheetDef {
    @XmlAttribute
    private String index;
    @XmlAttribute
    private String sheetMapRef;
    @XmlAttribute
    private int exportTemplateSheetIndex;

    public ExcelMapperSheetDef() {
    }

    public String getIndex() {
        return index;
    }

    public String getSheetMapRef() {
        return sheetMapRef;
    }

    public int getExportTemplateSheetIndex() {
        return exportTemplateSheetIndex;
    }
}
