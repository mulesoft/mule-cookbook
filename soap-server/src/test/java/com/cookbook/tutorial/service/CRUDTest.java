package com.cookbook.tutorial.service;

import com.cookbook.tutorial.internal.service.CookBookDefaultBackEndImp;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Created by Mulesoft.
 */
public class CRUDTest {
    MuleCookBookServiceImpl server;
    Ingredient created;
    String token;
    @Before
    public void setup() throws SessionExpiredException, InvalidEntityException, InvalidTokenException, InvalidCredentialsException {
        server = new MuleCookBookServiceImpl();
        server.setServiceDAL(new CookBookDefaultBackEndImp());
        token = server.login("admin","admin");
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Foo");
        Create createRequest = new Create();
        createRequest.setEntity(ingredient);
        created = (Ingredient) server.create(createRequest,token).getReturn();
    }

    @Test
    public void testGet() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException, InvalidTokenException {
        Get get = new Get();
        get.setId(created.getId());
        Ingredient retrieved = (Ingredient) server.get(get,token).getReturn();
        assertSame(created, retrieved);
    }

    @Test(expected = NoSuchEntityException.class)
    public void testGetWithWrongId() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException, InvalidTokenException {
        Get get=new Get();
        get.setId(5);
        server.get(get,token);
    }

    @Test
    public void testUpdate() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException, InvalidTokenException {
        String name = "SSSDDDSSSDD";
        created.setName(name);
        Update update = new Update();
        update.setEntity(created);
        server.update(update,token);
        Get get = new Get();
        get.setId(created.getId());
        Ingredient retrieved = (Ingredient)server.get(get,token).getReturn();
        assertEquals(retrieved.getName(), name);
    }

    @Test(expected = NoSuchEntityException.class)
    public void testDelete() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException, InvalidTokenException {
        Delete delete = new Delete();
        delete.setId(created.getId());
        server.delete(delete,token);
        Get get = new Get();
        get.setId(created.getId());
        server.get(get,token);
    }
}
