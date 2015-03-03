package com.cookbook.tutorial.client;

import com.cookbook.tutorial.service.*;

import java.util.List;

/**
 * Created by Mulesoft.
 */
public interface IMuleCookBookClient {

    public void login(String username,String password) throws InvalidCredentialsException;

    public List<Recipe> getRecentlyAdded();

    public CookBookEntity get(int id) throws NoSuchEntityException, SessionExpiredException, InvalidTokenException;

    public List<CookBookEntity> searchWithQuery(String query, Integer page, Integer pageSize) throws NoSuchEntityException,
            SessionExpiredException, InvalidTokenException;

    public CookBookEntity update(CookBookEntity entity) throws NoSuchEntityException,
            InvalidEntityException, SessionExpiredException, InvalidTokenException;

    public List<CookBookEntity> addList(List<CookBookEntity> entities) throws InvalidEntityException,
            SessionExpiredException, InvalidTokenException;

    public List<CookBookEntity> getList(List<Integer> entityIds) throws NoSuchEntityException,
            SessionExpiredException, InvalidTokenException;

    public void delete(int id) throws NoSuchEntityException, SessionExpiredException, InvalidTokenException;

    public List<CookBookEntity> updateList(List<CookBookEntity> entities) throws NoSuchEntityException,
            InvalidEntityException, SessionExpiredException, InvalidTokenException;

    public void deleteList(List<Integer> entityIds) throws NoSuchEntityException,
            SessionExpiredException, InvalidTokenException;

    public CookBookEntity create(CookBookEntity entity) throws InvalidEntityException,
            SessionExpiredException, InvalidTokenException;
}
