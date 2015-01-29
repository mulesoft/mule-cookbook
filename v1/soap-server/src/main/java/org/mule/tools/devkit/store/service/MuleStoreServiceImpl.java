package org.mule.tools.devkit.store.service;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.jws.WebParam;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Mulesoft.
 */
@javax.jws.WebService(
        serviceName = "IMuleStoreServiceService",
        portName = "IMuleStoreServicePort",
        targetNamespace = "http://service.store.devkit.tools.mule.org/",
        wsdlLocation = "wsdl/IMuleStoreService.wsdl",
        endpointInterface = "org.mule.tools.devkit.store.service.IMuleStoreService")
public class MuleStoreServiceImpl implements IMuleStoreService {

    private static final Logger LOG = Logger.getLogger(MuleStoreServiceImpl.class.getName());

    Integer currentIndex = 0;
    Integer exceptionRatio = 3;
    Integer callsCount = 0;
    private Map<Integer, Entity> entities = new HashMap<>();

    @Override public List<Entity> getRecentlyAdded() throws SessionExpiredException {
        LOG.info("Executing operation getRecentlyAdded");
        callsCount++;
        currentIndex = callsCount;
        checkExpiredSessionCondition();
        Collection<Entity> values = entities.values();
        return CollectionUtils.arrayToList(values.toArray());
    }

    @Override public Entity get(@WebParam(name = "id", targetNamespace = "") int id) throws NoSuchEntityException, SessionExpiredException {
        checkExpiredSessionCondition();

        if (!entities.containsKey(id)) {

            throw new NoSuchEntityException();
        }
        return entities.get(id);
    }

    @Override public List<Entity> searchWithQuery(@WebParam(name = "query", targetNamespace = "") String query, @WebParam(name = "page", targetNamespace = "") Integer page,
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

    @Override public Entity update(@WebParam(name = "entity", targetNamespace = "") Entity entity) throws InvalidEntityException, NoSuchEntityException, SessionExpiredException {
        checkExpiredSessionCondition();
        if (!entities.containsKey(entity.getId())) {
            throw new NoSuchEntityException();
        }
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override public List<Entity> addList(@WebParam(name = "entities", targetNamespace = "") List<Entity> entities) throws InvalidEntityException, SessionExpiredException {
        checkExpiredSessionCondition();
        for (Entity entity : entities) {
            create(entity);
        }
        return entities;
    }

    @Override public List<Entity> getList(@WebParam(name = "entityIds", targetNamespace = "") List<Integer> entityIds) throws NoSuchEntityException, SessionExpiredException {
        checkExpiredSessionCondition();
        List<Entity> returnValue = new ArrayList<>();
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

    @Override public List<Entity> updateList(@WebParam(name = "entities", targetNamespace = "") List<Entity> entities)
            throws InvalidEntityException, NoSuchEntityException, SessionExpiredException {
        checkExpiredSessionCondition();
        for (Entity entity : entities) {
            update(entity);
        }
        return entities;
    }

    @Override public void deleteList(@WebParam(name = "entityIds", targetNamespace = "") List<Integer> entityIds) throws NoSuchEntityException, SessionExpiredException {
        checkExpiredSessionCondition();
        for (Integer id : entityIds) {
            delete(id);
        }
    }

    @Override public Entity create(@WebParam(name = "entity", targetNamespace = "") Entity entity) throws InvalidEntityException, SessionExpiredException {
        checkExpiredSessionCondition();
        if (entity.getId() != null) {
            InvalidEntity invalid = new InvalidEntity();
            throw new InvalidEntityException("Cannot specify Id at creation.", invalid);
        }
        if (entity instanceof Book) {
            Book book = (Book) entity;
        }
        if (entity instanceof Author) {
            Author author = (Author) entity;
            if (StringUtils.isEmpty(author.getFirstName())) {
                InvalidEntity invalid = new InvalidEntity();
                throw new InvalidEntityException("Invalid Author", invalid);
            }
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
