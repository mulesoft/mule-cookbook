package com.cookbook.tutorial.client;

import com.cookbook.tutorial.service.*;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.BindingProvider;
import java.util.List;

/**
 * Created by Mulesoft.
 */
public class MuleCookBookClient implements IMuleCookBookClient {

    private static final Logger logger = LoggerFactory.getLogger(MuleCookBookClient.class);
    private IMuleCookBookService port;
    private String token;
    ObjectFactory factory;
    public MuleCookBookClient() {

        IMuleCookBookServiceService ss = new IMuleCookBookServiceService();
        factory = new ObjectFactory();
        port = ss.getIMuleCookBookServicePort();
    }

    public MuleCookBookClient(String address) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(IMuleCookBookService.class);
        this.factory = new ObjectFactory();
        port = (IMuleCookBookService) factory.create();
        ((BindingProvider) port).getRequestContext().put(
                (BindingProvider.ENDPOINT_ADDRESS_PROPERTY), address);
    }

    @Override
    public void login(String username, String password) throws InvalidCredentialsException {
        token = port.login(username, password);
    }

    @Override
    public List<Recipe> getRecentlyAdded() {
        return port.getRecentlyAdded();
    }

    @Override
    public void getRecentlyAdded(ICookbookCallback callback) throws Exception {
        while(!Thread.interrupted()){
            callback.execute(port.getRecentlyAdded());
            Thread.sleep(10000);
        }
    }

    @Override
    public CookBookEntity get(int id) throws NoSuchEntityException, SessionExpiredException {
        Get request = factory.createGet();
        request.setId(id);
        try {
            return port.get(request, token).getReturn();
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CookBookEntity> searchWithQuery(String query, Integer page, Integer pageSize) throws InvalidRequestException,
            SessionExpiredException {
        SearchWithQuery request = factory.createSearchWithQuery();
        request.setPage(page);
        request.setPageSize(pageSize);
        request.setQuery(query);
        try {
            return port.searchWithQuery(request, token).getReturn();
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public CookBookEntity update(CookBookEntity entity) throws NoSuchEntityException,
            InvalidEntityException, SessionExpiredException {
        Update request = factory.createUpdate();
        request.setEntity(entity);
        try {
            return port.update(request, token).getReturn();
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CookBookEntity> addList(List<CookBookEntity> entities) throws InvalidEntityException,
            SessionExpiredException {
        AddList request = factory.createAddList();
        request.setEntities(entities);
        try {
            return port.addList(request, token).getReturn();
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CookBookEntity> getList(List<Integer> entityIds) throws NoSuchEntityException,
            SessionExpiredException {
        GetList getList = factory.createGetList();
        getList.setEntityIds(entityIds);
        try {
            return port.getList(getList, token).getReturn();
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) throws NoSuchEntityException, SessionExpiredException {
        Delete delete = factory.createDelete();
        delete.setId(id);
        try {
            port.delete(delete, token);
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CookBookEntity> updateList(List<CookBookEntity> entities) throws NoSuchEntityException,
            InvalidEntityException, SessionExpiredException {
        UpdateList request = factory.createUpdateList();
        request.setEntities(entities);
        try {
            return port.updateList(request, token).getReturn();
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteList(List<Integer> entityIds) throws NoSuchEntityException,
            SessionExpiredException {
        DeleteList request = factory.createDeleteList();
        request.setEntityIds(entityIds);
        try {
            port.deleteList(request, token);
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public CookBookEntity create(CookBookEntity entity) throws InvalidEntityException,
            SessionExpiredException {
        Create request = factory.createCreate();
        request.setEntity(entity);
        try {
            return port.create(request, token).getReturn();
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CookBookEntity> getEntities() throws SessionExpiredException{
        try {
            return port.getEntitiesList(factory.createGetEntitiesList(),token).getReturn();
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Description describeEntity(CookBookEntity cookBookEntity) throws InvalidTokenException, InvalidEntityException, NoSuchEntityException, SessionExpiredException {
        DescribeEntity parameters= factory.createDescribeEntity();
        parameters.setEntity(cookBookEntity);
        return port.describeEntity(parameters,token).getReturn();
    }
}
