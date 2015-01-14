package org.mule.tools.devkit.store.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.mule.tools.devkit.store.service.*;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.net.URL;
import java.util.List;

/**
 * Created by Mulesoft.
 */
public class MuleStoreClient{

    private IMuleStoreService port;

    private static final QName SERVICE_NAME = new QName("http://service.store.devkit.tools.mule.org/", "IMuleStoreServiceService");

    public MuleStoreClient(){
        URL wsdlURL = IMuleStoreServiceService.WSDL_LOCATION;

        IMuleStoreServiceService ss = new IMuleStoreServiceService(wsdlURL, SERVICE_NAME);
        port = ss.getIMuleStoreServicePort();
    }

    public MuleStoreClient(String address){
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(IMuleStoreService.class);

        port = (IMuleStoreService) factory.create();
        ((BindingProvider) port).getRequestContext().put(
                (BindingProvider.ENDPOINT_ADDRESS_PROPERTY), address);
    }
    public List<Entity> getRecentlyAdded() throws SessionExpiredException{
        return port.getRecentlyAdded();
    }

    public Entity get(int id) throws NoSuchEntityException, SessionExpiredException{
        return port.get(id);
    }

    public List<Entity> searchWithQuery(String query,Integer page,Integer pageSize) throws NoSuchEntityException,SessionExpiredException {
        return port.searchWithQuery(query,page,pageSize);
    }

    public Entity update(Entity entity) throws NoSuchEntityException, InvalidEntityException,SessionExpiredException {
        return port.update(entity);
    }

    public List<Entity> addList( List<Entity> entities) throws InvalidEntityException,SessionExpiredException {
        return port.addList(entities);
    }

    public List<Entity> getList( List<Integer> entityIds) throws NoSuchEntityException,SessionExpiredException {
        return port.getList(entityIds);
    }

    public void delete(int id) throws NoSuchEntityException,SessionExpiredException {
        port.delete(id);
    }

    public List<Entity> updateList(List<Entity> entities) throws NoSuchEntityException, InvalidEntityException,SessionExpiredException {
        return port.updateList(entities);
    }

    public void deleteList(List<Integer> entityIds) throws NoSuchEntityException,SessionExpiredException {
        port.deleteList(entityIds);
    }

    public Entity add(Entity entity) throws InvalidEntityException,SessionExpiredException {
        return port.add(entity);
    }
}
