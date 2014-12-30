package tw.edu.ncu.cc.oauth.server.controller.oauth;

import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.model.AccessConfirmModel;
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService;

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
                           HttpSession session ) throws URISyntaxException, OAuthSystemException {

        ClientEntity client = confirmEntity.getClient();
        Set< String > scope = confirmEntity.getScope();
        String userID = confirmEntity.getUserID();

        session.invalidate();

        if( isAgree ) {
            AuthCodeEntity authCode = authCodeService.createAuthCode( client.getId() + "", userID, scope );
            return "redirect:" + String.format(
                    "%s?code=%s&state=%s",
                    client.getCallback(),
                    authCode.getCode(),
                    confirmEntity.getState()
            ) ;
        } else {
            return "redirect:" + String.format(
                    "%s?error=%s&state=%s",
                    client.getCallback(),
                    OAuthError.CodeResponse.ACCESS_DENIED,
                    confirmEntity.getState()
            );
        }
    }

}