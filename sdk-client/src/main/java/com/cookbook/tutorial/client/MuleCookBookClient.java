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

    public MuleCookBookClient() {

        IMuleCookBookServiceService ss = new IMuleCookBookServiceService();
        port = ss.getIMuleCookBookServicePort();
    }

    public MuleCookBookClient(String address) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(IMuleCookBookService.class);

        port = (IMuleCookBookService) factory.create();
        ((BindingProvider) port).getRequestContext().put(
                (BindingProvider.ENDPOINT_ADDRESS_PROPERTY), address);
    }

    public void login(String username, String password) throws InvalidCredentialsException {
        token = port.login(username, password);
    }

    public List<Recipe> getRecentlyAdded() {
        return port.getRecentlyAdded();
    }

    public CookBookEntity get(int id) throws NoSuchEntityException, SessionExpiredException {
        Get request = new Get();
        request.setId(id);
        try {
            return port.get(request, token).getReturn();
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    public List<CookBookEntity> searchWithQuery(String query, Integer page, Integer pageSize) throws NoSuchEntityException,
            SessionExpiredException {
        SearchWithQuery request = new SearchWithQuery();
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

    public CookBookEntity update(CookBookEntity entity) throws NoSuchEntityException,
            InvalidEntityException, SessionExpiredException {
        Update request = new Update();
        request.setEntity(entity);
        try {
            return port.update(request, token).getReturn();
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    public List<CookBookEntity> addList(List<CookBookEntity> entities) throws InvalidEntityException,
            SessionExpiredException {
        AddList request = new AddList();
        request.setEntities(entities);
        try {
            return port.addList(request, token).getReturn();
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    public List<CookBookEntity> getList(List<Integer> entityIds) throws NoSuchEntityException,
            SessionExpiredException {
        GetList getList = new GetList();
        getList.setEntityIds(entityIds);
        try {
            return port.getList(getList, token).getReturn();
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) throws NoSuchEntityException, SessionExpiredException {
        Delete delete = new Delete();
        delete.setId(id);
        try {
            port.delete(delete, token);
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    public List<CookBookEntity> updateList(List<CookBookEntity> entities) throws NoSuchEntityException,
            InvalidEntityException, SessionExpiredException {
        UpdateList request = new UpdateList();
        request.setEntities(entities);
        try {
            return port.updateList(request, token).getReturn();
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }

    }

    public void deleteList(List<Integer> entityIds) throws NoSuchEntityException,
            SessionExpiredException {
        DeleteList request = new DeleteList();
        request.setEntityIds(entityIds);
        try {
            port.deleteList(request, token);
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    public CookBookEntity create(CookBookEntity entity) throws InvalidEntityException,
            SessionExpiredException {
        Create request = new Create();
        request.setEntity(entity);
        try {
            return port.create(request, token).getReturn();
        } catch (InvalidTokenException e) {
            logger.warn("Should never happen.", e);
            throw new RuntimeException(e);
        }
    }
}
