package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tw.edu.ncu.cc.oauth.server.domain.User

@Service
@Transactional
class UserServiceImpl implements UserService {

    @Override
    User readByName( String name, List includeField = [] ) {
        return User.findByName( name, [ fetch: User.attrFetchModeMap( includeField ) ] )
    }

    @Override
    User createWithNameIfNotExist( String name ) {
        def user = readByName( name )
        if( user == null ) {
            user = createWithName( name )
        }
        return user
    }

    @Override
    User createWithName( String name ) {
        return new User( name: name ).save()
    }

}
