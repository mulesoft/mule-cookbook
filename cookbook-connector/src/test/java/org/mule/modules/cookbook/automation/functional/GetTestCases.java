/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cookbook.automation.functional;

import com.cookbook.tutorial.service.InvalidEntityException;
import com.cookbook.tutorial.service.NoSuchEntityException;
import com.cookbook.tutorial.service.SessionExpiredException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.cookbook.CookbookConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GetTestCases extends AbstractTestCase<CookbookConnector> {

    Map<String, Object> testData;
    Map<String, Object> ingredient;

    public GetTestCases() {
        super(CookbookConnector.class);
    }

    @Before
    public void setup() throws Exception {
        testData = TestDataBuilder.getTestData();
        ingredient = (Map<String, Object>) testData.get("ingredient-ref");
        final Map<String, Object> objectMap = getConnector().create((String) testData.get("type"), ingredient);
        testData.put("id", objectMap.get("id"));
    }

    @Test
    public void testGet() {
        try {
            final Map<String, Object> objectMap = getConnector().get((String) testData.get("type"), (Integer) testData.get("id"));
            assertEquals(ingredient.get("name"), objectMap.get("name"));
            assertEquals(Double.valueOf((String) ingredient.get("quantity")), objectMap.get("quantity"));
        } catch (NoSuchEntityException e) {
            fail(e.getMessage());
        } catch (SessionExpiredException e) {
            fail(e.getMessage());
        } catch (InvalidEntityException e) {
            fail(e.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().delete((Integer) testData.get("id"));
    }
}
