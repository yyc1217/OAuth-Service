package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository;
import tw.edu.ncu.cc.oauth.server.repository.impl.base.EntityManagerBean;

@Repository
public class ClientRepositoryImpl extends EntityManagerBean implements ClientRepository {

    @Override
    public void persistClient( ClientEntity client ) {
        getEntityManager().persist( client );
    }

    @Override
    public void deleteClient( ClientEntity client ) {
        getEntityManager().remove( client );
    }

    @Override
    public ClientEntity getClient( int clientID ) {
        return getEntityManager().find( ClientEntity.class, clientID );
    }

}
