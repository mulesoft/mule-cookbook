package org.mule.tools.devkit.cookbook.model;

import java.util.List;

/**
 * Created by Mulesoft.
 */
public class Recipe extends CookBookEntity{
    private String name;
    private List<Ingredient> ingredients;
    private Double prepTime;
    private Double cookTime;
    private List<String> directions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Double getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Double prepTime) {
        this.prepTime = prepTime;
    }

    public Double getCookTime() {
        return cookTime;
    }

    public void setCookTime(Double cookTime) {
        this.cookTime = cookTime;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }
}
