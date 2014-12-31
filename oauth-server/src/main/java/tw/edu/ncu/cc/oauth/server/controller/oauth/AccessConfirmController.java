package tw.edu.ncu.cc.oauth.server.controller.oauth;

import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.helper.OAuthRedirectBuilder;
import tw.edu.ncu.cc.oauth.server.model.AccessConfirmModel;
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URISyntaxException;
import java.util.Set;

@Controller
@SessionAttributes( "access_confirm" )
public final class AccessConfirmController {

    private AuthCodeService authCodeService;

    @Autowired
    public void setAuthCodeService( AuthCodeService authCodeService ) {
        this.authCodeService = authCodeService;
    }

    @RequestMapping( value = "oauth/confirm", method = RequestMethod.POST )
    public String confirm( @ModelAttribute( "access_confirm" ) AccessConfirmModel confirmEntity,
                           @RequestParam( "approval" ) boolean isAgree,
                           HttpServletRequest request,
                           SessionStatus sessionStatus ) throws URISyntaxException, OAuthSystemException {

        ClientEntity client = confirmEntity.getClient();
        Set< String > scope = confirmEntity.getScope();
        String userID = confirmEntity.getUserID();

        invalidateSession( request, sessionStatus );

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

    private void invalidateSession( HttpServletRequest request, SessionStatus sessionStatus ) {
        sessionStatus.setComplete();
        HttpSession session = request.getSession( true );
        session.invalidate();
        request.getSession( true );
    }

}