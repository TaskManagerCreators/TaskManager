package com.secutiry.services;


import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

//@Component
public class TokenAuthService {
    private static final String AUTH_HEADER = "X-Auth-Token";

    private TokenHandler tokenHandler;
    private UserService userService;

    public Optional<Authentication> getAuthentication(HttpServletRequest request) {
        return null;
    }

    //@Autowired
    public void setTokenHandler(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    //@Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
