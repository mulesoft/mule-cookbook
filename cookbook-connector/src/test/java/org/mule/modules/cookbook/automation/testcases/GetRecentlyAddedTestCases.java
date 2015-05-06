package org.mule.modules.cookbook.automation.testcases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.cookbook.automation.CookbookTestParent;
import org.mule.modules.cookbook.automation.RegressionTests;
import org.mule.modules.cookbook.automation.SmokeTests;

public class GetRecentlyAddedTestCases extends CookbookTestParent {

    @Before
    public void setup() throws Exception {
        // TODO: Add setup required to run test or remove method
        initializeTestRunMessage("getRecentlyAddedTestData");
    }

    @After
    public void tearDown() throws Exception {
        // TODO: Add code to reset sandbox state to the one before the test was run or remove
    }

    @Category({ RegressionTests.class, SmokeTests.class })
    @Test
    public void testGetRecentlyAdded() throws Exception {
        Object result = runFlowAndGetPayload("get-recently-added");
        assertNotNull(result);
    }

}
