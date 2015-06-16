package tw.edu.ncu.cc.oauth.server.concepts.manager

import org.springframework.validation.Errors
import org.springframework.validation.Validator
import tw.edu.ncu.cc.oauth.data.v1.management.user.ManagerCreateObject

class ManagerCreateValidator implements Validator {

    @Override
    public boolean supports( Class< ? > clazz ) {
        return ManagerCreateObject.class.equals( clazz )
    }

    @Override
    public void validate( Object target, Errors errors ) {
        ManagerCreateObject managerObject = ( ManagerCreateObject ) target
        if( managerObject.getId() == null ) {
            errors.rejectValue( "id", "id.necessary", "id is necessary" )
        }
//        if( StringUtils.isEmpty( managerObject.getName() ) ) {
//            errors.rejectValue( "name", "name.necessary", "name is necessary" )
//        }
    }

}
