package tw.edu.ncu.cc.oauth.server.controller.oauth;

import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tw.edu.ncu.cc.oauth.server.data.OauthAccess;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.helper.OAuthRedirectBuilder;
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.Set;

@Controller
@SessionAttributes( "access_confirm" )
public final class AccessConfirmController {

    private AuthCodeService authCodeService;
    private SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @Autowired
    public void setAuthCodeService( AuthCodeService authCodeService ) {
        this.authCodeService = authCodeService;
    }

    @RequestMapping( value = "oauth/confirm", method = RequestMethod.POST )
    public String confirm( @ModelAttribute( "access_confirm" ) OauthAccess confirmEntity,
                           @RequestParam( "approval" ) boolean isAgree,
                           HttpServletRequest request ) throws URISyntaxException, OAuthSystemException {

        ClientEntity client = confirmEntity.getClient();
        Set< String > scope = confirmEntity.getScope();
        String userID = confirmEntity.getUserID();

        logoutHandler.logout( request, null, null );

        if( isAgree ) {
            AuthCodeEntity authCode = authCodeService.createAuthCode( client.getId() + "", userID, scope );
            return "redirect:" + OAuthRedirectBuilder
                    .callback( client.getCallback() )
                    .code( authCode.getCode() )
                    .state( confirmEntity.getState() )
                    .build();
        } else {
            return "redirect:" + OAuthRedirectBuilder
                    .callback( client.getCallback() )
                    .error( OAuthError.CodeResponse.ACCESS_DENIED )
                    .state( confirmEntity.getState() )
                    .build();
        }
    }

}