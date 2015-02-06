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
public class MuleStoreClientTest {

    MuleCookBookClient client;
    IMuleCookBookService server;
    @Before
    public void setup() throws Exception {
        System.out.println("Starting Server");
        MuleCookBookServiceImpl implementor = new MuleCookBookServiceImpl();
        implementor.setServiceDAL(new CookBookDefaultBackEndImp());
        String address = "http://localhost:9090/cook-book";
        Endpoint.publish(address, implementor);
        server = implementor;
        //client = new MuleCookBookClient();
        client = new MuleCookBookClient("http://localhost:9090/cook-book");
    }

    @Test
    public void create() throws InvalidEntityException, SessionExpiredException {
        Ingredient Ingredient = new Ingredient();

        assertNotNull(client.create(Ingredient).getId());
    }


}
