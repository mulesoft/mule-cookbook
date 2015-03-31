package com.cookbook.tutorial.client;

import com.cookbook.tutorial.internal.service.CookBookDefaultBackEndImp;
import com.cookbook.tutorial.service.*;
import org.junit.Before;
import org.junit.Test;

import javax.xml.ws.Endpoint;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Mulesoft.
 */
public class MuleStoreClientAgainstMuleAppTestDriver {

    MuleCookBookClient client;
    @Before
    public void setup() throws Exception {
        client = new MuleCookBookClient("http://localhost:8081/soap");
        client.login("admin","admin");
    }

    @Test
    public void create() throws InvalidEntityException, SessionExpiredException, InvalidTokenException {
        Ingredient Ingredient = new Ingredient();
        assertNotNull(client.create(Ingredient).getId());
    }

}
