package com.cookbook.tutorial.model;

import java.util.List;

/**
 * Entity representing a set of instructions for preparing a particular dish, including a list of the ingredients required.
 */
public class Recipe extends CookBookEntity{

    /**
     * List of ingredients required to make this recipe.
     */
    private List<Ingredient> ingredients;

    /**
     * Estimated time required to prepare it.
     */
    private Double prepTime;

    /**
     * Estimated time required to cook it.
     */
    private Double cookTime;

    /**
     * List of steps that you need to follow in order to make this recipe.
     */
    private List<String> directions;

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
