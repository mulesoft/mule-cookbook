package com.cookbook.tutorial.model;

/**
 * Entity representing any of the foods or substances that are combined to make a particular dish.
 */
public class Ingredient extends CookBookEntity {

    /**
     * Amount required
     */
    private double quantity;

    /**
     * Unit type
     */
    private UnitType unit;

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
