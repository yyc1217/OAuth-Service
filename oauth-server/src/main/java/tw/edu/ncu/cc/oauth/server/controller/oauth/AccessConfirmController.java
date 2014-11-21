package tw.edu.ncu.cc.oauth.server.controller.oauth;


import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tw.edu.ncu.cc.oauth.data.PermissionUtil;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.entity.UserEntity;
import tw.edu.ncu.cc.oauth.server.repository.AuthCodeRepository;
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository;
import tw.edu.ncu.cc.oauth.server.repository.UserRepository;
import tw.edu.ncu.cc.oauth.server.view.AuthBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.net.URISyntaxException;
import java.util.Set;

@Controller
public final class AccessConfirmController {


    private UserRepository userRepository;
    private ClientRepository clientRepository;
    private AuthCodeRepository authCodeRepository;

    private OAuthIssuer tokenIssuer;

    @RequestMapping( value = "oauth/confirm", method = RequestMethod.POST )
    public Response confirm( HttpServletRequest request ) throws URISyntaxException, OAuthSystemException {

//        AuthBean authBean = new AuthBean( session );
        AuthBean authBean = null;
        //TODO NEED TO CONFIRM CSRF TOKEN OF FORM
        validateData( authBean );

        return prepareResponse( authBean, request );
    }

    private void validateData( AuthBean authBean ) {
        try{
            authBean.getScope();
            authBean.getPortalID();
            authBean.getClientID();
        } catch ( NullPointerException ignore ) {
            throw new RuntimeException( "DATA FETCH ERROR" );
        }
        if( clientRepository.getClient( Integer.parseInt( authBean.getClientID() ) ) == null ) {
            throw new RuntimeException( "CLIENT NOT EXISTS" );
        }
    }

    private Response prepareResponse( AuthBean authBean, HttpServletRequest request ) throws URISyntaxException, OAuthSystemException  {

        String clientID = authBean.getClientID();
        String portalID = authBean.getPortalID();
        Set<String> scopes = authBean.getScope();
        authBean.destroy();

        OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                OAuthASResponse.authorizationResponse( request, HttpServletResponse.SC_FOUND );

        ClientEntity client = clientRepository.getClient( Integer.parseInt( clientID ) );

        builder.setCode ( prepareAuthCode( client, portalID, scopes ) );
        builder.location( prepareRedirect( client ) );

        OAuthResponse response = builder.buildQueryMessage();

//        return Response
//                .status( response.getResponseStatus() )
//                .location( new URI( response.getLocationUri() ) )
//                .build();
        return null;
    }

    private String prepareAuthCode( ClientEntity client, String portalID, Set<String> scopes ) throws OAuthSystemException{

        UserEntity user = userRepository.getUser( portalID );

        AuthCodeEntity authCode = new AuthCodeEntity();
        authCode.setClient( client );
        authCode.setUser( user );
        authCode.setCode( tokenIssuer.authorizationCode() );
        authCode.setPermissions( PermissionUtil.valueOf( scopes ) );
        authCodeRepository.persistAuthCode( authCode );

        return authCode.getCode();
    }

    private String prepareRedirect( ClientEntity client ) {
        return client.getCallback();
    }

}