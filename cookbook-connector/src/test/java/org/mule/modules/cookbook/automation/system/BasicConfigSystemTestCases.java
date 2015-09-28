package org.mule.modules.cookbook.automation.system;

import org.junit.Before;
import org.junit.Test;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.modules.cookbook.config.BasicConfig;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BasicConfigSystemTestCases {

    private Properties validCredentials;
    private String address;
    private String username;
    private String password;
    private BasicConfig basicConfig;

    @Before
    public void setUp() throws Exception {
        validCredentials = ConfigurationUtils.getAutomationCredentialsProperties();
        address = validCredentials.getProperty("config.address");
        username = validCredentials.getProperty("config.username");
        password = validCredentials.getProperty("config.password");
        basicConfig = new BasicConfig();
        basicConfig.setAddress(address);
    }

    @Test
    public void validCredentialsConnectivityTest() {
        try {
            basicConfig.connect(username, password);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidCredentialsConnectivityTest() {
        try {
            basicConfig.connect("noUsername", "noPassword");
        } catch (ConnectionException ce) {
            assertEquals(ConnectionExceptionCode.INCORRECT_CREDENTIALS, ce.getCode());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void nullCredentialsConnectivityTest() {
        try {
            basicConfig.connect(null, null);
        } catch (ConnectionException ce) {
            assertEquals(ConnectionExceptionCode.INCORRECT_CREDENTIALS, ce.getCode());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
