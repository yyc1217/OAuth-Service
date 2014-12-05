package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.repository.AuthCodeRepository;
import tw.edu.ncu.cc.oauth.server.repository.impl.base.EntityManagerBean;

import java.util.List;

@Repository
public class AuthCodeRepositoryImpl extends EntityManagerBean implements AuthCodeRepository {

    @Override
    public void deleteAuthCode( AuthCodeEntity authCode ) {
        getEntityManager().remove( authCode );
    }

    @Override
    public AuthCodeEntity createAuthCode( AuthCodeEntity authCode ) {
        return getEntityManager().merge( authCode );
    }

    @Override
    public AuthCodeEntity readAuthCode( int id ) {
        return getEntityManager().find( AuthCodeEntity.class, id );
    }

    @Override
    public AuthCodeEntity readAuthCode( String code ) {
        List<AuthCodeEntity> list = getEntityManager()
                .createQuery(
                        "SELECT authCode FROM AuthCodeEntity authCode " +
                        "WHERE authCode.code = :code", AuthCodeEntity.class )
                .setParameter( "code", code )
                .getResultList();
        return ( list.isEmpty() ? null : list.get( 0 ) );
    }

}
