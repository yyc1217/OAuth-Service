package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.repository.AccessTokenRepository;
import tw.edu.ncu.cc.oauth.server.repository.impl.base.EntityManagerBean;

import java.util.Date;

@Repository
public class AccessTokenRepositoryImpl extends EntityManagerBean implements AccessTokenRepository {

    @Override
    public AccessTokenEntity revokeAccessToken( AccessTokenEntity accessToken ) {
        accessToken.setDateExpired( new Date() );
        return getEntityManager().merge( accessToken );
    }

    @Override
    public AccessTokenEntity createAccessToken( AccessTokenEntity accessToken ) {
        return getEntityManager().merge( accessToken );
    }

    @Override
    public AccessTokenEntity readUnexpiredAccessToken( int id ) {
        return getEntityManager()
                .createQuery(
                        "SELECT token FROM AccessTokenEntity token " +
                        "WHERE  token.id = :id " +
                        "AND ( token.dateExpired = NULL OR token.dateExpired > CURRENT_TIMESTAMP )",
                        AccessTokenEntity.class )
                .setParameter( "id", id )
                .getSingleResult();
    }

    @Override
    public AccessTokenEntity readUnexpiredAccessToken( String token ) {
        return getEntityManager()
                .createQuery(
                        "SELECT token FROM AccessTokenEntity token " +
                        "WHERE  token.token = :token " +
                        "AND ( token.dateExpired = NULL OR token.dateExpired > CURRENT_TIMESTAMP )",
                        AccessTokenEntity.class )
                .setParameter( "token", token )
                .getSingleResult();
    }

}
