package org.mule.modules.cookbook.automation.functional;

import com.cookbook.tutorial.service.Recipe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.cookbook.CookbookConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetRecentlyAddedTestCases extends AbstractTestCase<CookbookConnector> {

    Map<String, Object> testData;

    public GetRecentlyAddedTestCases() {
        super(CookbookConnector.class);
    }

    @Before
    public void setup() throws Exception {
        testData = TestDataBuilder.getRecentlyAddedTestData();
    }

    @Test
    public void testGetRecentlyAdded() {
        try {
            List<Recipe> recipes = getConnector().getRecentlyAdded();
            if (recipes.isEmpty()) {
                final Map<String, Object> objectMap = getConnector().create((String) testData.get("type"), (Map<String, Object>) testData.get("recipe-ref"));
                testData.put("id", objectMap.get("id"));
                recipes = getConnector().getRecentlyAdded();
            }
            assertTrue(recipes.size() > 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
        if (testData.containsKey("id")) {
            getConnector().delete((Integer) testData.get("id"));
        }
    }
}
