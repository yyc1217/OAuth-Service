package tw.edu.ncu.cc.oauth.server.validator;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tw.edu.ncu.cc.oauth.data.PermissionUtil;
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Component
public class OAuthAuthorizationValidator implements Validator {

    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository( ClientRepository clientRepository ) {
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean supports( Class< ? > clazz ) {
        return clazz.isInstance( HttpServletRequest.class );
    }

    @Override
    public void validate( Object target, Errors errors ) {

        try {

            OAuthAuthzRequest oauthRequest =  new OAuthAuthzRequest( ( HttpServletRequest ) target );
            Set<String> scope   = oauthRequest.getScopes();
            String responseType = oauthRequest.getResponseType();
            String clientState  = oauthRequest.getState();
            String clientID     = oauthRequest.getClientId();

            if ( clientState == null || clientState.equals( "" ) ) {
                errors.rejectValue( "request", "STATE IS NOT PROVIDED" );
            }
            if ( ! responseType.equals( ResponseType.CODE.toString() ) ) {
                errors.rejectValue( "request", "ONLY SUPPORT AUTH CODE" );
            }
            if ( clientRepository.getClient( Integer.parseInt( clientID ) ) == null ) {
                errors.rejectValue( "request", "CLIENT NOT EXISTS" );
            }
            if ( ! PermissionUtil.isAllExist( scope ) ) {
                errors.rejectValue( "request", "PERMISSION NOT EXISTS" );
            }
        } catch ( OAuthProblemException | OAuthSystemException e ) {
            errors.rejectValue( "request", "HEADER FORMAT ERROR" );
        }

    }


}
