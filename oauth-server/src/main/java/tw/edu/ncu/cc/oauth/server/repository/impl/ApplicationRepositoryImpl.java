package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.repository.ApplicationRepository;
import tw.edu.ncu.cc.oauth.server.repository.impl.base.EntityManagerBean;

@Repository
public class ApplicationRepositoryImpl extends EntityManagerBean implements ApplicationRepository {

    @Override
    public ClientEntity getApplication( int clientID ) {
        return getEntityManager().find( ClientEntity.class, clientID );
    }

    @Override
    public void persistApplication( ClientEntity application ) {
        getEntityManager().persist( application );
    }

}
