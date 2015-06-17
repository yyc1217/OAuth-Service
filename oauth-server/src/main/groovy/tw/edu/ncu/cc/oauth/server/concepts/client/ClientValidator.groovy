package tw.edu.ncu.cc.oauth.server.concepts.client

import org.springframework.util.StringUtils
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils;
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
		ValidationUtils.rejectIfEmpty(errors, "name", "name.necessary", "name is necessary")
		ValidationUtils.rejectIfEmpty(errors, "callback", "callback.necessary", "callback is necessary")
		ValidationUtils.rejectIfEmpty(errors, "owner", "owner.necessary", "owner is necessary")
    }

}
