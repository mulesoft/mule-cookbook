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
package org.mule.tools.devkit.cookbook.service;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.cxf.annotations.WSDLDocumentation;
import org.mule.tools.devkit.cookbook.model.CookBookEntity;
import org.mule.tools.devkit.cookbook.model.Recipe;

import java.util.List;

/**
 * Created by Mulesoft.
 */
@WebService
public interface IMuleCookBookService {

    @WSDLDocumentation("Create an ingredient or recipe.")
    CookBookEntity create(@WebParam(name = "entity") CookBookEntity entity) throws InvalidEntityException, SessionExpiredException;

    @WSDLDocumentation("Updates an ingredient or recipe.")
    CookBookEntity update(@WebParam(name = "entity") CookBookEntity entity) throws InvalidEntityException, NoSuchEntityException, SessionExpiredException;

    @WSDLDocumentation("Retrieves an ingredient or recipe.")
    CookBookEntity get(@WebParam(name = "id") int entityId) throws NoSuchEntityException, SessionExpiredException;

    @WSDLDocumentation("Deletes an ingredient or recipe.")
    void delete(@WebParam(name = "id") int entityId) throws NoSuchEntityException, SessionExpiredException;

    @WSDLDocumentation("Creates a list of ingredient or recipe.")
    List<CookBookEntity> addList(@WebParam(name = "entities") List<CookBookEntity> entities) throws InvalidEntityException, SessionExpiredException;

    @WSDLDocumentation("Updates a list of ingredient or recipe.")
    List<CookBookEntity> updateList(@WebParam(name = "entities") List<CookBookEntity> entities) throws InvalidEntityException, NoSuchEntityException, SessionExpiredException;

    @WSDLDocumentation("Retrieves a list of ingredient or recipe.")
    List<CookBookEntity> getList(@WebParam(name = "entityIds") List<Integer> entityIds) throws NoSuchEntityException, SessionExpiredException;

    @WSDLDocumentation("Deletes a list of ingredient or recipe.")
    void deleteList(@WebParam(name = "entityIds") List<Integer> entityIds) throws NoSuchEntityException, SessionExpiredException;

    @WSDLDocumentation("Searchs a list of ingredient or recipe matching a specific criteria.")
    List<CookBookEntity> searchWithQuery(@WebParam(name = "query") String query, @WebParam(name = "page") Integer page, @WebParam(name = "pageSize") Integer pageSize)
            throws NoSuchEntityException, SessionExpiredException;

    @WSDLDocumentation("Retrieves a list of the recently added recipies.")
    List<Recipe> getRecentlyAdded();

    @WSDLDocumentation("Updates the quantities of a specific list.")
    Recipe updateQuantities(Recipe recipe) throws InvalidEntityException, NoSuchEntityException, SessionExpiredException;

}
