package tw.edu.ncu.cc.oauth.server.concepts.manager

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.user.ManagerObject
import tw.edu.ncu.cc.oauth.server.concepts.user.User

@Component
class User_ManagerObjectConverter implements Converter< User, ManagerObject > {

    @Override
    ManagerObject convert( User source ) {
        ManagerObject managerObject = new ManagerObject();
        managerObject.id = source.name
        return managerObject;
    }

}
