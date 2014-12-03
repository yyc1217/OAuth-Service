package tw.edu.ncu.cc.oauth.server.validator;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tw.edu.ncu.cc.oauth.data.v1.management.application.Application;

public class ApplicationValidator implements Validator {

    @Override
    public boolean supports( Class< ? > clazz ) {
        return Application.class.equals( clazz );
    }

    @Override
    public void validate( Object target, Errors errors ) {
        Application application = ( Application ) target;
        if( StringUtils.isEmpty( application.getName() ) ) {
            errors.rejectValue( "name", "name.necessary", "name is necessary" );
        }
        if( StringUtils.isEmpty( application.getCallback() ) ) {
            errors.rejectValue( "callback", "callback.necessary", "callback is necessary" );
        }
        if(  StringUtils.isEmpty( application.getOwner() ) ) {
            errors.rejectValue( "owner", "owner.necessary", "owner is necessary" );
        }
    }

}
