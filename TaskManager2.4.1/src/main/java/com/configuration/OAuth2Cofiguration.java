package com.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * This is the configuration of OAuth2.
 * This is a resource server.
 * @version 1.0
 */
@Configuration
@EnableResourceServer
public class OAuth2Cofiguration extends ResourceServerConfigurerAdapter {
    private final RemoteTokenServices tokenServices = new RemoteTokenServices();
    /**
     * The address to connect to the remote authentication server.
     */
    private final String authServerAddress = "http://localhost:55555/oauth/check_token";
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests().anyRequest().permitAll();
    }
    //@Override
    @Bean
    public AccessTokenConverter accessTokenConverter(){
        return new DefaultAccessTokenConverter();
    }

    /**
     * Checking a token at a remote authentication server.
     * @return User data if the token is valid. Otherwise an error message.
     */
    @Primary
    @Bean
    public RemoteTokenServices tokenServices() {
        tokenServices.setCheckTokenEndpointUrl(authServerAddress);
        tokenServices.setClientId("client");
        tokenServices.setClientSecret("secret");
        tokenServices.setAccessTokenConverter(accessTokenConverter());
        return tokenServices;

    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("resource");
    }
}
