package com.cookbook.tutorial.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.cookbook.tutorial.internal.service.CookBookDefaultBackEndImp;

import static org.junit.Assert.assertNotSame;

/**
 * Created by Mulesoft.
 */
public class MuleStoreServerTest {

    MuleCookBookServiceImpl server;
    String token;
    @Before
    public void setup() throws InvalidCredentialsException {

        server = new MuleCookBookServiceImpl();
        server.setServiceDAL(new CookBookDefaultBackEndImp());
        token=server.login("admin","admin");
    }

    @Test
    public void testCreate() throws SessionExpiredException, InvalidEntityException, InvalidTokenException {
        CookBookEntity ingredient = new Ingredient();
        Create create = new Create();
        create.setEntity(ingredient);
        CookBookEntity created= server.create(create,token).getReturn();
        assertNotSame(created.getId(), 0);
    }

    @Test(expected = InvalidEntityException.class)
    public void testCreateInvalidWithId() throws SessionExpiredException, InvalidEntityException, InvalidTokenException {
        CookBookEntity ingredient = new Ingredient();
        ingredient.setId(1);
        Create create=new Create();
        create.setEntity(ingredient);
        server.create(create,token);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void testInvalidCredentials() throws InvalidCredentialsException {
        server.login("foo", "foo");
    }
}
