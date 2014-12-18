package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository;
import tw.edu.ncu.cc.oauth.server.repository.impl.base.EntityManagerBean;

import java.util.Date;

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
        Date timeNow = new Date();
        getEntityManager()
                .createQuery(
                        "UPDATE FROM AuthCodeEntity " +
                        "SET dateExpired = :time " +
                        "WHERE client = :client"
                )
                .setParameter( "time", timeNow )
                .setParameter( "client", client )
                .executeUpdate();
        getEntityManager()
                .createQuery(
                        "UPDATE FROM AccessTokenEntity " +
                        "SET dateExpired = :time " +
                        "WHERE client = :client"
                )
                .setParameter( "time", timeNow )
                .setParameter( "client", client )
                .executeUpdate();
        getEntityManager().remove( client );
    }

    @Override
    public void revokeClientTokens( ClientEntity client ) {
        getEntityManager()
                .createQuery(
                        "UPDATE FROM AccessTokenEntity " +
                        "SET dateExpired = :time " +
                        "WHERE client = :client"
                )
                .setParameter( "time", new Date() )
                .setParameter( "client", client )
                .executeUpdate();
    }

    @Override
    public ClientEntity readClient( int clientID ) {
        return getEntityManager()
                .createQuery(
                        "SELECT client FROM ClientEntity client " +
                        "WHERE client.id = :id ",
                        ClientEntity.class )
                .setParameter( "id", clientID )
                .getSingleResult();
    }

}
