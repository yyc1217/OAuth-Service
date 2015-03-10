package tw.edu.ncu.cc.oauth.server.validator;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tw.edu.ncu.cc.oauth.data.v1.management.client.Client;

public class ClientValidator implements Validator {

    @Override
    public boolean supports( Class< ? > clazz ) {
        return Client.class.equals( clazz );
    }

    @Override
    public void validate( Object target, Errors errors ) {
        Client client = ( Client ) target;
        if( StringUtils.isEmpty( client.getName() ) ) {
            errors.rejectValue( "name", "name.necessary", "name is necessary" );
        }
        if( StringUtils.isEmpty( client.getCallback() ) ) {
            errors.rejectValue( "callback", "callback.necessary", "callback is necessary" );
        }
        if(  StringUtils.isEmpty( client.getOwner() ) ) {
            errors.rejectValue( "owner", "owner.necessary", "owner is necessary" );
        }
    }

}
