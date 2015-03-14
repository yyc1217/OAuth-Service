package tw.edu.ncu.cc.oauth.server.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.user.UserObject
import tw.edu.ncu.cc.oauth.server.domain.User

@Component
class User_UserObjectConverter implements Converter< User, UserObject > {

    @Override
    UserObject convert( User source ) {
        UserObject userObject = new UserObject();
        userObject.name = source.name
        return userObject;
    }

}
