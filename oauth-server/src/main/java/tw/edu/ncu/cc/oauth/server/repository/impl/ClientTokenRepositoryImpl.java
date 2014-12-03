package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.repository.ClientTokenRepository;
import tw.edu.ncu.cc.oauth.server.repository.impl.base.EntityManagerBean;

@Repository
public class ClientTokenRepositoryImpl extends EntityManagerBean implements ClientTokenRepository {

    @Override
    public void deleteAllAccessTokenOfClient( ClientEntity client ) {
        getEntityManager()
                .createQuery( "DELETE FROM AccessTokenEntity token WHERE token.client = :client " )
                .setParameter( "client", client )
                .executeUpdate();
    }

}
