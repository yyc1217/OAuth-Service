package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class StubAuthenticationService implements AuthenticationManager {

    @Override
    public Authentication authenticate( Authentication authentication ) throws AuthenticationException {
        throw new UsernameNotFoundException( "stub service" );
    }

}
