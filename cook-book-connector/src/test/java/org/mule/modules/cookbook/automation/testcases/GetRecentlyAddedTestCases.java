package org.mule.modules.cookbook.automation.testcases;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.cookbook.automation.CookBookTestParent;
import org.mule.modules.cookbook.automation.RegressionTests;
import org.mule.modules.cookbook.automation.SmokeTests;

import com.cookbook.tutorial.service.Recipe;

public class GetRecentlyAddedTestCases extends CookBookTestParent {

	@Before
	public void setup() throws Exception {
		initializeTestRunMessage("getRecentlyAddedTestData");
	}

	@After
	public void tearDown() throws Exception {

	}

	@Category({ RegressionTests.class, SmokeTests.class })
	@Test
	@SuppressWarnings("unchecked")
	public void testGetRecentlyAdded() throws Exception {
		Object result = runFlowAndGetPayload("get-recently-added");
		assertNotNull(result);

		List<Recipe> recipes = (List<Recipe>) result;
		assertFalse(recipes.isEmpty());
	}

}
