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

    private Integer currentIndex = 0;
    private Integer exceptionRatio = 3;

    private IMuleCookBookService serviceDAL;

    @Override public List<Recipe> getRecentlyAdded() {
        return serviceDAL.getRecentlyAdded();
    }

    @Override public CookBookEntity get(@WebParam(name = "id", targetNamespace = "") int id) throws NoSuchEntityException, SessionExpiredException {
        checkExpiredSessionCondition();
        return serviceDAL.get(id);
    }

    @Override public List<CookBookEntity> searchWithQuery(@WebParam(name = "query", targetNamespace = "") String query, @WebParam(name = "page", targetNamespace = "") Integer page,
            @WebParam(name = "pageSize", targetNamespace = "") Integer pageSize) throws NoSuchEntityException, SessionExpiredException {

        LOG.info("Executing operation searchWithQuery");
        checkExpiredSessionCondition();
        return serviceDAL.searchWithQuery(query,page,pageSize);
    }

    @Override public CookBookEntity update(@WebParam(name = "entity", targetNamespace = "") CookBookEntity entity) throws InvalidEntityException, NoSuchEntityException,
            SessionExpiredException {
        checkExpiredSessionCondition();
        return serviceDAL.update(entity);
    }

    @Override public List<CookBookEntity> addList(@WebParam(name = "entities", targetNamespace = "") List<CookBookEntity> entities) throws InvalidEntityException,
            SessionExpiredException {
        checkExpiredSessionCondition();
        return serviceDAL.addList(entities);
    }

    @Override public List<CookBookEntity> getList(@WebParam(name = "entityIds", targetNamespace = "") List<Integer> entityIds) throws NoSuchEntityException,
            SessionExpiredException {
        checkExpiredSessionCondition();
        return serviceDAL.getList(entityIds);
    }

    @Override public void delete(@WebParam(name = "id", targetNamespace = "") int id) throws NoSuchEntityException, SessionExpiredException {
        checkExpiredSessionCondition();
        serviceDAL.delete(id);
    }

    @Override public List<CookBookEntity> updateList(@WebParam(name = "entities", targetNamespace = "") List<CookBookEntity> entities)
            throws InvalidEntityException, NoSuchEntityException, SessionExpiredException {
        checkExpiredSessionCondition();
        return serviceDAL.updateList(entities);
    }

    @Override public Recipe updateQuantities(@WebParam(name = "recipe", targetNamespace = "") Recipe recipe)
            throws NoSuchEntityException, SessionExpiredException, InvalidEntityException {
        return serviceDAL.updateQuantities(recipe);
    }

    @Override public void deleteList(@WebParam(name = "entityIds", targetNamespace = "") List<Integer> entityIds) throws NoSuchEntityException, SessionExpiredException {
        checkExpiredSessionCondition();
        serviceDAL.deleteList(entityIds);
    }

    @Override public CookBookEntity create(@WebParam(name = "entity", targetNamespace = "") CookBookEntity entity) throws InvalidEntityException, SessionExpiredException {
        checkExpiredSessionCondition();
        return serviceDAL.create(entity);
    }

    private void checkExpiredSessionCondition() throws SessionExpiredException {
        if (((currentIndex+1) % exceptionRatio) == 0) {
            throw new SessionExpiredException();
        }
    }

    public IMuleCookBookService getServiceDAL() {
        return serviceDAL;
    }

    public void setServiceDAL(IMuleCookBookService serviceDAL) {
        this.serviceDAL = serviceDAL;
    }

    public Integer getExceptionRatio() {
        return exceptionRatio;
    }

    public void setExceptionRatio(Integer exceptionRatio) {
        this.exceptionRatio = exceptionRatio;
    }
}
