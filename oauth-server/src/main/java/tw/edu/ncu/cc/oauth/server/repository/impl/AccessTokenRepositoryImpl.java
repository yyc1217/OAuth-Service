package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.repository.AccessTokenRepository;
import tw.edu.ncu.cc.oauth.server.repository.impl.base.EntityManagerBean;

@Repository
public class AccessTokenRepositoryImpl extends EntityManagerBean implements AccessTokenRepository {

    @Override
    public void persistAccessToken( AccessTokenEntity accessToken ) {
       getEntityManager().persist( accessToken );
    }

    @Override
    public void deleteAccessToken( AccessTokenEntity accessToken ) {
      getEntityManager().remove( accessToken );
    }

    @Override
    public AccessTokenEntity getAccessToken( int id ) {
        return getEntityManager().find( AccessTokenEntity.class, id );
    }

    @Override
    public AccessTokenEntity getAccessToken( String token ) {
        return getEntityManager()
                .createQuery(
                        "SELECT token FROM AccessTokenEntity token " +
                        "WHERE  token.token = :token", AccessTokenEntity.class )
                .setParameter( "token", token )
                .getSingleResult();
    }

}
