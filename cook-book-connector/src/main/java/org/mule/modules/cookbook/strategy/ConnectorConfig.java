/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.cookbook.strategy;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.param.Default;

import com.cookbook.tutorial.client.MuleCookBookClient;

/**
 * Base Configuration
 */

public abstract class ConnectorConfig {

	private MuleCookBookClient client;

	/**
	 * URL used to connect to the service
	 */
	@Configurable
	@Default("http://localhost:9090/cook-book")
	private String endpoint;

	/**
	 * Get endpoint
	 * 
	 * @return endpoint configured by the user.
	 */
	public String getEndpoint() {
		return endpoint;
	}

	/**
	 * Sets the endpoint
	 * 
	 * @param endpoint
	 *            URL client will use to connect.
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	/**
	 * 
	 * @return
	 */
	public MuleCookBookClient getClient() {
		return client;
	}

	/**
	 * 
	 * @param client
	 */
	public void setClient(MuleCookBookClient client) {
		this.client = client;
	}

}