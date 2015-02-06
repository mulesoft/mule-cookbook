package com.cookbook.tutorial.client;

import com.cookbook.tutorial.service.*;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import javax.xml.ws.BindingProvider;
import java.util.List;

/**
 * Created by Mulesoft.
 */
public class MuleCookBookClient {

    private IMuleCookBookService port;

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

    public List<Recipe> getRecentlyAdded() {
        return port.getRecentlyAdded();
    }

    public CookBookEntity get(int id) throws NoSuchEntityException, SessionExpiredException {
        return port.get(id);
    }

    public List<CookBookEntity> searchWithQuery(String query, Integer page, Integer pageSize) throws NoSuchEntityException,
            SessionExpiredException {
        return port.searchWithQuery(query, page, pageSize);
    }

    public CookBookEntity update(CookBookEntity entity) throws NoSuchEntityException,
            InvalidEntityException, SessionExpiredException {
        return port.update(entity);
    }

    public List<CookBookEntity> addList(List<CookBookEntity> entities) throws InvalidEntityException,
            SessionExpiredException {
        return port.addList(entities);
    }

    public List<CookBookEntity> getList(List<Integer> entityIds) throws NoSuchEntityException,
            SessionExpiredException {
        return port.getList(entityIds);
    }

    public void delete(int id) throws NoSuchEntityException, SessionExpiredException {
        port.delete(id);
    }

    public List<CookBookEntity> updateList(List<CookBookEntity> entities) throws NoSuchEntityException,
            InvalidEntityException, SessionExpiredException {
        return port.updateList(entities);
    }

    public void deleteList(List<Integer> entityIds) throws NoSuchEntityException,
            SessionExpiredException {
        port.deleteList(entityIds);
    }

    public CookBookEntity create(CookBookEntity entity) throws InvalidEntityException,
            SessionExpiredException {
        return port.create(entity);
    }
}
