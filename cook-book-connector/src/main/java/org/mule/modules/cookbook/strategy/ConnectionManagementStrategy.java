package org.mule.modules.cookbook.strategy;

import com.cookbook.tutorial.client.MuleCookBookClient;
import com.cookbook.tutorial.service.InvalidCredentialsException;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.*;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.param.ConnectionKey;

/**
 * Created by Mulesoft.
 */
@ConnectionManagement(configElementName = "config-type", friendlyName = "Connection Managament type strategy")
public class ConnectionManagementStrategy extends ConnectorConfig {

    /**
     * Connect
     *
     * @param username
     *            A username
     * @param password
     *            A password
     * @throws org.mule.api.ConnectionException
     */
    @Connect
    @TestConnectivity
    public void connect(@ConnectionKey String username,
            @Password String password) throws ConnectionException {
        setClient(new MuleCookBookClient(getEndpoint()));
        try {
            getClient().login(username, password);
        } catch (InvalidCredentialsException e) {
            throw new ConnectionException(
                    ConnectionExceptionCode.INCORRECT_CREDENTIALS, e.getMessage(),
                    "Invalid credentials");
        }
    }

    /**
     * Disconnect
     */
    @Disconnect
    public void disconnect() {
        setClient(null);
    }

    /**
     * Are we connected
     */
    @ValidateConnection
    public boolean isConnected() {
        return getClient() != null;
    }

    /**
     * Are we connected
     */
    @ConnectionIdentifier
    public String connectionId() {
        return "001";
    }
}
