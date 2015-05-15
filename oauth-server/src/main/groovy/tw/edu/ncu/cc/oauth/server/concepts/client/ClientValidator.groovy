package tw.edu.ncu.cc.oauth.server.concepts.client

import org.springframework.util.StringUtils
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import tw.edu.ncu.cc.oauth.data.v1.management.client.ClientObject

public class ClientValidator implements Validator {

    @Override
    public boolean supports( Class< ? > clazz ) {
        return ClientObject.class.equals( clazz )
    }

    @Override
    public void validate( Object target, Errors errors ) {
        ClientObject clientObject = ( ClientObject ) target
        if( StringUtils.isEmpty( clientObject.getName() ) ) {
            errors.rejectValue( "name", "name.necessary", "name is necessary" )
        }
        if( StringUtils.isEmpty( clientObject.getCallback() ) ) {
            errors.rejectValue( "callback", "callback.necessary", "callback is necessary" )
        }
        if(  StringUtils.isEmpty( clientObject.getOwner() ) ) {
            errors.rejectValue( "owner", "owner.necessary", "owner is necessary" )
        }
    }

}
