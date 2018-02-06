package com.fla.common.excel.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExcelMapperColumnResult {
    @XmlAttribute
    private String property;
    @XmlAttribute
    private String column;
    @XmlAttribute
    private String javaType;

    public ExcelMapperColumnResult() {
    }

    public String getProperty() {
        return property;
    }

    public String getColumn() {
        return column;
    }

    public String getJavaType() {
        return javaType;
    }
}
