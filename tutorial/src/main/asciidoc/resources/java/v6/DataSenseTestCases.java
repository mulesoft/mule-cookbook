package org.mule.modules.cookbook.automation.testcases;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.mule.common.Result;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.MetaDataKey;
import org.mule.modules.cookbook.automation.CookBookTestParent;

public class DataSenseTestCases extends CookBookTestParent {

    @Test
    public void getKeys() {
        ConnectorMetaDataEnabled connector = (ConnectorMetaDataEnabled) muleContext.getRegistry().lookupObject("config");
        Result<List<MetaDataKey>> result = connector.getMetaDataKeys();
        assertTrue(Result.Status.SUCCESS.equals(result.getStatus()));
        MetaDataKey key = result.get().get(0);
        assertTrue(Result.Status.SUCCESS.equals(connector.getMetaData(key).getStatus()));
    }
}
