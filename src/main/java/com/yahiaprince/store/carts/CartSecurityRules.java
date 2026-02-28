package com.yahiaprince.store.carts;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

import com.yahiaprince.store.common.SecurityRules;
@Component
public class CartSecurityRules implements SecurityRules {

    @Override
    public void configure(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        registry.requestMatchers("/carts/**").permitAll();
    }
}