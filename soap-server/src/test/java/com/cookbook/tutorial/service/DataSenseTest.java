package com.cookbook.tutorial.service;

import com.cookbook.tutorial.internal.service.CookBookDefaultBackEndImp;
import org.junit.Before;
import org.junit.Test;
import sun.security.krb5.internal.crypto.Des;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Mulesoft.
 */
public class DataSenseTest {
    MuleCookBookServiceImpl server;
    Recipe created;
    String token;
    @Before
    public void setup() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException, InvalidTokenException, InvalidCredentialsException {
        server = new MuleCookBookServiceImpl();
        server.setServiceDAL(new CookBookDefaultBackEndImp());
        token=server.login("admin","admin");
        Recipe recipe = new Recipe();
        recipe.setName("FooRecipe");
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient firsItem = new Ingredient();
        firsItem.setId(2);
        firsItem.setName("Orange");
        ingredients.add(firsItem);

        Ingredient secondItem = new Ingredient();
        secondItem.setId(3);
        secondItem.setName("Cream");
        ingredients.add(secondItem);
        recipe.setIngredients(ingredients);

        Create request = new Create();
        request.setEntity(recipe);
        created = (Recipe) server.create(request,token).getReturn();
    }

    @Test
    public void getEntities() throws SessionExpiredException, InvalidTokenException {
        GetEntitiesList request = new GetEntitiesList();
        GetEntitiesListResponse response = server.getEntitiesList(request,token);
        assertEquals(2,response.getReturn().size());
    }
    
    @Test
    public void testDescribeIngredient() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException, InvalidTokenException {

        DescribeEntity request = new DescribeEntity();
        request.setEntity(new Ingredient());
        Description description = server.describeEntity(request,token).getReturn();

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
    public void testDescribeRecipe() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException, InvalidTokenException {
        DescribeEntity request = new DescribeEntity();
        request.setEntity(new Recipe());
        Description description = server.describeEntity(request,token).getReturn();
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
    public void getSpecificIngredientDescription() throws NoSuchEntityException, SessionExpiredException, InvalidEntityException, InvalidTokenException {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(7);
        DescribeEntity request = new DescribeEntity();
        request.setEntity(ingredient);
        Description description=server.describeEntity(request, token).getReturn();
        List<Description> items =description.getInnerFields();
        assertTrue(items.isEmpty());
    }


    @Test
    public void getSpecificRecipeDescription() throws NoSuchEntityException, SessionExpiredException, InvalidEntityException, InvalidTokenException {
        Recipe recipe = new Recipe();
        recipe.setId(created.getId());
        DescribeEntity request = new DescribeEntity();
        request.setEntity(recipe);
        Description description=server.describeEntity(request,token).getReturn();
        Description describedRecipe = description.getInnerFields().get(4);
        List<Description> items =describedRecipe.getInnerFields();
        assertEquals(2, items.size());

        for(Description itemDescription : items){
            assertEquals(DataType.OBJECT,itemDescription.getDataType());
            assertNotNull(itemDescription.getName());
        }


    }
}
