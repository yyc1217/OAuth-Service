package tw.edu.ncu.cc.oauth.server.controller.oauth;


import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.entity.UserEntity;
import tw.edu.ncu.cc.oauth.server.model.AccessConfirmModel;
import tw.edu.ncu.cc.oauth.server.repository.AuthCodeRepository;
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository;
import tw.edu.ncu.cc.oauth.server.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.net.URISyntaxException;
import java.util.Set;

@Controller
@SessionAttributes( "access_confirm" )
public final class AccessConfirmController {

    private UserRepository userRepository;
    private ClientRepository clientRepository;
    private AuthCodeRepository authCodeRepository;

    private OAuthIssuer tokenIssuer;

    @RequestMapping( value = "oauth/confirm", method = RequestMethod.POST )
    public Response confirm( @ModelAttribute( "access_confirm" ) AccessConfirmModel confirmEntity,
                             HttpServletRequest request ) throws URISyntaxException, OAuthSystemException {

//        AuthBean authBean = new AuthBean( session );
//        AuthBean authBean = null;

//        return prepareResponse( authBean, request );
        return null;
    }

//    private Response prepareResponse( AuthBean authBean, HttpServletRequest request ) throws URISyntaxException, OAuthSystemException  {
//
//        String clientID = authBean.getClientID();
//        String portalID = authBean.getPortalID();
//        Set<String> scopes = authBean.getScope();
//
//        OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
//                OAuthASResponse.authorizationResponse( request, HttpServletResponse.SC_FOUND );
//
//        ClientEntity client = clientRepository.getClient( Integer.parseInt( clientID ) );
//
//        builder.setCode ( prepareAuthCode( client, portalID, scopes ) );
//        builder.location( prepareRedirect( client ) );
//
//        OAuthResponse response = builder.buildQueryMessage();
//
//        return Response
//                .status( response.getResponseStatus() )
//                .location( new URI( response.getLocationUri() ) )
//                .build();
//    }

    private String prepareAuthCode( ClientEntity client, String portalID, Set<String> scopes ) throws OAuthSystemException{

        UserEntity user = userRepository.getUser( portalID );

        AuthCodeEntity authCode = new AuthCodeEntity();
        authCode.setClient( client );
        authCode.setUser( user );
        authCode.setCode( tokenIssuer.authorizationCode() );
//        authCode.setPermissions( PermissionUtil.valueOf( scopes ) );
        authCodeRepository.persistAuthCode( authCode );

        return authCode.getCode();
    }

    private String prepareRedirect( ClientEntity client ) {
        return client.getCallback();
    }

}