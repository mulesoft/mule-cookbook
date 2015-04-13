/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cookbook.tutorial.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.cookbook.tutorial.customization.Description;
import org.apache.cxf.annotations.WSDLDocumentation;
import com.cookbook.tutorial.model.CookBookEntity;
import com.cookbook.tutorial.model.Recipe;

import java.util.List;

/**
 * Created by Mulesoft.
 */
@WebService
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface IMuleCookBookService {

    @WSDLDocumentation("Create an ingredient or recipe.")
    CookBookEntity create(@WebParam(name = "entity") CookBookEntity entity,
            @WebParam(name = "token",header = true) String token) throws InvalidEntityException, SessionExpiredException, InvalidTokenException;

    @WSDLDocumentation("Updates an ingredient or recipe.")
    CookBookEntity update(@WebParam(name = "entity") CookBookEntity entity,
            @WebParam(name = "token",header = true) String token) throws InvalidEntityException, NoSuchEntityException, SessionExpiredException, InvalidTokenException;

    @WSDLDocumentation("Retrieves an ingredient or recipe.")
    CookBookEntity get(@WebParam(name = "id") int entityId,
            @WebParam(name = "token",header = true) String token) throws NoSuchEntityException, SessionExpiredException, InvalidTokenException;

    @WSDLDocumentation("Deletes an ingredient or recipe.")
    void delete(@WebParam(name = "id") int entityId,
            @WebParam(name = "token",header = true) String token) throws NoSuchEntityException, SessionExpiredException, InvalidTokenException;

    @WSDLDocumentation("Creates a list of ingredient or recipe.")
    List<CookBookEntity> addList(@WebParam(name = "entities") List<CookBookEntity> entities,
            @WebParam(name = "token",header = true) String token) throws InvalidEntityException, SessionExpiredException, InvalidTokenException;

    @WSDLDocumentation("Updates a list of ingredient or recipe.")
    List<CookBookEntity> updateList(@WebParam(name = "entities") List<CookBookEntity> entities,
            @WebParam(name = "token",header = true) String token) throws InvalidEntityException, NoSuchEntityException, SessionExpiredException, InvalidTokenException;

    @WSDLDocumentation("Retrieves a list of ingredient or recipe.")
    List<CookBookEntity> getList(@WebParam(name = "entityIds") List<Integer> entityIds,
            @WebParam(name = "token",header = true) String token) throws NoSuchEntityException, SessionExpiredException, InvalidTokenException;

    @WSDLDocumentation("Deletes a list of ingredient or recipe.")
    void deleteList(@WebParam(name = "entityIds") List<Integer> entityIds,@WebParam(name = "token",header = true) String token) throws NoSuchEntityException, SessionExpiredException, InvalidTokenException;

    @WSDLDocumentation("Searchs a list of ingredient or recipe matching a specific criteria.")
    List<CookBookEntity> searchWithQuery(@WebParam(name = "query") String query, @WebParam(name = "page") Integer page, @WebParam(name = "pageSize") Integer pageSize,
            @WebParam(name = "token",header = true) String token)
            throws SessionExpiredException, InvalidTokenException, InvalidRequestException;

    @WSDLDocumentation("Retrieves a list of the recently added recipies.")
    List<Recipe> getRecentlyAdded();

    @WSDLDocumentation("Describes an entity given the Id and entity Type.")
    Description describeEntity(@WebParam(name = "entity") CookBookEntity entity,
            @WebParam(name = "token",header = true) String token) throws InvalidEntityException, NoSuchEntityException, SessionExpiredException, InvalidTokenException;

    @WSDLDocumentation("List of CookBook Entities that can be retrieved from the server.")
    List<CookBookEntity> getEntitiesList(@WebParam(name = "token",header = true) String token) throws InvalidTokenException, SessionExpiredException;

    @WSDLDocumentation("Logs into system, and returns a token to be used in future requests.")
    String login(@WebParam(name = "accountId") String accountId,@WebParam(name = "password") String password) throws InvalidCredentialsException;

    @WSDLDocumentation("Logs out.")
    void logout(@WebParam(name = "token") String token) throws InvalidTokenException;
}
