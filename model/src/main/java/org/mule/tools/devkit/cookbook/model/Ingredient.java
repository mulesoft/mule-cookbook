package org.mule.tools.devkit.cookbook.model;

import java.util.Date;

/**
 * Created by Mulesoft.
 */
public class Ingredient extends CookBookEntity {

    private String name;
    private double quantity;
    private UnitType unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public UnitType getUnit() {
        return unit;
    }

    public void setUnit(UnitType unit) {
        this.unit = unit;
    }
}
