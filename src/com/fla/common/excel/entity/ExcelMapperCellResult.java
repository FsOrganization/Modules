package com.fla.common.excel.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExcelMapperCellResult {
    @XmlAttribute
    private String property;
    @XmlAttribute
    private String cell;
    @XmlAttribute
    private String javaType;

    public ExcelMapperCellResult() {
    }

    public String getProperty() {
        return property;
    }

    public String getCell() {
        return cell;
    }

    public String getJavaType() {
        if(javaType == null) javaType = "string";
        return javaType;
    }
}
