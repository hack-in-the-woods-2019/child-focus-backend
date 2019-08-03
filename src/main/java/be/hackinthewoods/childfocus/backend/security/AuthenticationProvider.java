package be.hackinthewoods.childfocus.backend.security;

import be.hackinthewoods.childfocus.backend.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final UserService userService;

    public AuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        // Nothin'
    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        Object credentials = usernamePasswordAuthenticationToken.getCredentials();
        return Optional.ofNullable(credentials)
          .map(String::valueOf)
          .flatMap(userService::findByToken)
          .orElseThrow(() -> new UsernameNotFoundException("Can't find user with authentication credentials: " + credentials));
    }
}
