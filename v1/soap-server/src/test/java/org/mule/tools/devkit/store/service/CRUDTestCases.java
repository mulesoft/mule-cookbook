package org.mule.tools.devkit.store.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Created by Mulesoft.
 */
public class CRUDTestCases {
    IMuleStoreService server;
    Book created;
    @Before
    public void setup() throws SessionExpiredException, InvalidEntityException {
        server = new MuleStoreServiceImpl();
        Book book = new Book();
        book.setGenre(Genre.HISTORY);
        book.setDescription("A Book");
        book.setPrice(5);

        created = (Book) server.create(book);
    }

    @Test
    public void testGet() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException {
        Book retrieved = (Book) server.get(created.getId());
        assertSame(created, retrieved);
    }

    @Test(expected = NoSuchEntityException.class)
    public void testGetWithWrongId() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException {
        server.get(5);
    }

    @Test
    public void testUpdate() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException {
        String isbn = "SSSDDDSSSDD";
        created.setIsbn(isbn);
        server.update(created);
        Book retrieved = (Book)server.get(created.getId());
        assertEquals(retrieved.getIsbn(), isbn);
    }

    @Test(expected = NoSuchEntityException.class)
    public void testDelete() throws SessionExpiredException, InvalidEntityException, NoSuchEntityException {
        server.delete(created.getId());
        server.get(created.getId());
    }
}
