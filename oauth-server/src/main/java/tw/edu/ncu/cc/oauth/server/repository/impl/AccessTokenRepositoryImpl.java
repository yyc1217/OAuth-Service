package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.repository.AccessTokenRepository;
import tw.edu.ncu.cc.oauth.server.repository.impl.base.EntityManagerBean;

import java.util.Date;
import java.util.List;

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
        List<AccessTokenEntity> list = getEntityManager()
                .createQuery(
                        "SELECT token FROM AccessTokenEntity token " +
                                "WHERE  token.id = :id " +
                                "AND ( token.dateExpired = NULL OR token.dateExpired > CURRENT_TIMESTAMP )",
                        AccessTokenEntity.class )
                .setParameter( "id", id )
                .getResultList();
        return ( list.isEmpty() ? null : list.get( 0 ) );
    }

    @Override
    public AccessTokenEntity readUnexpiredAccessToken( String token ) {
        List<AccessTokenEntity> list = getEntityManager()
                .createQuery(
                        "SELECT token FROM AccessTokenEntity token " +
                                "WHERE  token.token = :token " +
                                "AND ( token.dateExpired = NULL OR token.dateExpired > CURRENT_TIMESTAMP )",
                        AccessTokenEntity.class )
                .setParameter( "token", token )
                .getResultList();
        return ( list.isEmpty() ? null : list.get( 0 ) );
    }

}
