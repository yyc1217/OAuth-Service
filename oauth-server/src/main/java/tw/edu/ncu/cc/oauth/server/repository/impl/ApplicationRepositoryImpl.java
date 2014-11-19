package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.entity.ApplicationEntity;
import tw.edu.ncu.cc.oauth.server.repository.ApplicationRepository;
import tw.edu.ncu.cc.oauth.server.repository.impl.base.EntityManagerBean;

@Repository
public class ApplicationRepositoryImpl extends EntityManagerBean implements ApplicationRepository {

    @Override
    public ApplicationEntity getApplication( int clientID ) {
        return getEntityManager().find( ApplicationEntity.class, clientID );
    }

    @Override
    public void persistApplication( ApplicationEntity application ) {
        getEntityManager().persist( application );
    }

}
