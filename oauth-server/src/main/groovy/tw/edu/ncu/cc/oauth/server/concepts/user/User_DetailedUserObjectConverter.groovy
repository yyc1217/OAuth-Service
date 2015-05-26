package tw.edu.ncu.cc.oauth.server.concepts.user

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.user.DetailedUserObject

@Component
class User_DetailedUserObjectConverter implements Converter< User, DetailedUserObject > {

    @Override
    DetailedUserObject convert( User source ) {
        DetailedUserObject userObject = new DetailedUserObject()
        userObject.id = source.id
        userObject.name = source.name
        userObject.date_created = source.dateCreated
        userObject.last_updated = source.lastUpdated
        userObject
    }

}
