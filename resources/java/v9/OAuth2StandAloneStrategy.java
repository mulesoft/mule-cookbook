package org.mule.modules.cookbook.strategy;

import com.cookbook.tutorial.client.MuleCookBookClient;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.oauth.*;
import org.mule.api.annotations.param.Default;

/**
 * Created by Mulesoft.
 */
@OAuth2(friendlyName = "OAuth2 Strategy",
        authorizationUrl = "http://localhost:9091/oauth/authorize",
        accessTokenUrl = "http://localhost:9091/oauth/accessToken")
public class OAuthStrategy {

    public MuleCookBookClient getClient() {
        return client;
    }

    public void setClient(MuleCookBookClient client) {
        this.client = client;
    }

    private MuleCookBookClient client;

    /**
     * URL used to connect to the service
     */
    @Configurable
    @Default("http://localhost:9090/cook-book")
    private String endpoint;

    @OAuthAccessToken
    private String accessToken;

    @Configurable
    @OAuthConsumerKey
    private String consumerKey;

    @Configurable
    @OAuthConsumerSecret
    private String consumerSecret;

    @OAuthPostAuthorization
    public void postAuthorize() {
        setClient(new MuleCookBookClient(getEndpoint()));
        getClient().setToken(getAccessToken());
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerKey() {
        return this.consumerKey;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getConsumerSecret() {
        return this.consumerSecret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
