package org.mule.tools.devkit.store.model.custom;

import org.mule.tools.devkit.store.model.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mulesoft.
 */
public class CustomEntity extends Entity implements Serializable {

    private String typeId;
    private String displayName;
    private boolean isCustom;
    private List<Field> fields;

    public boolean isQuerable() {
        return isQuerable;
    }

    public void setQuerable(boolean isQuerable) {
        this.isQuerable = isQuerable;
    }

    private boolean isQuerable;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }


    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
