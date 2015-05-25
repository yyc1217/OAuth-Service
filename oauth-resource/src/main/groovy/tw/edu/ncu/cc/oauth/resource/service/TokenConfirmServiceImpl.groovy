package tw.edu.ncu.cc.oauth.resource.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject
import tw.edu.ncu.cc.oauth.data.v1.management.token.ApiTokenObject
import tw.edu.ncu.cc.oauth.resource.config.RemoteConfig

public class TokenConfirmServiceImpl implements TokenConfirmService {

    def RemoteConfig config;

    private RestTemplate restTemplate = new RestTemplate();

    public TokenConfirmServiceImpl( RemoteConfig config ) {
        this.config = config
    }

    @Override
    public AccessTokenObject readAccessToken( String accessToken ) {
        String resourceAddress = config.serverPath + config.accessTokenPath;
        try {
            getTokenWithType( resourceAddress, accessToken, AccessTokenObject.class ).getBody();
        } catch ( HttpClientErrorException e ) {
            if( e.statusCode.equals( HttpStatus.NOT_FOUND ) ) {
                return null;
            } else {
                throw e;
            }
        }
    }

    @Override
    ApiTokenObject readApiToken( String apiToken ) {
        String remoteAddr = config.serverPath + config.apiTokenPath;
        try {
            getTokenWithType( remoteAddr, apiToken, ApiTokenObject.class ).getBody();
        } catch ( HttpClientErrorException e ) {
            if( e.statusCode.equals( HttpStatus.NOT_FOUND ) ) {
                return null;
            } else {
                throw e;
            }
        }
    }

    private <T> ResponseEntity<T> getTokenWithType( String url, String token, Class<T> responseType ) {
        restTemplate.getForEntity( url + "/" + token, responseType )
    }

}
