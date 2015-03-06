/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.cookbook.strategy;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.param.Default;

/**
 * Configuration type Strategy
 *
 * @author MuleSoft, Inc.
 */
@Configuration(configElementName = "config-type", friendlyName = "Configuration type strategy")
public class ConnectorConnectionStrategy {
	
	/**
	 * Address where the server is running
	 */
	@Configurable
	@Default("http://localhost:9090/cook-book")
	private String address;

	/**
	 * Get Server Address
	 * @return the address configured by the user
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set the address
	 * @param address The url of that will be used to initialize the client.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}