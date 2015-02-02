package org.mule.tools.devkit.cookbook.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.mule.tools.devkit.cookbook.service.*;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.net.URL;
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

    public List<Recipe> getRecentlyAdded() throws org.mule.tools.devkit.cookbook.service.SessionExpiredException {
        return port.getRecentlyAdded();
    }

    public CookBookEntity get(int id) throws org.mule.tools.devkit.cookbook.service.NoSuchEntityException, org.mule.tools.devkit.cookbook.service.SessionExpiredException {
        return port.get(id);
    }

    public List<CookBookEntity> searchWithQuery(String query, Integer page, Integer pageSize) throws org.mule.tools.devkit.cookbook.service.NoSuchEntityException,
            org.mule.tools.devkit.cookbook.service.SessionExpiredException {
        return port.searchWithQuery(query, page, pageSize);
    }

    public CookBookEntity update(CookBookEntity entity) throws org.mule.tools.devkit.cookbook.service.NoSuchEntityException,
            org.mule.tools.devkit.cookbook.service.InvalidEntityException, org.mule.tools.devkit.cookbook.service.SessionExpiredException {
        return port.update(entity);
    }

    public List<CookBookEntity> addList(List<CookBookEntity> entities) throws org.mule.tools.devkit.cookbook.service.InvalidEntityException,
            org.mule.tools.devkit.cookbook.service.SessionExpiredException {
        return port.addList(entities);
    }

    public List<CookBookEntity> getList(List<Integer> entityIds) throws org.mule.tools.devkit.cookbook.service.NoSuchEntityException,
            org.mule.tools.devkit.cookbook.service.SessionExpiredException {
        return port.getList(entityIds);
    }

    public void delete(int id) throws org.mule.tools.devkit.cookbook.service.NoSuchEntityException, org.mule.tools.devkit.cookbook.service.SessionExpiredException {
        port.delete(id);
    }

    public List<CookBookEntity> updateList(List<CookBookEntity> entities) throws org.mule.tools.devkit.cookbook.service.NoSuchEntityException,
            org.mule.tools.devkit.cookbook.service.InvalidEntityException, org.mule.tools.devkit.cookbook.service.SessionExpiredException {
        return port.updateList(entities);
    }

    public void deleteList(List<Integer> entityIds) throws org.mule.tools.devkit.cookbook.service.NoSuchEntityException,
            org.mule.tools.devkit.cookbook.service.SessionExpiredException {
        port.deleteList(entityIds);
    }

    public CookBookEntity create(CookBookEntity entity) throws org.mule.tools.devkit.cookbook.service.InvalidEntityException,
            org.mule.tools.devkit.cookbook.service.SessionExpiredException {
        return port.create(entity);
    }
}
