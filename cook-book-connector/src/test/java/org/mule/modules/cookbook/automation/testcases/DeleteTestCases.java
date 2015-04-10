
package org.mule.modules.cookbook.automation.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.cookbook.automation.CookBookTestParent;
import org.mule.modules.cookbook.automation.RegressionTests;
import org.mule.modules.cookbook.automation.SmokeTests;

public class DeleteTestCases
    extends CookBookTestParent
{


    @Before
    public void setup()
        throws Exception
    {
        //TODO: Add setup required to run test or remove method
        initializeTestRunMessage("deleteTestData");
    }

    @After
    public void tearDown()
        throws Exception
    {
        //TODO: Add code to reset sandbox state to the one before the test was run or remove
    }

    @Category({
        RegressionTests.class,
        SmokeTests.class
    })
    @Test
    public void testDelete()
        throws Exception
    {
        Object result = runFlowAndGetPayload("delete");
        throw new RuntimeException("NOT IMPLEMENTED METHOD");
    }

}
