package com.ipostu.demo.spring.jar13demo.security;

import com.ipostu.demo.spring.jar13demo.services.PersonDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final PersonDetailsServiceImpl personDetailsService;

    public AuthProviderImpl(PersonDetailsServiceImpl personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails userDetails = personDetailsService.loadUserByUsername(username);

        String incomingPassword = authentication.getCredentials().toString();
        if (!Objects.equals(userDetails.getPassword(), incomingPassword)) {
            throw new BadCredentialsException("Incorrect password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, incomingPassword,
                List.of());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
