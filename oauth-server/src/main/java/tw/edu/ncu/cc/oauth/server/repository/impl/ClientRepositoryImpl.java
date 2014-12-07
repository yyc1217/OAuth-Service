package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository;
import tw.edu.ncu.cc.oauth.server.repository.impl.base.EntityManagerBean;

@Repository
public class ClientRepositoryImpl extends EntityManagerBean implements ClientRepository {

    @Override
    public ClientEntity createClient( ClientEntity client ) {
        return getEntityManager().merge( client );
    }

    @Override
    public ClientEntity updateClient( ClientEntity client ) {
        return getEntityManager().merge( client );
    }

    @Override
    public void deleteClient( ClientEntity client ) {
        getEntityManager().remove( client );
    }

    @Override
    public ClientEntity readClient( int clientID ) {
        return getEntityManager().find( ClientEntity.class, clientID );
    }

}
