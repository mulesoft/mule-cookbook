/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.cookbook;

import java.util.ArrayList;
import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.cookbook.tutorial.service.Ingredient;
import com.cookbook.tutorial.service.Recipe;
import com.cookbook.tutorial.service.UnitType;

public class OmletteRecipe implements Callable {

    @Override
    public Object onCall(MuleEventContext eventContext) throws Exception {
        Recipe recipe = new Recipe();
        recipe.setName("Omelette");
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        Ingredient eggs = new Ingredient();
        eggs.setName("Large free-range eggs");
        eggs.setUnit(UnitType.UNIT);
        eggs.setQuantity(2.0);
        ingredients.add(eggs);

        Ingredient salt = new Ingredient();
        salt.setName("Sea Salt");
        salt.setUnit(UnitType.SPOONS);
        salt.setQuantity(0.5);
        ingredients.add(salt);

        Ingredient pepper = new Ingredient();
        pepper.setName("Freshly ground black pepper");
        pepper.setUnit(UnitType.SPOONS);
        pepper.setQuantity(0.5);
        ingredients.add(pepper);

        Ingredient butter = new Ingredient();
        butter.setName("Small knob butter");
        butter.setUnit(UnitType.UNIT);
        butter.setQuantity(1.0);
        ingredients.add(butter);

        Ingredient cheese = new Ingredient();
        cheese.setName("Small handful Cheddar cheese, grated");
        cheese.setUnit(UnitType.UNIT);
        cheese.setQuantity(1.0);
        ingredients.add(cheese);
        
        recipe.setIngredients(ingredients);

        List<String> directions = new ArrayList<String>();
        directions.add("Crack the eggs into a mixing bowl with a pinch of salt and pepper. Beat well with a fork.");
        directions.add("Put a small frying pan on a low heat and let it get hot.");
        directions.add("Add a small knob of butter. When the butter has melted and is bubbling, add your eggs and move the pan around to"
                + "spread them out evenly. When the omelette begins to cook and firm up, but still has a little raw egg on top, sprinkle over the"
                + "cheese, if using (I sometimes grate mine directly on to the omelette).");
        directions.add("Using a spatula, ease around the edges of the omelette, then fold it over in half. When it starts to turn golden"
                + "brown underneath, remove the pan from the heat and slide the omelette on to a plate.");
        
        recipe.setDirections(directions);
        return recipe;
    }

}
