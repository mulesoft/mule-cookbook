package com.cookbook.tutorial.service;

import com.cookbook.tutorial.internal.service.CookBookDefaultBackEndImp;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Mulesoft.
 */
public class DSQLServerTest {

    MuleCookBookServiceImpl server;
    String token;
    @Before
    public void setup() throws InvalidCredentialsException {

        server = new MuleCookBookServiceImpl();
        server.setServiceDAL(new CookBookDefaultBackEndImp());
        token=server.login("admin","admin");
    }

    @Test
    public void queryRecipes() throws SessionExpiredException, InvalidEntityException, InvalidTokenException, InvalidRequestException {
        SearchWithQuery query = new SearchWithQuery();
        query.setQuery("GET ALL FROM INGREDIENT");
        query.setPageSize(5);
        SearchWithQueryResponse result=server.searchWithQuery(query, token);
        assertNotNull(result.getReturn());
    }



    @Test
    public void queryIngredients() throws SessionExpiredException, InvalidEntityException, InvalidTokenException, InvalidRequestException {
        SearchWithQuery query = new SearchWithQuery();
        query.setQuery("GET ALL FROM RECIPE");
        query.setPageSize(5);
        SearchWithQueryResponse result=server.searchWithQuery(query, token);
        assertNotNull(result.getReturn());
    }

    @Test(expected = InvalidRequestException.class)
    public void badQuery() throws SessionExpiredException, InvalidEntityException, InvalidTokenException, InvalidRequestException {
        SearchWithQuery query = new SearchWithQuery();
        query.setQuery("GET ALL FROM FOO");
        query.setPageSize(5);
        SearchWithQueryResponse result=server.searchWithQuery(query, token);
        assertNotNull(result.getReturn());
    }
}
