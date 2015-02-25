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

    public void login(String username,String password) throws InvalidCredentialsException {
        token = port.login(username,password);
    }

    public List<Recipe> getRecentlyAdded() {
        return port.getRecentlyAdded();
    }

    public CookBookEntity get(int id) throws NoSuchEntityException, SessionExpiredException, InvalidTokenException {
        Get request = new Get();
        request.setId(id);
        return port.get(request, token).getReturn();
    }

    public List<CookBookEntity> searchWithQuery(String query, Integer page, Integer pageSize) throws NoSuchEntityException,
            SessionExpiredException, InvalidTokenException {
        SearchWithQuery request = new SearchWithQuery();
        request.setPage(page);
        request.setPageSize(pageSize);
        request.setQuery(query);
        return port.searchWithQuery(request,token).getReturn();
    }

    public CookBookEntity update(CookBookEntity entity) throws NoSuchEntityException,
            InvalidEntityException, SessionExpiredException, InvalidTokenException {
        Update request = new Update();
        request.setEntity(entity);
        return port.update(request,token).getReturn();
    }

    public List<CookBookEntity> addList(List<CookBookEntity> entities) throws InvalidEntityException,
            SessionExpiredException, InvalidTokenException {
        AddList request = new AddList();
        request.setEntities(entities);
        return port.addList(request,token).getReturn();
    }

    public List<CookBookEntity> getList(List<Integer> entityIds) throws NoSuchEntityException,
            SessionExpiredException, InvalidTokenException {
        GetList getList = new GetList();
        getList.setEntityIds(entityIds);
        return port.getList(getList,token).getReturn();
    }

    public void delete(int id) throws NoSuchEntityException, SessionExpiredException, InvalidTokenException {
        Delete delete=new Delete();
        delete.setId(id);
        port.delete(delete,token);
    }

    public List<CookBookEntity> updateList(List<CookBookEntity> entities) throws NoSuchEntityException,
            InvalidEntityException, SessionExpiredException, InvalidTokenException {
        UpdateList request = new UpdateList();
        request.setEntities(entities);
        return port.updateList(request,token).getReturn();
    }

    public void deleteList(List<Integer> entityIds) throws NoSuchEntityException,
            SessionExpiredException, InvalidTokenException {
        DeleteList request = new DeleteList();
        request.setEntityIds(entityIds);
        port.deleteList(request,token);
    }

    public CookBookEntity create(CookBookEntity entity) throws InvalidEntityException,
            SessionExpiredException, InvalidTokenException {
        Create request = new Create();
        request.setEntity(entity);
        return port.create(request,token).getReturn();
    }
}
