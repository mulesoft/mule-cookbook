/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cookbook;

import com.cookbook.tutorial.client.ICookbookCallback;
import com.cookbook.tutorial.service.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mule.api.annotations.*;
import org.mule.api.annotations.lifecycle.OnException;
import org.mule.api.annotations.oauth.OAuthProtected;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.MetaDataKeyParam;
import org.mule.api.annotations.param.MetaDataKeyParamAffectsType;
import org.mule.api.annotations.param.RefOnly;
import org.mule.api.callback.SourceCallback;
import org.mule.modules.cookbook.config.AbstractConfig;
import org.mule.modules.cookbook.datasense.DataSenseResolver;
import org.mule.modules.cookbook.handler.CookbookHandler;
import org.mule.modules.cookbook.pagination.CookbookPagingDelegate;
import org.mule.streaming.PagingConfiguration;
import org.mule.streaming.ProviderAwarePagingDelegate;

import java.util.List;
import java.util.Map;

/**
 * Anypoint Cookbook Connector
 *
 * @author MuleSoft, Inc.
 */
@ReconnectOn(exceptions = {SessionExpiredException.class})
@Connector(name = "cookbook", friendlyName = "Cookbook")
@MetaDataScope(DataSenseResolver.class)
public class CookbookConnector {

    @Config
    AbstractConfig config;

    /**
     * Returns the list of recently added recipes
     * <p/>
     * {@sample.xml ../../../doc/cookbook-connector.xml.sample cookbook:get-recently-added}
     *
     * @return A list of the recently added recipes
     */
    @OAuthProtected
    @Processor
    public List<Recipe> getRecentlyAdded() {
        return config.getClient().getRecentlyAdded();
    }

    /**
     * Constantly polls the system to return the recently added recipes.
     * <p/>
     * {@sample.xml ../../../doc/cookbook-connector.xml.sample cookbook:get-recently-added-source}
     *
     * @param callback The callback that will hook the result into mule event.
     * @throws Exception When the source fails.
     */
    @OAuthProtected
    @Source(sourceStrategy = SourceStrategy.POLLING, pollingPeriod = 10000)
    public void getRecentlyAddedSource(final SourceCallback callback) throws Exception {

        if (this.getConfig().getClient() != null) {
            // Every 5 seconds our callback will be executed
            this.getConfig().getClient().getRecentlyAdded(new ICookbookCallback() {

                public void execute(List<Recipe> recipes) throws Exception {
                    callback.process(recipes);
                }
            });

            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        }
    }

    /**
     * Create Recipes and Ingredients
     * <p/>
     * {@sample.xml ../../../doc/cookbook-connector.xml.sample cookbook:create}
     *
     * @param type   Type of the entity
     * @param entity Ingredient/Recipe to be created
     * @return return Id of the entity from the system.
     * @throws SessionExpiredException Exception thrown when an action is taken by a client who's session has expired.
     * @throws InvalidEntityException  Exception thrown when an wrong entity or type is mapped to the entity parameter.
     */
    @OAuthProtected
    @Processor
    @OnException(handler = CookbookHandler.class)
    public Map<String, Object> create(@MetaDataKeyParam(affects = MetaDataKeyParamAffectsType.BOTH) String type, @Default("#[payload]") @RefOnly Map<String, Object> entity)
            throws InvalidEntityException, SessionExpiredException {
        ObjectMapper m = new ObjectMapper();
        CookBookEntity input = null;
        if (type.contains("com.cookbook.tutorial.service.Recipe")) {
            input = m.convertValue(entity, Recipe.class);
        } else if (type.contains("com.cookbook.tutorial.service.Ingredient")) {
            input = m.convertValue(entity, Ingredient.class);
        } else {
            throw new InvalidEntityException("Don't know how to handle type:" + type);
        }
        return m.convertValue(this.getConfig().getClient().create(input), Map.class);
    }

