package org.mule.tools.devkit.cookbook.service;

import org.springframework.util.CollectionUtils;

import javax.jws.WebParam;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Mulesoft.
 */
@javax.jws.WebService(
        serviceName = "IMuleCookBookServiceService",
        portName = "IMuleCookBookServicePort",
        targetNamespace = "http://service.cookbook.devkit.tools.mule.org/",
        wsdlLocation = "wsdl/IMuleCookBookService.wsdl",
        endpointInterface = "IMuleCookBookService")
public class MuleCookBookServiceImpl implements IMuleCookBookService {

    private static final Logger LOG = Logger.getLogger(MuleCookBookServiceImpl.class.getName());

    Integer currentIndex = 0;
    Integer exceptionRatio = 3;
    Integer callsCount = 0;
    private Map<Integer, CookBookEntity> entities = new HashMap<>();

    @Override public List<Recipe> getRecentlyAdded() {
        LOG.info("Executing operation getRecentlyAdded");
        Collection<CookBookEntity> values = entities.values();
        return CollectionUtils.arrayToList(values.toArray());
    }

    @Override public CookBookEntity get(@WebParam(name = "id", targetNamespace = "") int id) throws NoSuchEntityException, SessionExpiredException {
        checkExpiredSessionCondition();

        if (!entities.containsKey(id)) {

            throw new NoSuchEntityException();
        }
        return entities.get(id);
    }

    @Override public List<CookBookEntity> searchWithQuery(@WebParam(name = "query", targetNamespace = "") String query, @WebParam(name = "page", targetNamespace = "") Integer page,
            @WebParam(name = "pageSize", targetNamespace = "") Integer pageSize) throws NoSuchEntityException, SessionExpiredException {

        LOG.info("Executing operation searchWithQuery");
        checkExpiredSessionCondition();
        try {
            return CollectionUtils.arrayToList(entities.values().toArray());
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override public CookBookEntity update(@WebParam(name = "entity", targetNamespace = "") CookBookEntity entity) throws InvalidEntityException, NoSuchEntityException,
            SessionExpiredException {
        checkExpiredSessionCondition();
        if (!entities.containsKey(entity.getId())) {
            throw new NoSuchEntityException();
        }
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override public List<CookBookEntity> addList(@WebParam(name = "entities", targetNamespace = "") List<CookBookEntity> entities) throws InvalidEntityException,
            SessionExpiredException {
        checkExpiredSessionCondition();
        for (CookBookEntity entity : entities) {
            create(entity);
        }
        return entities;
    }

    @Override public List<CookBookEntity> getList(@WebParam(name = "entityIds", targetNamespace = "") List<Integer> entityIds) throws NoSuchEntityException,
            SessionExpiredException {
        checkExpiredSessionCondition();
        List<CookBookEntity> returnValue = new ArrayList<>();
        for (Integer id : entityIds) {
            returnValue.add(get(id));
        }
        return returnValue;
    }

    @Override public void delete(@WebParam(name = "id", targetNamespace = "") int id) throws NoSuchEntityException, SessionExpiredException {
        checkExpiredSessionCondition();
        if (!entities.containsKey(id)) {
            throw new NoSuchEntityException();
        }
        entities.remove(id);
    }

    @Override public List<CookBookEntity> updateList(@WebParam(name = "entities", targetNamespace = "") List<CookBookEntity> entities)
            throws InvalidEntityException, NoSuchEntityException, SessionExpiredException {
        checkExpiredSessionCondition();
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
        checkExpiredSessionCondition();
        for (Integer id : entityIds) {
            delete(id);
        }
    }

    @Override public CookBookEntity create(@WebParam(name = "entity", targetNamespace = "") CookBookEntity entity) throws InvalidEntityException, SessionExpiredException {
        checkExpiredSessionCondition();
        if (entity.getId() != null) {
            InvalidEntity invalid = new InvalidEntity();
            throw new InvalidEntityException("Cannot specify Id at creation.", invalid);
        }
        if (entity instanceof Ingredient) {
            Ingredient book = (Ingredient) entity;
        }
        if (entity instanceof Recipe) {
            Recipe author = (Recipe) entity;

        }
        entity.setId(++currentIndex);
        entities.put(entity.getId(), entity);
        return entity;
    }

    private void checkExpiredSessionCondition() throws SessionExpiredException {
        if (((currentIndex+1) % exceptionRatio) == 0) {
            throw new SessionExpiredException();
        }
    }
}
