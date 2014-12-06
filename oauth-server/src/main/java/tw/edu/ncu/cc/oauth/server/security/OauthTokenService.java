package tw.edu.ncu.cc.oauth.server.security;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

public interface OauthTokenService {

    public void validate( OAuthTokenRequest request ) throws OAuthProblemException, OAuthSystemException;

    public String createResponseString( OAuthTokenRequest request ) throws OAuthSystemException;

}
