/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cookbook.automation.system;

import org.junit.Before;
import org.junit.Test;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.modules.cookbook.config.Config;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BasicConfigSystemTestCases {

    private Properties validCredentials;
    private String address;
    private String username;
    private String password;
    private Config config;

    @Before
    public void setUp() throws Exception {
        validCredentials = ConfigurationUtils.getAutomationCredentialsProperties();
        address = validCredentials.getProperty("config.address");
        username = validCredentials.getProperty("config.username");
        password = validCredentials.getProperty("config.password");
        config = new Config();
        config.setAddress(address);
    }

    @Test
    public void validCredentialsConnectivityTest() {
        try {
            config.connect(username, password);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidCredentialsConnectivityTest() {
        try {
            config.connect("noUsername", "noPassword");
        } catch (ConnectionException ce) {
            assertEquals(ConnectionExceptionCode.INCORRECT_CREDENTIALS, ce.getCode());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void nullCredentialsConnectivityTest() {
        try {
            config.connect(null, null);
        } catch (ConnectionException ce) {
            assertEquals(ConnectionExceptionCode.INCORRECT_CREDENTIALS, ce.getCode());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
