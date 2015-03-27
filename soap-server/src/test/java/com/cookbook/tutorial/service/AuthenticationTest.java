package com.cookbook.tutorial.service;

import com.cookbook.tutorial.internal.service.CookBookDefaultBackEndImp;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotSame;

/**
 * Created by Mulesoft.
 */
public class AuthenticationTest {

    MuleCookBookServiceImpl server;
    String token;
    @Before
    public void setup() throws InvalidCredentialsException {

        server = new MuleCookBookServiceImpl();
        server.setServiceDAL(new CookBookDefaultBackEndImp());
        token=server.login("admin","admin");
    }

    @Test(expected = InvalidTokenException.class)
    public void invalidLogout() throws SessionExpiredException, InvalidEntityException, InvalidTokenException {
        server.logout("ASDASD123");
    }

    @Test
    public void validLogout() throws SessionExpiredException, InvalidEntityException, InvalidTokenException {
        server.logout(token);
    }
}
