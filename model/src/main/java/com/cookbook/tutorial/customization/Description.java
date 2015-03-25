package com.cookbook.tutorial.customization;

import java.util.List;

/**
 * This Class represents the description an entity and its fields.
 */
public class Description {

    /**
     *
     */
    private String name;
    private int entityId;
    private DataType dataType;
    private boolean isQuerable;
    private boolean isSortable;
    private String innerType;
    private List<Description> innerFields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public boolean isQuerable() {
        return isQuerable;
    }

    public void setQuerable(boolean isQuerable) {
        this.isQuerable = isQuerable;
    }

    public boolean isSortable() {
        return isSortable;
    }

    public void setSortable(boolean isSortable) {
        this.isSortable = isSortable;
    }

    public String getInnerType() {
        return innerType;
    }

    public void setInnerType(String innerType) {
        this.innerType = innerType;
    }

    public List<Description> getInnerFields() {
        return innerFields;
    }

    public void setInnerFields(List<Description> innerFields) {
        this.innerFields = innerFields;
    }
}
