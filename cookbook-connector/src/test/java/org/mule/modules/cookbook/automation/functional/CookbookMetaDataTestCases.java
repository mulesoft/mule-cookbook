/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cookbook.automation.functional;

import org.junit.Test;
import org.mule.common.Result;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.modules.cookbook.CookbookConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;
import org.mule.tools.devkit.ctf.junit.MetaDataTest;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CookbookMetaDataTestCases extends AbstractTestCase<CookbookConnector> {

    public CookbookMetaDataTestCases() {
        super(CookbookConnector.class);
    }

    @Test
    @MetaDataTest
    public void testMetaDataKeys() throws Exception {
        Result<List<MetaDataKey>> metaDataKeysResult = getDispatcher().fetchMetaDataKeys();
        assertTrue(Result.Status.SUCCESS.equals(metaDataKeysResult.getStatus()));

        List<MetaDataKey> metaDataKeys = metaDataKeysResult.get();

        MetaDataKey recipeKey = null;
        MetaDataKey ingredientKey = null;

        for (MetaDataKey key : metaDataKeys) {
            if (key.getDisplayName().equals("Recipe"))
                recipeKey = key;
            if (key.getDisplayName().equals("Ingredient"))
                ingredientKey = key;
        }

        //Assert on MetaDataKeys
        assertNotNull(recipeKey);
        assertTrue(recipeKey.getCategory().equals("DataSenseResolver"));
        assertNotNull(ingredientKey);
        assertTrue(ingredientKey.getCategory().equals("DataSenseResolver"));
    }

    @Test
    @MetaDataTest
    public void testMetaData() throws Exception {

        Result<List<MetaDataKey>> metaDataKeysResult = getDispatcher().fetchMetaDataKeys();
        assertTrue(Result.Status.SUCCESS.equals(metaDataKeysResult.getStatus()));

        List<MetaDataKey> metaDataKeys = metaDataKeysResult.get();

        MetaDataKey recipeKey = null;
        MetaDataKey ingredientKey = null;

        for (MetaDataKey key : metaDataKeys) {
            if (key.getDisplayName().equals("Recipe"))
                recipeKey = key;
            if (key.getDisplayName().equals("Ingredient"))
                ingredientKey = key;
        }

        //Asserts on MetaData
        Result<MetaData> recipeKeyResult = getDispatcher().fetchMetaData(recipeKey);
        assertTrue(Result.Status.SUCCESS.equals(recipeKeyResult.getStatus()));

        Result<MetaData> ingredientKeyResult = getDispatcher().fetchMetaData(ingredientKey);
        assertTrue(Result.Status.SUCCESS.equals(ingredientKeyResult.getStatus()));
    }
}

