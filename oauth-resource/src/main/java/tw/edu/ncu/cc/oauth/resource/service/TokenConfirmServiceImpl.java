package tw.edu.ncu.cc.oauth.resource.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject;
import tw.edu.ncu.cc.oauth.resource.config.RemoteConfig;

public class TokenConfirmServiceImpl implements TokenConfirmService {

    private RemoteConfig config;
    private RestTemplate restTemplate = new RestTemplate();

    public void setConfig( RemoteConfig config ) {
        this.config = config;
    }

    @Override
    public AccessTokenObject readToken( String accessToken ) {
        String remoteAddr = config.getAddrPrefix() + accessToken + config.getAddrSuffix();
        try {
            return restTemplate.getForEntity( remoteAddr, AccessTokenObject.class ).getBody();
        } catch ( HttpClientErrorException e ) {
            if( e.getStatusCode().equals( HttpStatus.NOT_FOUND ) ) {
                return null;
            } else {
                throw e;
            }
        }
    }

}
