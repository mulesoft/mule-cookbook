/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.cookbook;

import java.util.List;

import org.mule.api.annotations.ConnectionStrategy;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.lifecycle.Start;
import org.mule.modules.cookbook.strategy.ConnectorConnectionStrategy;

import com.cookbook.tutorial.client.MuleCookBookClient;
import com.cookbook.tutorial.service.Recipe;

/**
 * Anypoint Connector
 *
 * @author MuleSoft, Inc.
 */
@Connector(name="cook-book", friendlyName="CookBook")
public class CookBookConnector {

    @ConnectionStrategy
    ConnectorConnectionStrategy connectionStrategy;

    private MuleCookBookClient client;
    
    @Start
    public void initialize(){
    	client = new MuleCookBookClient(connectionStrategy.getAddress());
    }
    
	/**
	 * Returns the list of recently added recipes
	 *
	 * {@sample.xml ../../../doc/cook-book-connector.xml.sample cook-book:getRecentlyAdded}
	 * 
	 * @return A list of the recently added recipes
	 */
	@Processor
	public List<Recipe> getRecentlyAdded() {
		return client.getRecentlyAdded();
	}

    public ConnectorConnectionStrategy getConnectionStrategy() {
        return connectionStrategy;
    }

    public void setConnectionStrategy(ConnectorConnectionStrategy connectionStrategy) {
        this.connectionStrategy = connectionStrategy;
    }

}