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
package org.mule.tools.devkit.store.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import org.mule.tools.devkit.store.model.Entity;

import java.util.List;

/**
 * Created by Mulesoft.
 */
@WebService
public interface IMuleStoreService {

	Entity add(@WebParam(name = "entity") Entity entity) throws InvalidEntityException, SessionExpiredException;
	Entity update(@WebParam(name = "entity")Entity entity) throws InvalidEntityException,NoSuchEntityException,SessionExpiredException;
	Entity get(@WebParam(name = "id") int entityId) throws NoSuchEntityException,SessionExpiredException;
	void delete(@WebParam(name = "id") int entityId) throws NoSuchEntityException,SessionExpiredException;

	List<Entity> addList(@WebParam(name = "entities") List<Entity> entities) throws InvalidEntityException,SessionExpiredException;
	List<Entity> updateList(@WebParam(name = "entities") List<Entity> entities) throws InvalidEntityException,NoSuchEntityException,SessionExpiredException;
	List<Entity> getList(@WebParam(name = "entityIds") List<Integer> entityIds) throws NoSuchEntityException,SessionExpiredException;
	void deleteList(@WebParam(name = "entityIds") List<Integer> entityIds) throws NoSuchEntityException,SessionExpiredException;

	List<Entity> searchWithQuery(@WebParam(name="query") String query,@WebParam(name = "page") Integer page,@WebParam(name = "pageSize") Integer pageSize) throws NoSuchEntityException,SessionExpiredException;

	List<Entity> getRecentlyAdded() throws SessionExpiredException;

}
