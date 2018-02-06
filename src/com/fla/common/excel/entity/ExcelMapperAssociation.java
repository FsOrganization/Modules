package com.fla.common.excel.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExcelMapperAssociation {
    @XmlAttribute
    private String property;
    @XmlAttribute
    private String type;
    @XmlElement(name="result")
    private List<ExcelMapperCellResult> cellResultList = new ArrayList<ExcelMapperCellResult>();

    public ExcelMapperAssociation() {
    }

    public String getProperty() {
        return property;
    }

    public String getType() {
        return type;
    }

    public List<ExcelMapperCellResult> getCellResultList() {
        return cellResultList;
    }
}
