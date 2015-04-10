package org.mule.modules.cookbook.automation.testcases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.common.Result;
import org.mule.common.TestResult;
import org.mule.common.Testable;
import org.mule.modules.cookbook.automation.CookBookTestParent;
import org.mule.modules.cookbook.automation.RegressionTests;
import org.mule.modules.cookbook.automation.SmokeTests;

public class ConnectivityTestCases extends CookBookTestParent {

	@Before
	public void setup() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {

	}

	@Category({ RegressionTests.class, SmokeTests.class })

    @Test
    public void testConnectivity(){
        Testable connector = muleContext.getRegistry().lookupObject("config");
        TestResult testResult = connector.test();
        assertEquals(testResult.getStatus(), Result.Status.SUCCESS);
    }
	
    @Test
    public void testConnectivityFailure(){
        Testable connector = muleContext.getRegistry().lookupObject("config-that-fails");
        TestResult testResult = connector.test();
        assertEquals(testResult.getStatus(), Result.Status.FAILURE);
        assertEquals(testResult.getMessage(), "Invalid credentials");
    }
}
