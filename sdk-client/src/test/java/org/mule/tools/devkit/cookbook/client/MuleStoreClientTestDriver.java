package org.mule.tools.devkit.cookbook.client;

import org.junit.Before;
import org.junit.Test;
import org.mule.tools.devkit.cookbook.service.*;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Mulesoft.
 */
public class MuleStoreClientTestDriver {

    MuleCookBookClient client;

    @Before
    public void setup() {
        client = new MuleCookBookClient();
        //client = new MuleStoreClient("http://localhost:8081/mule-store");
    }

    @Test
    public void create() throws org.mule.tools.devkit.cookbook.service.InvalidEntityException, org.mule.tools.devkit.cookbook.service.SessionExpiredException {
        Ingredient Ingredient = new Ingredient();

        assertNotNull(client.create(Ingredient).getId());
    }


}
