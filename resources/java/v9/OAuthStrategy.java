package org.mule.modules.cookbook.strategy;

import com.cookbook.tutorial.client.MuleCookBookClient;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.oauth.*;

/**
 * Created by Mulesoft.
 */
@OAuth2(friendlyName = "OAuth2 Strategy",
        authorizationUrl = "http://localhost:9091/oauth/authorize",
        accessTokenUrl = "http://localhost:9091/oauth/accessToken")
public class OAuthStrategy extends ConnectorConnectionStrategy{

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
        MuleCookBookClient client=new MuleCookBookClient(getEndpoint());
        client.setToken(this.getAccessToken());
        setClient(client);
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

}
