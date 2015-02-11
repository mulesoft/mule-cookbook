package com.cookbook.tutorial.service;

import com.cookbook.tutorial.internal.service.CookBookDefaultBackEndImp;
import org.junit.Before;
import org.junit.Test;
import sun.security.krb5.internal.crypto.Des;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Mulesoft.
 */
public class DataSenseTest {
    MuleCookBookServiceImpl server;
    Recipe created;
    @Before
    public void setup() throws SessionExpiredException, InvalidEntityException {
        server = new MuleCookBookServiceImpl();
        server.setServiceDAL(new CookBookDefaultBackEndImp());
        Recipe recipe = new Recipe();
        recipe.setName("FooRecipe");
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        Ingredient firsItem = new Ingredient();
        firsItem.setId(2);
        firsItem.setName("Orange");
        ingredients.add(firsItem);

        Ingredient secondItem = new Ingredient();
        secondItem.setId(3);
        secondItem.setName("Cream");
        ingredients.add(secondItem);
        recipe.setIngredients(ingredients);
        created = (Recipe) server.create(recipe);
    }

    @Test
    public void testDescribeIngredient() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException {
        Description description = server.describeEntity(new Ingredient());
        assertEquals(description.dataType,DataType.OBJECT);
        assertEquals(6,description.getInnerFields().size());
        assertTrue(description.getQuerable());
        assertFalse(description.getSortable());
        assertNull(description.getInnerType());
        assertSame("Ingredient",description.getName());

        List<Description> fields = description.getInnerFields();
        Description field = fields.get(0);
        assertSame("id",field.getName());
        assertTrue(field.getQuerable());
        assertTrue(field.getSortable());
    }

    @Test
    public void testDescribeRecipe() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException {
        Description description = server.describeEntity(new Recipe());
        assertEquals(description.dataType,DataType.OBJECT);
        assertEquals(8,description.getInnerFields().size());
        assertTrue(description.getQuerable());
        assertFalse(description.getSortable());
        assertNull(description.getInnerType());
        assertSame( "Recipe",description.getName());

        List<Description> fields = description.getInnerFields();
        Description field = fields.get(0);
        assertSame("id",field.getName());
        assertTrue(field.getQuerable());
        assertTrue(field.getSortable());

        Description ingredientsField = fields.get(4);
        assertSame("ingredients",ingredientsField.getName());
        assertTrue(ingredientsField.getQuerable());
        assertEquals(DataType.LIST,ingredientsField.getDataType());
        assertSame("Ingredient",ingredientsField.getInnerType());

        Description directionsField = fields.get(7);
        assertSame("directions",directionsField.getName());
        assertFalse(directionsField.getQuerable());
        assertFalse(directionsField.getSortable());
        assertEquals(DataType.LIST,directionsField.getDataType());
        assertSame("String",directionsField.getInnerType());
    }

    @Test(expected = InvalidEntityException.class)
    public void getSpecificIngredientDescription() throws NoSuchEntityException, SessionExpiredException, InvalidEntityException {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(7);
        server.describeEntity(ingredient);
    }


    @Test
    public void getSpecificRecipeDescription() throws NoSuchEntityException, SessionExpiredException, InvalidEntityException {
        Recipe recipe = new Recipe();
        recipe.setId(created.getId());
        Description description=server.describeEntity(recipe);
        Description ingredients = description.getInnerFields().get(4);
        List<Description> items =ingredients.getInnerFields();
        assertEquals(2, items.size());

        for(Description itemDescription : items){
            assertEquals(DataType.OBJECT,itemDescription.getDataType());
            assertNotNull(itemDescription.getName());
        }


    }
}
