package com.cookbook.tutorial.service;

import java.util.List;

/**
 * Created by Mulesoft.
 */
public interface IDAOCookBookService {

        CookBookEntity create(CookBookEntity entity) throws InvalidEntityException;

        CookBookEntity update(CookBookEntity entity) throws InvalidEntityException, NoSuchEntityException;

        CookBookEntity get(int entityId) throws NoSuchEntityException;

        void delete(int entityId) throws NoSuchEntityException;

        List<CookBookEntity> addList(List<CookBookEntity> entities) throws InvalidEntityException;

        List<CookBookEntity> updateList(List<CookBookEntity> entities) throws InvalidEntityException, NoSuchEntityException;

        List<CookBookEntity> getList(List<Integer> entityIds) throws NoSuchEntityException;

        void deleteList(List<Integer> entityIds) throws NoSuchEntityException;

        List<CookBookEntity> searchWithQuery(String query,Integer page, Integer pageSize)
                throws InvalidRequestException;

        List<Recipe> getRecentlyAdded();

        Description describeEntity(CookBookEntity entity) throws InvalidEntityException, NoSuchEntityException;

        List<CookBookEntity> getEntitiesList();
}
