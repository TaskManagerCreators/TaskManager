package com.config;

import com.secutiry.services.TokenAuthService;
import com.secutiry.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().loginPage("/login").permitAll()
                .and()
                .requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access","oauth/check_token")
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    private CorsSettingsFilter corsSettingsFilter;

    private UserService userService;

    private TokenAuthService tokenAuthService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.userDetailsService(userService);
    }

    /*@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        configuration.addAllowedOrigin("*");
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/

   // @Autowired
    public void setTokenAuthService(TokenAuthService tokenAuthService) {
        this.tokenAuthService = tokenAuthService;
    }

    @Autowired
    public void setCorsSettingsFilter(CorsSettingsFilter corsSettingsFilter) {
        this.corsSettingsFilter = corsSettingsFilter;
    }
}
