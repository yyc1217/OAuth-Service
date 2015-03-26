package tw.edu.ncu.cc.oauth.server.web.oauth

import org.apache.oltu.oauth2.common.error.OAuthError
import org.apache.oltu.oauth2.common.exception.OAuthSystemException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import tw.edu.ncu.cc.oauth.server.domain.AuthorizationCode
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.Permission
import tw.edu.ncu.cc.oauth.server.helper.OAuthURLBuilder
import tw.edu.ncu.cc.oauth.server.helper.TimeBuilder
import tw.edu.ncu.cc.oauth.server.helper.data.TimeUnit
import tw.edu.ncu.cc.oauth.server.service.domain.AuthorizationCodeService
import tw.edu.ncu.cc.oauth.server.service.domain.PermissionService
import tw.edu.ncu.cc.oauth.server.service.domain.UserService

import javax.servlet.http.HttpServletRequest

@Controller
@SessionAttributes( [ "state", "scope", "client" ] )
public final class AccessConfirmController {

    @Autowired
    def UserService userService

    @Autowired
    def PermissionService permissionService

    @Autowired
    def AuthorizationCodeService authorizationCodeService

    @Value( '${custom.oauth.authCode.expire-seconds}' )
    def long authorizationCodeExpireSeconds

    private Logger logger = LoggerFactory.getLogger( this.getClass() )

    private SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler()

    @RequestMapping( value = "oauth/confirm", method = RequestMethod.POST )
    public String confirm( @ModelAttribute( "state" )  String state,
                           @ModelAttribute( "scope" )  Set< Permission > scope,
                           @ModelAttribute( "client" ) Client client,
                           @RequestParam( "approval" ) boolean isAgree,
                           HttpServletRequest request, Authentication authentication ) throws URISyntaxException, OAuthSystemException {

        logger.info( String.format( "ACCESS CONFIRM, USER: %s, AGREE: %s", authentication.name, isAgree ) )

        logoutHandler.logout( request, null, null );

        if( isAgree ) {

            Date expireDate = TimeBuilder
                    .now()
                    .after( authorizationCodeExpireSeconds, TimeUnit.SECOND )
                    .buildDate();

            AuthorizationCode authCode = authorizationCodeService.create( new AuthorizationCode(
                    client: client,
                    user: userService.readByName( authentication.name ),
                    scope: scope,
                    dateExpired: expireDate
            ) );

            return "redirect:" + OAuthURLBuilder
                    .url( client.getCallback() )
                    .code( authCode.getCode() )
                    .state( state )
                    .build();
        } else {
            return "redirect:" + OAuthURLBuilder
                    .url( client.getCallback() )
                    .error( OAuthError.CodeResponse.ACCESS_DENIED )
                    .state( state )
                    .build();
        }
    }

}