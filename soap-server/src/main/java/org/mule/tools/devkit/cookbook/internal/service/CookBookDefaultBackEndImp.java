package org.mule.tools.devkit.cookbook.internal.service;

import org.mule.tools.devkit.cookbook.service.*;
import org.springframework.util.CollectionUtils;

import javax.jws.WebParam;
import java.util.*;

/**
 * Created by Mulesoft.
 *
 * This is a dummy implementation, none persistent. Will be valid until the server stops running.
 *
 */
public class CookBookDefaultBackEndImp implements IMuleCookBookService{

    private Integer currentIndex = 0;

    private Map<Integer, CookBookEntity> entities = new HashMap<>();

    @Override public List<CookBookEntity> getList(@WebParam(name = "entityIds", targetNamespace = "") List<Integer> entityIds)
            throws NoSuchEntityException, SessionExpiredException {
        List<CookBookEntity> returnValue = new ArrayList<>();
        for (Integer id : entityIds) {
            returnValue.add(get(id));
        }
        return returnValue;
    }

    @Override public void delete(@WebParam(name = "id", targetNamespace = "") int id) throws NoSuchEntityException, SessionExpiredException {
        if (!entities.containsKey(id)) {
            throw new NoSuchEntityException();
        }
        entities.remove(id);
    }

    @Override public CookBookEntity create(@WebParam(name = "entity", targetNamespace = "") CookBookEntity entity) throws SessionExpiredException, InvalidEntityException {
        if (entity.getId() != null) {
            InvalidEntity invalid = new InvalidEntity();
            throw new InvalidEntityException("Cannot specify Id at creation.", invalid);
        }
        if (entity instanceof Ingredient) {
            Ingredient ingredient = (Ingredient) entity;
        }
        if (entity instanceof Recipe) {
            Recipe recipe = (Recipe) entity;

        }
        entity.setId(++currentIndex);
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override public CookBookEntity update(@WebParam(name = "entity", targetNamespace = "") CookBookEntity entity)
            throws NoSuchEntityException, SessionExpiredException, InvalidEntityException {
        if (!entities.containsKey(entity.getId())) {
            throw new NoSuchEntityException();
        }
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override public CookBookEntity get(@WebParam(name = "id", targetNamespace = "") int id) throws NoSuchEntityException, SessionExpiredException {

        if (!entities.containsKey(id)) {

            throw new NoSuchEntityException();
        }
        return entities.get(id);
    }

    @Override public List<CookBookEntity> updateList(@WebParam(name = "entities", targetNamespace = "") List<CookBookEntity> entities)
            throws NoSuchEntityException, SessionExpiredException, InvalidEntityException {
        for (CookBookEntity entity : entities) {
            update(entity);
        }
        return entities;
    }

    @Override public Recipe updateQuantities(@WebParam(name = "arg0", targetNamespace = "") Recipe arg0)
            throws NoSuchEntityException, SessionExpiredException, InvalidEntityException {
        return null;
    }

    @Override public void deleteList(@WebParam(name = "entityIds", targetNamespace = "") List<Integer> entityIds) throws NoSuchEntityException, SessionExpiredException {
        for (Integer id : entityIds) {
            delete(id);
        }
    }

    @Override public List<CookBookEntity> addList(@WebParam(name = "entities", targetNamespace = "") List<CookBookEntity> entities)
            throws SessionExpiredException, InvalidEntityException {
        for (CookBookEntity entity : entities) {
            create(entity);
        }
        return entities;
    }

    @Override public List<CookBookEntity> searchWithQuery(@WebParam(name = "query", targetNamespace = "") String query, @WebParam(name = "page", targetNamespace = "") Integer page,
            @WebParam(name = "pageSize", targetNamespace = "") Integer pageSize) throws NoSuchEntityException, SessionExpiredException {
        try {
            return CollectionUtils.arrayToList(entities.values().toArray());
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override public List<Recipe> getRecentlyAdded() {
        Collection<CookBookEntity> values = entities.values();
        return CollectionUtils.arrayToList(values.toArray());
    }
}
