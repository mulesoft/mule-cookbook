package com.cookbook.tutorial.service;

import com.cookbook.tutorial.internal.service.CookBookDefaultBackEndImp;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
    public void getRecentlyAdded(){
        List<Recipe> recentlyAdded = server.getRecentlyAdded();
        assertFalse(recentlyAdded.isEmpty());
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
        get.setId(85);
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
    public void testUpdateNoneExistingEntity() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException, InvalidTokenException {
        String name = "SSSDDDSSSDD";
        Ingredient ing = new Ingredient();
        ing.setName(name);
        ing.setId(85);
        Update update = new Update();
        update.setEntity(ing);
        server.update(update,token);
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

    @Test(expected = NoSuchEntityException.class)
    public void testDeleteNoneExisting() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException, InvalidTokenException {
        Delete delete = new Delete();
        delete.setId(85);
        server.delete(delete,token);
    }

    @Test
    public void getList() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException, InvalidTokenException {
        GetList ids = new GetList();
        ids.getEntityIds().add(1);
        ids.getEntityIds().add(2);
        GetListResponse response =server.getList(ids, token);
        assertEquals(2, response.getReturn().size());
    }

    @Test
    public void updateList() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException, InvalidTokenException {
        String updatedName = "XXX";
        GetList ids = new GetList();
        ids.getEntityIds().add(1);
        ids.getEntityIds().add(2);
        GetListResponse response =server.getList(ids, token);
        List<CookBookEntity> list=response.getReturn();
        assertEquals(2,list.size());
        list.get(0).setName(updatedName);
        list.get(1).setName(updatedName);
        UpdateList updateRequest = new UpdateList();
        updateRequest.setEntities(list);
        UpdateListResponse result=server.updateList(updateRequest, token);
        assertEquals(updatedName,result.getReturn().get(0).getName());
        assertEquals(updatedName,result.getReturn().get(1).getName());
    }


    @Test
    public void createList() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException, InvalidTokenException {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Foo");
        AddList addList = new AddList();
        List<CookBookEntity> entities = new ArrayList<>();
        entities.add(ingredient);
        ingredient = new Ingredient();
        ingredient.setName("Foo2");
        entities.add(ingredient);

        addList.setEntities(entities);
        AddListResponse result=server.addList(addList, token);
        assertNotNull(result.getReturn().get(0));
        assertNotNull(result.getReturn().get(1));
    }

    @Test
    public void deleteList() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException, InvalidTokenException {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Foo");
        Create createRequest = new Create();

        createRequest.setEntity(ingredient);
        Ingredient created = (Ingredient) server.create(createRequest,token).getReturn();
        List<Integer> ids = new ArrayList<>();
        ids.add(created.getId());

        ingredient = new Ingredient();
        ingredient.setName("Foo");
        createRequest.setEntity(ingredient);
        created = (Ingredient) server.create(createRequest,token).getReturn();
        ids.add(created.getId());

        DeleteList deleteRequest = new DeleteList();
        deleteRequest.setEntityIds(ids);
        server.deleteList(deleteRequest, token);
    }
}
