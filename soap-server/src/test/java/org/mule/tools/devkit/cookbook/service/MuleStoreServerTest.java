package org.mule.tools.devkit.cookbook.service;

import static org.junit.Assert.assertNotSame;

/**
 * Created by Mulesoft.
 */
public class MuleStoreServerTest {

    IMuleCookBookService server;
/*    @Before
    public void setup(){
        server = new MuleCookBookServiceImpl();
    }

    @Test
    public void testCreate() throws SessionExpiredException, InvalidEntityException {
        Entity book = new Book();
        Book created= (Book) server.create(book);
        assertNotSame(created.getId(), 0);
    }

    @Test(expected = InvalidEntityException.class)
    public void testCreateInvalidWithId() throws SessionExpiredException, InvalidEntityException {
        Book book = new Book();
        book.setId(1);
        server.create(book);
    }

    @Test(expected = InvalidEntityException.class)
    public void testCreateInvalidAuthorNoName() throws SessionExpiredException, InvalidEntityException {
        Author author= new Author();
        server.create(author);
    }

    @Test(expected = SessionExpiredException.class)
    public void testSessionExpired() throws SessionExpiredException, InvalidEntityException {
        server.getRecentlyAdded();
        server.getRecentlyAdded();
        server.getRecentlyAdded();
    }*/
}
