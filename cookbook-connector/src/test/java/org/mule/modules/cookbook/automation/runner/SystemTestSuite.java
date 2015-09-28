package org.mule.modules.cookbook.automation.runner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mule.modules.cookbook.automation.system.BasicConfigSystemTestCases;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BasicConfigSystemTestCases.class
})
public class SystemTestSuite {

}