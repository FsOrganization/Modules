package com.fla.common.excel.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExcelMapperSheetMap {
    @XmlAttribute
    private String id;
    @XmlAttribute
    private String type;

    @XmlElement(name="result")
    private List<ExcelMapperCellResult> cellResultList = new ArrayList<ExcelMapperCellResult>();

    @XmlElement(name="association")
    private List<ExcelMapperAssociation> associationList = new ArrayList<ExcelMapperAssociation>();

    @XmlElement(name="collection")
    private ExcelMapperCollection collection;

    public ExcelMapperSheetMap() {
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public List<ExcelMapperCellResult> getCellResultList() {
        return cellResultList;
    }

    public List<ExcelMapperAssociation> getAssociationList() {
        return associationList;
    }

    public ExcelMapperCollection getCollection() {
        return collection;
    }
}