    /**
     * Update the existing entities (Recipes/Ingredients)
     * <p/>
     * {@sample.xml ../../../doc/cookbook-connector.xml.sample cookbook:update}
     *
     * @param type   Type of the entity
     * @param entity Entity to be updated
     * @return return Entity with Id from the system.
     * @throws SessionExpiredException Exception thrown when an action is taken by a client who's session has expired.
     * @throws InvalidEntityException  Exception thrown when an wrong entity or type is mapped to the entity parameter.
     * @throws NoSuchEntityException   Exception thrown when the specified entity does not exist in the system.
     */
    @OAuthProtected
    @Processor
    @OnException(handler = CookbookHandler.class)
    public Map<String, Object> update(@MetaDataKeyParam(affects = MetaDataKeyParamAffectsType.BOTH) String type, @Default("#[payload]") @RefOnly Map<String, Object> entity)
            throws InvalidEntityException, SessionExpiredException, NoSuchEntityException {
        ObjectMapper m = new ObjectMapper();
        CookBookEntity input = null;
        if (type.contains("com.cookbook.tutorial.service.Recipe")) {
            input = m.convertValue(entity, Recipe.class);
        } else if (type.contains("com.cookbook.tutorial.service.Ingredient")) {
            input = m.convertValue(entity, Ingredient.class);
        } else {
            throw new InvalidEntityException("Don't know how to handle type:" + type);
        }
        return m.convertValue(this.getConfig().getClient().update(input), Map.class);
    }

    /**
     * Retrieve the entity from the system based on the identifier.
     * <p/>
     * {@sample.xml ../../../doc/cookbook-connector.xml.sample cookbook:get}
     *
     * @param type Type of the entity
     * @param id   Id of the entity to retrieve
     * @return return Ingredient with Id from the system.
     * @throws SessionExpiredException Exception thrown when an action is taken by a client who's session has expired.
     * @throws InvalidEntityException  Exception thrown when an wrong entity or type is mapped to the entity parameter.
     * @throws NoSuchEntityException   Exception thrown when the specified entity does not exist in the system.
     */
    @OAuthProtected
    @Processor
    @OnException(handler = CookbookHandler.class)
    public Map<String, Object> get(@MetaDataKeyParam(affects = MetaDataKeyParamAffectsType.OUTPUT) String type, @Default("1") Integer id) throws InvalidEntityException,
            SessionExpiredException, NoSuchEntityException {
        ObjectMapper m = new ObjectMapper();
        return m.convertValue(this.getConfig().getClient().get(id), Map.class);
    }

    /**
     * Performs delete operation, based on the id corresponding entity is delete from system.
     * <p/>
     * {@sample.xml ../../../doc/cookbook-connector.xml.sample cookbook:delete}
     *
     * @param id Id of the entity to delete
     * @throws SessionExpiredException Exception thrown when an action is taken by a client who's session has expired.
     * @throws NoSuchEntityException   Exception thrown when the specified entity does not exist in the system.
     */
    @OAuthProtected
    @Processor
    @OnException(handler = CookbookHandler.class)
    public void delete(@Default("1") Integer id) throws NoSuchEntityException, SessionExpiredException {
        this.getConfig().getClient().delete(id);
    }

    /**
     * ResultSet retrieved based on the query.
     * <p/>
     * {@sample.xml ../../../doc/cookbook-connector.xml.sample cookbook:query-paginated}
     *
     * @param query               The query
     * @param pagingConfiguration the paging configuration
     * @return return comment
     * @throws SessionExpiredException Exception thrown when an action is taken by a client who's session has expired.
     */
    @OAuthProtected
    @Processor
    @Paged
    public ProviderAwarePagingDelegate<Map<String, Object>, CookbookConnector> queryPaginated(
            final String query, final PagingConfiguration pagingConfiguration)
            throws SessionExpiredException {
        return new CookbookPagingDelegate(query, pagingConfiguration.getFetchSize());
    }

    /**
     * Basic object mapper that converts list of Recipe entities to List of Map<String, Object> objects.
     *
     * @param list provide a list of Recipes to be mapped
     * @return List of Map containing key/value pairs of the Recipe entity.
     */
    @Transformer(sourceTypes = {List.class})
    public static List<Map<String, Object>> recipesToMaps(List<Recipe> list) {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> result = mapper.convertValue(list, new TypeReference<List<Map<String, Object>>>() {
        });
        return result;
    }

    /**
     * Basic object mapper that converts a Recipe entities to a Map<String, Object> object.
     *
     * @param recipe provide a Recipe to be mapped
     * @return Map containing key/value pairs of the Recipe Object.
     */
    @Transformer(sourceTypes = {Recipe.class})
    public static Map<String, Object> recipeToMap(Recipe recipe) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = mapper.convertValue(recipe, new TypeReference<Map<String, Object>>() {
        });
        return result;
    }

    public AbstractConfig getConfig() {
        return config;
    }

    public void setConfig(AbstractConfig config) {
        this.config = config;
    }

}