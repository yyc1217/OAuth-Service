package tw.edu.ncu.cc.oauth.server.controller.oauth;

import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.model.AccessConfirmModel;
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService;
import tw.edu.ncu.cc.oauth.server.service.ClientService;
import tw.edu.ncu.cc.oauth.server.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.net.URISyntaxException;
import java.util.Set;

@Controller
@SessionAttributes( "access_confirm" )
public final class AccessConfirmController {

    private UserService userService;
    private ClientService clientService;
    private AuthCodeService authCodeService;

    @RequestMapping( value = "oauth/confirm", method = RequestMethod.POST )
    public Response confirm( @ModelAttribute( "access_confirm" ) AccessConfirmModel confirmEntity,
                             HttpServletRequest request ) throws URISyntaxException, OAuthSystemException {

        ClientEntity client = confirmEntity.getClient();
        Set<String> scope = confirmEntity.getScope();
        String userID = confirmEntity.getUserID();

        OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                OAuthASResponse.authorizationResponse( request, HttpServletResponse.SC_FOUND );

//        builder.setCode ( prepareAuthCode( client, userID, scope ) );
        builder.location( client.getCallback() );

        OAuthResponse response = builder.buildQueryMessage();

//        return Response
//                .status( response.getResponseStatus() )
//                .location( new URI( response.getLocationUri() ) )
//                .build();

        return null;
    }

//    private String prepareAuthCode( ClientEntity client, String userID, Set<String> scopes ) throws OAuthSystemException{
//
//        UserEntity user = userService.getUser( userID );
//
//        AuthCodeEntity authCode = new AuthCodeEntity();
//        authCode.setClient( client );
//        authCode.setUser( user );
//        authCode.setCode( tokenIssuer.authorizationCode() );
//        authCode.setPermissions( PermissionUtil.valueOf( scopes ) );
//        authCodeRepository.persistAuthCode( authCode );
//
//        return authCode.getCode();
//    }

}