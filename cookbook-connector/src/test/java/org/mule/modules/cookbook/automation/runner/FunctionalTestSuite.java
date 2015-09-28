package org.mule.modules.cookbook.automation.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mule.modules.cookbook.CookbookConnector;
import org.mule.modules.cookbook.automation.functional.*;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateTestCases.class,
        DeleteTestCases.class,
        GetRecentlyAddedTestCases.class,
        GetTestCases.class,
        QueryPaginatedTestCases.class,
        UpdateTestCases.class,
        CookbookMetaDataTestCases.class
})
public class FunctionalTestSuite {

    @BeforeClass
    public static void initialiseSuite() {
        ConnectorTestContext.initialize(CookbookConnector.class);
    }

    @AfterClass
    public static void shutdownSuite() {
        ConnectorTestContext.shutDown();
    }
}
