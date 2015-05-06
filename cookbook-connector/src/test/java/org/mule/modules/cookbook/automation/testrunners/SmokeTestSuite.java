
package org.mule.modules.cookbook.automation.testrunners;

import org.junit.runner.RunWith;
import org.mule.modules.cookbook.automation.SmokeTests;

@RunWith(org.junit.experimental.categories.Categories.class)
@org.junit.experimental.categories.Categories.IncludeCategory(SmokeTests.class)
@org.junit.runners.Suite.SuiteClasses({

})
public class SmokeTestSuite {


}
