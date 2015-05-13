package tw.edu.ncu.cc.oauth.server.concepts.user

import org.springframework.util.StringUtils
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import tw.edu.ncu.cc.oauth.data.v1.management.user.UserObject

public class UserValidator implements Validator {

    @Override
    public boolean supports( Class< ? > clazz ) {
        return UserObject.class.equals( clazz )
    }

    @Override
    public void validate( Object target, Errors errors ) {
        UserObject userObject = ( UserObject ) target
        if( StringUtils.isEmpty( userObject.getName() ) ) {
            errors.rejectValue( "name", "name.necessary", "name is necessary" )
        }
    }

}
