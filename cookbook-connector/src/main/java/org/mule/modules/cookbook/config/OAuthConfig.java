/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cookbook.config;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.oauth.*;

import com.cookbook.tutorial.client.MuleCookBookClient;

/**
 * Authenticates against the service using OAuth 2.0 protocol
 *
 * @author MuleSoft, Inc.
 */
@OAuth2(configElementName = "oauth2", friendlyName = "OAuth 2.0", authorizationUrl = "http://devkit-cookbook.cloudhub.io/rest/oauth/authorize", accessTokenUrl = "http://devkit-cookbook.cloudhub.io/rest/oauth/accessToken")
public class OAuthConfig extends AbstractConfig {

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
        setClient(new MuleCookBookClient(getAddress()));
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

}
