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
public class MuleStoreClientNegativeTest {

    MuleCookBookClient client;
    IMuleCookBookService server;
    @Before
    public void setup() throws Exception {
        System.out.println("Starting Server");
        MuleCookBookServiceImpl implementor = new MuleCookBookServiceImpl();
        implementor.setServiceDAL(new CookBookDefaultBackEndImp());
        String address = "http://localhost:9092/cook-book";
        Endpoint.publish(address, implementor);
        server = implementor;
        client = new MuleCookBookClient("http://localhost:9092/cook-book");

    }

    @Test(expected = InvalidCredentialsException.class)
    public void create() throws InvalidCredentialsException {
        client.login("asd", "asd");
    }

}
