package tw.edu.ncu.cc.oauth.server.controller.oauth;

import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.model.AccessConfirmModel;
import tw.edu.ncu.cc.oauth.server.service.AuthCodeAPIService;

import java.net.URISyntaxException;
import java.util.Set;

@Controller
@SessionAttributes( "access_confirm" )
public final class AccessConfirmController {

    private AuthCodeAPIService authCodeAPIService;

    @Autowired
    public void setAuthCodeAPIService( AuthCodeAPIService authCodeAPIService ) {
        this.authCodeAPIService = authCodeAPIService;
    }

    @RequestMapping( value = "oauth/confirm", method = RequestMethod.POST )
    public String confirm( @ModelAttribute( "access_confirm" ) AccessConfirmModel confirmEntity,
                           @RequestParam( "approval" ) boolean isAgree ) throws URISyntaxException, OAuthSystemException {

        ClientEntity client = confirmEntity.getClient();
        Set< String > scope = confirmEntity.getScope();
        String userID = confirmEntity.getUserID();

        if( isAgree ) {
            AuthCodeEntity authCode = authCodeAPIService.createAuthCode( client.getId(), userID, scope );
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