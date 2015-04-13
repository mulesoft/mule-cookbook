/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.cookbook;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.mule.api.MuleException;
import org.mule.api.annotations.ConnectionStrategy;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.MetaDataScope;
import org.mule.api.annotations.Paged;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.Query;
import org.mule.api.annotations.ReconnectOn;
import org.mule.api.annotations.Source;
import org.mule.api.annotations.Transformer;
import org.mule.api.annotations.lifecycle.OnException;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.MetaDataKeyParam;
import org.mule.api.annotations.param.MetaDataKeyParamAffectsType;
import org.mule.api.annotations.param.RefOnly;
import org.mule.api.callback.SourceCallback;
import org.mule.modules.cookbook.datasense.DataSenseResolver;
import org.mule.modules.cookbook.handler.CookBookHandler;
import org.mule.modules.cookbook.strategy.ConnectorConnectionStrategy;
import org.mule.streaming.PagingConfiguration;
import org.mule.streaming.ProviderAwarePagingDelegate;

import com.cookbook.tutorial.client.ICookbookCallback;
import com.cookbook.tutorial.client.MuleCookBookClient;
import com.cookbook.tutorial.service.CookBookEntity;
import com.cookbook.tutorial.service.Ingredient;
import com.cookbook.tutorial.service.InvalidEntityException;
import com.cookbook.tutorial.service.InvalidTokenException;
import com.cookbook.tutorial.service.NoSuchEntityException;
import com.cookbook.tutorial.service.Recipe;
import com.cookbook.tutorial.service.SessionExpiredException;

/**
 * Anypoint Connector
 *
 * @author MuleSoft, Inc.
 */
@Connector(name = "cook-book", friendlyName = "CookBook")
@MetaDataScope(DataSenseResolver.class)
public class CookBookConnector {

	private MuleCookBookClient client;

	@ConnectionStrategy
	ConnectorConnectionStrategy connectionStrategy;

	/**
	 * Get a list of the most recently added Ingredients
	 *
	 * {@sample.xml ../../../doc/cook-book-connector.xml.sample
	 * cook-book:getRecentlyAdded}
	 * 
	 * @return List of recently added Ingredients.
	 *
	 */
	@Processor
	public List<Recipe> getRecentlyAdded() {
		return getClient().getRecentlyAdded();
	}

	
	/**
	 * Description for getRecentlyAddedSource
	 *
	 * {@sample.xml ../../../doc/cook-book-connector.xml.sample
	 * cook-book:getRecentlyAddedSource}
	 *
	 * @param callback
	 *            The callback that will hook the result into mule event.
	 * @throws Exception
	 *             When the source fails.
	 */
	@Source
	public void getRecentlyAddedSource(final SourceCallback callback)
			throws Exception {
		while (true) {
			// Every 5 seconds our callback will be executed
			getClient().getRecentlyAdded(new ICookbookCallback() {

				@Override
				public void execute(List<Recipe> recipes) throws Exception{
						callback.process(recipes);
					
				}
			});

			if (Thread.interrupted()) {
				throw new InterruptedException();
			}
			// This value can be configurable also
			Thread.sleep(10000);
		}
	}

	/**
	 * Description for create
	 *
	 * {@sample.xml ../../../doc/cook-book-connector.xml.sample
	 * cook-book:create}
	 *
	 * @param entity
	 *            Ingredient to be created
	 * @return return Ingredient with Id from the system.
	 * 
	 * @throws InvalidTokenException
	 * @throws SessionExpiredException
	 * @throws InvalidEntityException
	 */
	@SuppressWarnings("unchecked")
	@Processor
	@OnException(handler = CookBookHandler.class)
	@ReconnectOn(exceptions = { SessionExpiredException.class })
	public Map<String, Object> create(
			@MetaDataKeyParam(affects = MetaDataKeyParamAffectsType.BOTH) String type,
			@Default("#[payload]") @RefOnly Map<String, Object> entity)
			throws InvalidEntityException, SessionExpiredException {
		ObjectMapper m = new ObjectMapper();
		CookBookEntity input = null;
		if (type.contains("com.cookbook.tutorial.service.Recipe")) {
			input = m.convertValue(entity, Recipe.class);
		} else if (type.contains("com.cookbook.tutorial.service.Ingredient")) {
			input = m.convertValue(entity, Ingredient.class);
		} else {
			throw new InvalidEntityException("Don't know how to handle type:"
					+ type);
		}
		return m.convertValue(getClient().create(input), Map.class);
	}

