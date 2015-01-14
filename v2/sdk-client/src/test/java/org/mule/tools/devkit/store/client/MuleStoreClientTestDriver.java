package org.mule.tools.devkit.store.client;

import org.junit.Before;
import org.junit.Test;
import org.mule.tools.devkit.store.service.*;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Mulesoft.
 */
public class MuleStoreClientTestDriver {

    MuleStoreClient client;
    @Before
    public void setup(){
        client = new MuleStoreClient();
        //client = new MuleStoreClient("http://localhost:8081/mule-store");
    }

    @Test
    public void add() throws InvalidEntityException, SessionExpiredException {
        Author author = new Author();
        author.setFirstName("Pedro");
        author.setLastName("Foo");
        assertNotNull(client.add(author).getId());
    }

    @Test
    public void getRecentlyAdded() throws InvalidEntityException, NoSuchEntityException, SessionExpiredException {
        assertNotNull(client.getRecentlyAdded());
    }

    @Test
    public void get() throws InvalidEntityException, NoSuchEntityException, SessionExpiredException {
        client.get(1);
    }

    @Test(expected = NoSuchEntityException.class)
    public void getNoneExisting() throws InvalidEntityException, NoSuchEntityException, SessionExpiredException {
        client.get(100);
    }
}
