package com.fla.common.excel.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExcelMapperCollection {
    @XmlAttribute
    private String property;
    @XmlAttribute
    private String type;
    @XmlAttribute
    private int startrow;

    @XmlElement(name="result")
    private List<ExcelMapperColumnResult> columnResultList = new ArrayList<ExcelMapperColumnResult>();

    public ExcelMapperCollection() {
    }

    public String getProperty() {
        return property;
    }

    public String getType() {
        return type;
    }

    public int getStartrow() {
        return startrow;
    }

    public List<ExcelMapperColumnResult> getColumnResultList() {
        return columnResultList;
    }
}
