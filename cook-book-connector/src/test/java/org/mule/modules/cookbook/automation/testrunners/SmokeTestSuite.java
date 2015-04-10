
package org.mule.modules.cookbook.automation.testrunners;

import org.junit.runner.RunWith;
import org.mule.modules.cookbook.automation.SmokeTests;
import org.mule.modules.cookbook.automation.testcases.CreateTestCases;
import org.mule.modules.cookbook.automation.testcases.DeleteTestCases;
import org.mule.modules.cookbook.automation.testcases.GetRecentlyAddedTestCases;
import org.mule.modules.cookbook.automation.testcases.GetTestCases;
import org.mule.modules.cookbook.automation.testcases.UpdateTestCases;

@RunWith(org.junit.experimental.categories.Categories.class)
@org.junit.experimental.categories.Categories.IncludeCategory(SmokeTests.class)
@org.junit.runners.Suite.SuiteClasses({
    GetRecentlyAddedTestCases.class,
    CreateTestCases.class,
    UpdateTestCases.class,
    GetTestCases.class,
    DeleteTestCases.class
})
public class SmokeTestSuite {


}
