package tw.edu.ncu.cc.oauth.server.service;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

public interface TokenExchangeService {

    public String createToken( OAuthTokenRequest request, long expireSeconds ) throws OAuthProblemException, OAuthSystemException;

}
