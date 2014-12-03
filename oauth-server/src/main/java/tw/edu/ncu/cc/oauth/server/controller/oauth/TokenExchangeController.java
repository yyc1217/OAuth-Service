package tw.edu.ncu.cc.oauth.server.controller.oauth;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ncu.cc.oauth.server.service.AccessTokenFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public final class TokenExchangeController {

    private AccessTokenFactory accessTokenFactory;

    @Autowired
    public void setAccessTokenFactory( AccessTokenFactory accessTokenFactory ) {
        this.accessTokenFactory = accessTokenFactory;
    }

    @RequestMapping( value = "oauth/token", method = RequestMethod.POST )
    public String authorize( HttpServletRequest servletRequest ) throws OAuthSystemException, OAuthProblemException {

        OAuthTokenRequest request = new OAuthTokenRequest( servletRequest );

        OAuthResponse response = OAuthASResponse
                    .tokenResponse( HttpServletResponse.SC_OK )
                    .setAccessToken( prepareAccessToken( request ) )
                    .buildJSONMessage();

        return response.getBody();
    }

    private String prepareAccessToken( OAuthTokenRequest request ) {
        return accessTokenFactory
                .createAccessToken( request.getCode() )
                .getToken();
    }

}
