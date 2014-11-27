package tw.edu.ncu.cc.oauth.server.controller.oauth;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.model.AccessConfirmModel;
import tw.edu.ncu.cc.oauth.server.service.AuthCodeBuilder;

import java.net.URISyntaxException;
import java.util.Set;

@Controller
@SessionAttributes( "access_confirm" )
public final class AccessConfirmController {

    private AuthCodeBuilder authCodeBuilder;

    @Autowired
    public void setAuthCodeBuilder( AuthCodeBuilder authCodeBuilder ) {
        this.authCodeBuilder = authCodeBuilder;
    }

    @RequestMapping( value = "oauth/confirm", method = RequestMethod.POST )
    public String confirm( @ModelAttribute( "access_confirm" ) AccessConfirmModel confirmEntity ) throws URISyntaxException, OAuthSystemException {

        ClientEntity client = confirmEntity.getClient();
        Set< String > scope = confirmEntity.getScope();
        String userID = confirmEntity.getUserID();

        AuthCodeEntity authCode = authCodeBuilder.buildAuthCode( client.getId(), userID, scope );

        return "redirect:" + String.format(
                "%s?code=%s&state=%s",
                client.getCallback(),
                authCode.getCode(),
                confirmEntity.getState()
        ) ;

    }

}