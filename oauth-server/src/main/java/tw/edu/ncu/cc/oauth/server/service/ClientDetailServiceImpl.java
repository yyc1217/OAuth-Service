package tw.edu.ncu.cc.oauth.server.service;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

public class ClientDetailServiceImpl implements ClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId( String clientId ) throws ClientRegistrationException {
        return null;
    }

}
