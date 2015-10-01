/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cookbook.automation.unit;

import com.cookbook.tutorial.client.MuleCookBookClient;
import com.cookbook.tutorial.service.CookBookEntity;
import com.cookbook.tutorial.service.Ingredient;
import com.cookbook.tutorial.service.Recipe;
import com.cookbook.tutorial.service.UnitType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mule.modules.cookbook.CookbookConnector;
import org.mule.modules.cookbook.config.Config;
import org.mule.streaming.PagingConfiguration;
import org.mule.streaming.ProviderAwarePagingDelegate;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class CookbookConnectorTest {

    private CookbookConnector connector;

    private String randomString;

    @Mock
    private MuleCookBookClient client;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.connector = new CookbookConnector();
        this.randomString = String.format("qaTest%s", new Object[]{UUID.randomUUID().toString().substring(0, 7)});

        Config config = new Config();
        config.setClient(client);
        this.connector.setConfig(config);
    }

    @Test
    public void testCreate() {
        try {
            when(client.create(any(CookBookEntity.class))).thenReturn(getIngredient());
            Map<String, Object> ingredient = connector.create("com.cookbook.tutorial.service.Ingredient", getIngredientMap());
            assertEquals(ingredient.get("name"), getIngredient().getName());
            assertEquals(ingredient.get("unit"), getIngredient().getUnit().name());

            when(client.create(any(CookBookEntity.class))).thenReturn(getRecipe());
            Map<String, Object> recipe = connector.create("com.cookbook.tutorial.service.Recipe", getRecipeMap());
            assertEquals(recipe.get("name"), getIngredient().getName());
            assertEquals(recipe.get("ingredients"), Arrays.asList(ingredient));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDelete() {
        try {
            doNothing().when(client).delete(anyInt());
            connector.delete(99);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGet() {
        try {
            when(client.get(anyInt())).thenReturn(getIngredient());
            Map<String, Object> ingredient = connector.get("com.cookbook.tutorial.service.Ingredient", 99);
            assertEquals(ingredient.get("name"), getIngredient().getName());
            assertEquals(ingredient.get("unit"), getIngredient().getUnit().name());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetRecentlyAdded() {
        try {
            when(client.getRecentlyAdded()).thenReturn(Arrays.asList(getRecipe()));
            final List<Recipe> recentlyAdded = connector.getRecentlyAdded();
            assertTrue(recentlyAdded.size() == 1);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdate() {
        try {
            when(client.update(any(CookBookEntity.class))).thenReturn(getIngredient());
            Map<String, Object> ingredient = connector.update("com.cookbook.tutorial.service.Ingredient", getIngredientMap());
            assertEquals(ingredient.get("name"), getIngredient().getName());
            assertEquals(ingredient.get("unit"), getIngredient().getUnit().name());

            when(client.update(any(CookBookEntity.class))).thenReturn(getRecipe());
            Map<String, Object> recipe = connector.update("com.cookbook.tutorial.service.Recipe", getRecipeMap());
            assertEquals(recipe.get("name"), getIngredient().getName());
            assertEquals(recipe.get("ingredients"), Arrays.asList(ingredient));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testQueryPaginated() {
        try {
            final List<CookBookEntity> entities = Arrays.asList(new CookBookEntity[10]);
            when(client.searchWithQuery(anyString(), anyInt(), anyInt())).thenReturn(entities);
            final ProviderAwarePagingDelegate<Map<String, Object>, CookbookConnector> pagingDelegate = connector.queryPaginated("GET ALL FROM INGREDIENT", new PagingConfiguration(10));
            assertEquals(pagingDelegate.getPage(connector).size(), entities.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private Ingredient getIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(99);
        ingredient.setName(randomString);
        ingredient.setQuantity(10);
        ingredient.setUnit(UnitType.SPOONS);
        return ingredient;
    }

    private Recipe getRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName(randomString);
        recipe.setCookTime(10.0);
        recipe.setPrepTime(10.0);
        recipe.setIngredients(Arrays.asList(getIngredient()));
        return recipe;
    }

    private Map<String, Object> getIngredientMap() {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("name", randomString);
        objectMap.put("quantity", 10);
        objectMap.put("unit", UnitType.SPOONS);
        return objectMap;
    }

    private Map<String, Object> getRecipeMap() {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("name", randomString);
        objectMap.put("cookTime", 10.0);
        objectMap.put("prepTime", 10.0);
        objectMap.put("ingredients", Arrays.asList(getIngredientMap()));
        return objectMap;
    }
}
