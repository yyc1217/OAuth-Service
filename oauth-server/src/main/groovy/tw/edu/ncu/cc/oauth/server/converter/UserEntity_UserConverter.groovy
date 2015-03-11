package tw.edu.ncu.cc.oauth.server.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.user.User
import tw.edu.ncu.cc.oauth.server.domain.UserEntity

@Component
public class UserEntity_UserConverter implements Converter< UserEntity, User > {

    @Override
    public User convert( UserEntity source ) {
        User user = new User();
        user.setName( source.getName() );
        return user;
    }

}
