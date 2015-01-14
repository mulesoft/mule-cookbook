package org.mule.tools.devkit.store.model.custom;

import javax.xml.crypto.Data;

/**
 * Created by Mulesoft.
 */
public class Field {
    private String id;
    private String displayName;
    private DataType dataType;
    private Object value;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
