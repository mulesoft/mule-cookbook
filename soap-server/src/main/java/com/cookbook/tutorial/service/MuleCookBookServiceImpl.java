package com.cookbook.tutorial.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Mulesoft.
 */
@javax.jws.WebService(
        serviceName = "IMuleCookBookServiceService",
        portName = "IMuleCookBookServicePort",
        targetNamespace = "http://service.tutorial.cookbook.com/",
        wsdlLocation = "wsdl/IMuleCookBookService.wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class MuleCookBookServiceImpl implements IMuleCookBookService {

    private static final Logger logger = Logger.getLogger(MuleCookBookServiceImpl.class.getName());

    private Integer currentIndex = 0;
    private Integer exceptionRatio = 10;

    private IDAOCookBookService serviceDAL;

    @Override
    public CreateResponse create(@WebParam(partName = "parameters", name = "create", targetNamespace = "http://service.tutorial.cookbook.com/") Create parameters,
            @WebParam(partName = "token", name = "token", targetNamespace = "http://service.tutorial.cookbook.com/", header = true) String token)
            throws InvalidTokenException, SessionExpiredException, InvalidEntityException {
        verifyToken(token);
        checkExpiredSessionCondition();
        CreateResponse response = new CreateResponse();
        response.setReturn(serviceDAL.create(parameters.getEntity()));
        return response;
    }

    @Override
    public GetListResponse getList(@WebParam(partName = "parameters", name = "getList", targetNamespace = "http://service.tutorial.cookbook.com/") GetList parameters,
            @WebParam(partName = "token", name = "token", targetNamespace = "http://service.tutorial.cookbook.com/", header = true) String token)
            throws InvalidTokenException, SessionExpiredException, NoSuchEntityException {
        verifyToken(token);
        checkExpiredSessionCondition();
        GetListResponse response = new GetListResponse();
        response.setReturn(serviceDAL.getList(parameters.getEntityIds()));
        return response;
    }

    @Override
    public AddListResponse addList(@WebParam(partName = "parameters", name = "addList", targetNamespace = "http://service.tutorial.cookbook.com/") AddList parameters,
            @WebParam(partName = "token", name = "token", targetNamespace = "http://service.tutorial.cookbook.com/", header = true) String token)
            throws InvalidTokenException, SessionExpiredException, InvalidEntityException {
        verifyToken(token);
        checkExpiredSessionCondition();
        AddListResponse response = new AddListResponse();
        response.setReturn(serviceDAL.addList(parameters.getEntities()));
        return response;
    }

    @Override
    public DescribeEntityResponse describeEntity(
            @WebParam(partName = "parameters", name = "describeEntity", targetNamespace = "http://service.tutorial.cookbook.com/") DescribeEntity parameters,
            @WebParam(partName = "token", name = "token", targetNamespace = "http://service.tutorial.cookbook.com/", header = true) String token)
            throws InvalidTokenException, SessionExpiredException, InvalidEntityException, NoSuchEntityException {
        verifyToken(token);
        checkExpiredSessionCondition();
        DescribeEntityResponse response = new DescribeEntityResponse();
        response.setReturn(serviceDAL.describeEntity(parameters.getEntity()));
        return response;
    }

    @Override
    public DeleteListResponse deleteList(
            @WebParam(partName = "parameters", name = "deleteList", targetNamespace = "http://service.tutorial.cookbook.com/") DeleteList parameters,
            @WebParam(partName = "token", name = "token", targetNamespace = "http://service.tutorial.cookbook.com/", header = true) String token)
            throws InvalidTokenException, SessionExpiredException, NoSuchEntityException {
        verifyToken(token);
        checkExpiredSessionCondition();
        DeleteListResponse response = new DeleteListResponse();
        serviceDAL.deleteList(parameters.getEntityIds());
        return response;
    }

    @Override
    public String login(@WebParam(name = "accountId", targetNamespace = "") String accountId, @WebParam(name = "password", targetNamespace = "") String password)
            throws InvalidCredentialsException {
        //Only work for admin admin
        if("admin".equals(accountId) && "admin".equals(password)){
            return Constants.DEFAULT_TOKEN;
        }
        throw new InvalidCredentialsException();
    }

    @Override
    public SearchWithQueryResponse searchWithQuery(
            @WebParam(partName = "parameters", name = "searchWithQuery", targetNamespace = "http://service.tutorial.cookbook.com/") SearchWithQuery parameters,
            @WebParam(partName = "token", name = "token", targetNamespace = "http://service.tutorial.cookbook.com/", header = true) String token)
            throws InvalidTokenException, SessionExpiredException, NoSuchEntityException {
        verifyToken(token);
        checkExpiredSessionCondition();
        SearchWithQueryResponse response = new SearchWithQueryResponse();
        response.setReturn(serviceDAL.searchWithQuery(parameters.getQuery(),parameters.getPage(),parameters.getPageSize()));
        return response;
    }

    @Override
    public UpdateResponse update(@WebParam(partName = "parameters", name = "update", targetNamespace = "http://service.tutorial.cookbook.com/") Update parameters,
            @WebParam(partName = "token", name = "token", targetNamespace = "http://service.tutorial.cookbook.com/", header = true) String token)
            throws InvalidTokenException, SessionExpiredException, InvalidEntityException, NoSuchEntityException {
        verifyToken(token);
        checkExpiredSessionCondition();
        UpdateResponse response = new UpdateResponse();
        response.setReturn(serviceDAL.update(parameters.getEntity()));
        return response;
    }

    @Override
    public UpdateListResponse updateList(
            @WebParam(partName = "parameters", name = "updateList", targetNamespace = "http://service.tutorial.cookbook.com/") UpdateList parameters,
            @WebParam(partName = "token", name = "token", targetNamespace = "http://service.tutorial.cookbook.com/", header = true) String token)
            throws InvalidTokenException, SessionExpiredException, InvalidEntityException, NoSuchEntityException {
        verifyToken(token);
        checkExpiredSessionCondition();
        UpdateListResponse response= new UpdateListResponse();
        response.setReturn(serviceDAL.updateList(parameters.getEntities()));
        return response;
    }

    @Override
    public List<Recipe> getRecentlyAdded() {
        return serviceDAL.getRecentlyAdded();
    }

    @Override
    public void logout(@WebParam(name = "token", targetNamespace = "") String token) throws InvalidTokenException {
        verifyToken(token);
    }

    @Override public GetEntitiesListResponse getEntitiesList(
            @WebParam(partName = "parameters", name = "getEntitiesList", targetNamespace = "http://service.tutorial.cookbook.com/") GetEntitiesList parameters,
            @WebParam(partName = "token", name = "token", targetNamespace = "http://service.tutorial.cookbook.com/", header = true) String token)
            throws InvalidTokenException, SessionExpiredException {
        verifyToken(token);
        GetEntitiesListResponse response = new GetEntitiesListResponse();
        response.setReturn(serviceDAL.getEntitiesList());
        return response;
    }

    @Override
    public DeleteResponse delete(@WebParam(partName = "parameters", name = "delete", targetNamespace = "http://service.tutorial.cookbook.com/") Delete parameters,
            @WebParam(partName = "token", name = "token", targetNamespace = "http://service.tutorial.cookbook.com/", header = true) String token)
            throws InvalidTokenException, SessionExpiredException, NoSuchEntityException {
        verifyToken(token);
        checkExpiredSessionCondition();
        DeleteResponse response = new DeleteResponse();
        serviceDAL.delete(parameters.getId());
        return response;
    }

    @Override
    public GetResponse get(@WebParam(partName = "parameters", name = "get", targetNamespace = "http://service.tutorial.cookbook.com/") Get parameters,
            @WebParam(partName = "token", name = "token", targetNamespace = "http://service.tutorial.cookbook.com/", header = true) String token)
            throws InvalidTokenException, SessionExpiredException, NoSuchEntityException {
        verifyToken(token);
        checkExpiredSessionCondition();
        GetResponse response = new GetResponse();
        response.setReturn(serviceDAL.get(parameters.getId()));
        return response;
    }

    @Override
    public UpdateQuantitiesResponse updateQuantities(
            @WebParam(partName = "parameters", name = "updateQuantities", targetNamespace = "http://service.tutorial.cookbook.com/") UpdateQuantities parameters,
            @WebParam(partName = "token", name = "token", targetNamespace = "http://service.tutorial.cookbook.com/", header = true) String token)
            throws InvalidTokenException, SessionExpiredException, InvalidEntityException, NoSuchEntityException {
        verifyToken(token);
        checkExpiredSessionCondition();
        UpdateQuantitiesResponse updateQuantitiesResponse = new UpdateQuantitiesResponse();
        updateQuantitiesResponse.setReturn(serviceDAL.updateQuantities(parameters.getRecipe()));
        return updateQuantitiesResponse;
    }

    private void checkExpiredSessionCondition() throws SessionExpiredException {
        currentIndex++;
        if (((currentIndex) % exceptionRatio) == 0) {
            throw new SessionExpiredException();
        }

    }

    private void verifyToken(String token) throws InvalidTokenException {
        if(!token.equals(Constants.DEFAULT_TOKEN)){
            throw new InvalidTokenException();
        }
    }

    @WebMethod(exclude = true)
    public IDAOCookBookService getServiceDAL() {
        return serviceDAL;
    }

    @WebMethod(exclude = true)
    public void setServiceDAL(IDAOCookBookService serviceDAL) {
        this.serviceDAL = serviceDAL;
    }

    @WebMethod(exclude = true)
    public Integer getExceptionRatio() {
        return exceptionRatio;
    }

    @WebMethod(exclude = true)
    public void setExceptionRatio(Integer exceptionRatio) {
        this.exceptionRatio = exceptionRatio;
    }
}
