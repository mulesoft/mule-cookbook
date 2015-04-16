package org.mule.modules.cookbook;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.ConnectionException;
import org.mule.modules.cookbook.strategy.ConnectionManagementStrategy;
import org.mule.modules.cookbook.strategy.ConnectorConnectionStrategy;

import com.cookbook.tutorial.service.InvalidEntityException;
import com.cookbook.tutorial.service.Recipe;
import com.cookbook.tutorial.service.SessionExpiredException;

public class CookbookTestDriver {

	CookBookConnector connector;
	@Before
	public void init() throws ConnectionException{
		connector = new CookBookConnector();
        ConnectionManagementStrategy connectionStrategy = new ConnectionManagementStrategy();
		connectionStrategy.setEndpoint("http://localhost:8081/soap");
		connector.setConnectionStrategy(connectionStrategy );
		connectionStrategy.connect("admin", "admin");
	}
	
	@Test
	public void createFromMap() throws InvalidEntityException, SessionExpiredException{
		Map<String,Object> recipe = new HashMap<>();
		recipe.put("name", "Apple Pie");
		List<String> directions = new ArrayList<>();
		directions.add("Step1");
		directions.add("Step2");
		directions.add("Step3");
		recipe.put("directions", directions);
		recipe.put("cookTime", 10.0);
		recipe.put("prepTime", 15.0);
		List<Map<String,Object>> ingredients = new ArrayList<>();
		Map<String,Object> ingredient = new HashMap<>();
		ingredient.put("name", "Green Apple");
		ingredients.add(ingredient);
		recipe.put("ingredients", ingredients);
		Map<String,Object> result=connector.create("com.cookbook.tutorial.service.Recipe#0", recipe);
		
		assertNotNull(result);
	}
}
