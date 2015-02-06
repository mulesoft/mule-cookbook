package com.cookbook.tutorial.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Created by Mulesoft.
 */
public class CRUDTestCases {
    IMuleCookBookService server;
    Ingredient created;
    @Before
    public void setup() throws SessionExpiredException, InvalidEntityException {
        server = new MuleCookBookServiceImpl();
        Ingredient book = new Ingredient();
        book.setName("Foo");

        created = (Ingredient) server.create(book);
    }

    @Test
    public void testGet() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException {
        Ingredient retrieved = (Ingredient) server.get(created.getId());
        assertSame(created, retrieved);
    }

    @Test(expected = NoSuchEntityException.class)
    public void testGetWithWrongId() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException {
        server.get(5);
    }

    @Test
    public void testUpdate() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException {
        String name = "SSSDDDSSSDD";
        created.setName(name);
        server.update(created);
        Ingredient retrieved = (Ingredient)server.get(created.getId());
        assertEquals(retrieved.getName(), name);
    }

    @Test(expected = NoSuchEntityException.class)
    public void testDelete() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException {
        server.delete(created.getId());
        server.get(created.getId());
    }
}
