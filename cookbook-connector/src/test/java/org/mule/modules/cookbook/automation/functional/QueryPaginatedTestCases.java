/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cookbook.automation.functional;

import com.cookbook.tutorial.service.SessionExpiredException;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.cookbook.CookbookConnector;
import org.mule.streaming.PagingConfiguration;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class QueryPaginatedTestCases extends AbstractTestCase<CookbookConnector> {

    Map<String, Object> testData;

    public QueryPaginatedTestCases() {
        super(CookbookConnector.class);
    }

    @Before
    public void setup() throws Exception {
        testData = TestDataBuilder.queryPaginatedTestData();
    }

    @Test
    public void testQueryPaginated() {
        try {
            List<Object> results = new ArrayList<Object>();
            Object[] args = new Object[]{(String) testData.get("query"), new PagingConfiguration(Integer.parseInt((String) testData.get("fetchSize")))};
            final Collection<?> paginatedMethod = getDispatcher().runPaginatedMethod("queryPaginated", args);
            final Iterator<?> resultIterator = paginatedMethod.iterator();
            while (resultIterator.hasNext()) {
                results.add(resultIterator.next());
            }
            assertEquals(paginatedMethod.size(), results.size());
        } catch (SessionExpiredException e) {
            fail(e.getMessage());
        } catch (Throwable throwable) {
            fail(throwable.getMessage());
        }
    }
}
