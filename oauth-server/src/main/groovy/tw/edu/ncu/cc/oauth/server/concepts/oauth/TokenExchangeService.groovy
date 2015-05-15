package tw.edu.ncu.cc.oauth.server.concepts.oauth

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest
import org.apache.oltu.oauth2.common.exception.OAuthProblemException
import org.apache.oltu.oauth2.common.exception.OAuthSystemException


interface TokenExchangeService {
    public String buildResonseMessage( OAuthTokenRequest request, long expireSeconds ) throws OAuthProblemException, OAuthSystemException;
}