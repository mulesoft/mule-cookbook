/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.cookbook;

import java.util.List;

import org.mule.api.annotations.ConnectionStrategy;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.ReconnectOn;
import org.mule.api.annotations.lifecycle.OnException;
import org.mule.api.annotations.param.Default;
import org.mule.modules.cookbook.handler.CookBookHandler;
import org.mule.modules.cookbook.strategy.ConnectorConnectionStrategy;

import com.cookbook.tutorial.client.MuleCookBookClient;
import com.cookbook.tutorial.service.InvalidEntityException;
import com.cookbook.tutorial.service.InvalidTokenException;
import com.cookbook.tutorial.service.NoSuchEntityException;
import com.cookbook.tutorial.service.Ingredient;
import com.cookbook.tutorial.service.Recipe;
import com.cookbook.tutorial.service.SessionExpiredException;

/**
 * Anypoint Connector
 *
 * @author MuleSoft, Inc.
 */
@Connector(name = "cook-book", friendlyName = "CookBook")
public class CookBookConnector {

    private MuleCookBookClient client;

    @ConnectionStrategy
    ConnectorConnectionStrategy connectionStrategy;
    
    /**
     * Get a list of the most recently added Ingredients
     *
     * {@sample.xml ../../../doc/cook-book-connector.xml.sample cook-book:getRecentlyAdded}
     * 
     * @return List of recently added Ingredients.
     *
     */
    @Processor
    public List<Recipe> getRecentlyAdded() {
        return client.getRecentlyAdded();
    }

    
	/**
	 * Description for createIngredient
	 *
	 * {@sample.xml ../../../doc/cook-book-connector.xml.sample cook-book:createIngredient}
	 *
	 * @param Ingredient Ingredient to be created
	 * @return return Ingredient with Id from the system.
	 * 
	 * @throws InvalidTokenException 
	 * @throws SessionExpiredException 
	 * @throws InvalidEntityException 
	 */
	@Processor
	@OnException (handler=CookBookHandler.class)
	public Ingredient createIngredient(@Default("#[payload]") Ingredient Ingredient) throws InvalidEntityException, SessionExpiredException {
		return (Ingredient) client.create(Ingredient);
	}

	/**
	 * Description for createIngredient
	 *
	 * {@sample.xml ../../../doc/cook-book-connector.xml.sample cook-book:createIngredient}
	 *
	 * @param Ingredient Ingredient to be retrieved
	 * @return return Ingredient with Id from the system.
	 *
	 * @throws SessionExpiredException
	 * @throws InvalidEntityException
	 * @throws NoSuchEntityException
	 */
	@Processor
	@ReconnectOn(exceptions = { SessionExpiredException.class })
	public Ingredient retriveIngredient(@Default("1") int id) throws SessionExpiredException, NoSuchEntityException {
		return (Ingredient) client.get(id);
	}

	/**
	 * Description for createIngredient
	 *
	 * {@sample.xml ../../../doc/cook-book-connector.xml.sample cook-book:createIngredient}
	 *
	 * @param ingredient Ingredient to be created
	 * @return return Ingredient with Id from the system.
	 *
	 * @throws InvalidTokenException
	 * @throws SessionExpiredException
	 * @throws InvalidEntityException
	 * @throws NoSuchEntityException
	 */
	@Processor
	public Ingredient updateIngredient(@Default("#[payload]") Ingredient ingredient) throws InvalidEntityException, SessionExpiredException, NoSuchEntityException {
		return (Ingredient) client.update(ingredient);
	}

	/**
	 * Delete a cook book entity with the provided Id.
	 *
	 * {@sample.xml ../../../doc/cook-book-connector.xml.sample cook-book:delete}
	 *
	 * @throws InvalidTokenException
	 * @throws SessionExpiredException
	 * @throws NoSuchEntityException
	 */
	@Processor
	@ReconnectOn(exceptions = { SessionExpiredException.class })
	public void delete(@Default("1") int id) throws NoSuchEntityException, SessionExpiredException {
		client.delete(id);
	}
	
    public ConnectorConnectionStrategy getConnectionStrategy() {
        return connectionStrategy;
    }

    public void setConnectionStrategy(ConnectorConnectionStrategy connectionStrategy) {
        this.connectionStrategy = connectionStrategy;
        client = connectionStrategy.getClient();
    }

}