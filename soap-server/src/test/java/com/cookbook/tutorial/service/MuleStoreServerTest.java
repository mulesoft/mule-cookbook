package com.cookbook.tutorial.service;

import org.junit.Before;
import org.junit.Test;
import com.cookbook.tutorial.internal.service.CookBookDefaultBackEndImp;

import static org.junit.Assert.assertNotSame;

/**
 * Created by Mulesoft.
 */
public class MuleStoreServerTest {

    MuleCookBookServiceImpl server;
    @Before
    public void setup(){
        server = new MuleCookBookServiceImpl();
        server.setServiceDAL(new CookBookDefaultBackEndImp());

    }

    @Test
    public void testCreate() throws SessionExpiredException, InvalidEntityException {
        CookBookEntity ingredient = new Ingredient();
        CookBookEntity created= server.create(ingredient);
        assertNotSame(created.getId(), 0);
    }

    @Test(expected = InvalidEntityException.class)
    public void testCreateInvalidWithId() throws SessionExpiredException, InvalidEntityException {
        CookBookEntity book = new Ingredient();
        book.setId(1);
        server.create(book);
    }

}