	/**
	 * Description for update
	 *
	 * {@sample.xml ../../../doc/cook-book-connector.xml.sample
	 * cook-book:update}
	 *
	 * @param entity
	 *            Ingredient to be updated
	 * @return return Ingredient with Id from the system.
	 * 
	 * @throws SessionExpiredException
	 * @throws InvalidEntityException
	 * @throws NoSuchEntityException
	 */
	@SuppressWarnings("unchecked")
	@Processor
	@OnException(handler = CookBookHandler.class)
	@ReconnectOn(exceptions = { SessionExpiredException.class })
	public Map<String, Object> update(
			@MetaDataKeyParam(affects = MetaDataKeyParamAffectsType.BOTH) String type,
			@Default("#[payload]") @RefOnly Map<String, Object> entity)
			throws InvalidEntityException, SessionExpiredException,
			NoSuchEntityException {
		ObjectMapper m = new ObjectMapper();
		CookBookEntity input = null;
		if (type.contains("com.cookbook.tutorial.service.Recipe")) {
			input = m.convertValue(entity, Recipe.class);
		} else if (type.contains("com.cookbook.tutorial.service.Ingredient")) {
			input = m.convertValue(entity, Ingredient.class);
		} else {
			throw new InvalidEntityException("Don't know how to handle type:"
					+ type);
		}
		return m.convertValue(getClient().update(input), Map.class);
	}

	/**
	 * Description for get
	 *
	 * {@sample.xml ../../../doc/cook-book-connector.xml.sample cook-book:get}
	 *
	 * @param id
	 *            Id of the entity to retrieve
	 * @return return Ingredient with Id from the system.
	 * 
	 * @throws SessionExpiredException
	 * @throws InvalidEntityException
	 * @throws NoSuchEntityException
	 */
	@SuppressWarnings("unchecked")
	@Processor
	@OnException(handler = CookBookHandler.class)
	@ReconnectOn(exceptions = { SessionExpiredException.class })
	public Map<String, Object> get(
			@MetaDataKeyParam(affects = MetaDataKeyParamAffectsType.OUTPUT) String type,
			@Default("1") Integer id) throws InvalidEntityException,
			SessionExpiredException, NoSuchEntityException {
		ObjectMapper m = new ObjectMapper();
		return m.convertValue(getClient().get(id), Map.class);
	}

	/**
	 * Description for get
	 *
	 * {@sample.xml ../../../doc/cook-book-connector.xml.sample cook-book:get}
	 *
	 * @param id
	 *            Id of the entity to retrieve
	 * @return return Ingredient with Id from the system.
	 * 
	 * @throws SessionExpiredException
	 * @throws NoSuchEntityException
	 */
	@Processor
	@OnException(handler = CookBookHandler.class)
	@ReconnectOn(exceptions = { SessionExpiredException.class })
	public void delete(@Default("1") Integer id) throws NoSuchEntityException,
			SessionExpiredException {
		client.delete(id);
	}

	
	/**
	 * Description for queryPaginated
	 *
	 * {@sample.xml ../../../doc/cook-book-connector.xml.sample cook-book:query-paginated}
	 *
	 *	@param query The query
	 *	@param pagingConfiguration the paging configuration
	 *	@return return comment
	 */
	@Processor
	@Paged
	public ProviderAwarePagingDelegate<Map<String, Object>, CookBookConnector> queryPaginated(
			final String query, final PagingConfiguration pagingConfiguration) {
		return new ProviderAwarePagingDelegate<Map<String, Object>, CookBookConnector>() {
			private Integer currentPage = 0;
			ObjectMapper m = new ObjectMapper();

			@Override
			public void close() throws MuleException {

			}

			@Override
			public List<Map<String, Object>> getPage(CookBookConnector connector)
					throws Exception {

				List<CookBookEntity> list = connector.getClient()
						.searchWithQuery(query, ++currentPage,
								pagingConfiguration.getFetchSize());
				List<Map<String, Object>> result = m.convertValue(list,
						new TypeReference<List<Map<String, Object>>>() {
						});
				return result;
			}

			@Override
			public int getTotalResults(CookBookConnector connector)
					throws Exception {
				return 0;
			}

		};
	}
	
	@Transformer(sourceTypes = { List.class })
	public static List<Map<String, Object>> recipesToMaps(
			List<Recipe> list) {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> result = mapper.convertValue(list,
				new TypeReference<List<Map<String, Object>>>() {
				});
		return result;
	}

	public ConnectorConnectionStrategy getConnectionStrategy() {
		return connectionStrategy;
	}

	public void setConnectionStrategy(
			ConnectorConnectionStrategy connectionStrategy) {
		this.connectionStrategy = connectionStrategy;
	}

	private MuleCookBookClient getClient() {
		return connectionStrategy.getClient();
	}
}