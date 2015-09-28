package org.mule.modules.cookbook.automation.functional;

import com.cookbook.tutorial.service.InvalidEntityException;
import com.cookbook.tutorial.service.NoSuchEntityException;
import com.cookbook.tutorial.service.SessionExpiredException;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.cookbook.CookbookConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DeleteTestCases extends AbstractTestCase<CookbookConnector> {

    Map<String, Object> testData;

    public DeleteTestCases() {
        super(CookbookConnector.class);
    }

    @Before
    public void setup() throws Exception {
        testData = TestDataBuilder.deleteTestData();
        final Map<String, Object> ingredient = (Map<String, Object>) testData.get("ingredient-ref");
        final Map<String, Object> objectMap = getConnector().create((String) testData.get("type"), ingredient);
        testData.put("id", objectMap.get("id"));
    }

    @Test
    public void testDelete() {
        try {
            getConnector().delete((Integer) testData.get("id"));
        } catch (NoSuchEntityException e) {
            fail(e.getMessage());
        } catch (SessionExpiredException e) {
            fail(e.getMessage());
        }
        // Check to confirm the object does not exist.
        try {
            getConnector().get((String) testData.get("type"), (Integer) testData.get("id"));
        } catch (InvalidEntityException e) {
            fail(e.getMessage());
        } catch (NoSuchEntityException e) {
            assertTrue(e instanceof NoSuchEntityException);
        } catch (SessionExpiredException e) {
            fail(e.getMessage());
        }
    }
}
