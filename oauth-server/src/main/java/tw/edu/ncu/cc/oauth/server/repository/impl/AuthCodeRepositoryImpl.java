package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.repository.AuthCodeRepository;
import tw.edu.ncu.cc.oauth.server.repository.impl.base.EntityManagerBean;

import java.util.Date;
import java.util.List;

@Repository
public class AuthCodeRepositoryImpl extends EntityManagerBean implements AuthCodeRepository {

    @Override
    public AuthCodeEntity revokeAuthCode( AuthCodeEntity authCode ) {
        authCode.setDateExpired( new Date() );
        return getEntityManager().merge( authCode );
    }

    @Override
    public AuthCodeEntity createAuthCode( AuthCodeEntity authCode ) {
        return getEntityManager().merge( authCode );
    }

    @Override
    public AuthCodeEntity readUnexpiredAuthCode( int id ) {
        List<AuthCodeEntity> list = getEntityManager()
                .createQuery(
                        "SELECT code FROM AuthCodeEntity code " +
                                "WHERE code.id = :id " +
                                "AND ( code.dateExpired = NULL OR code.dateExpired > CURRENT_TIMESTAMP )", AuthCodeEntity.class )
                .setParameter( "id", id )
                .getResultList();
        return ( list.isEmpty() ? null : list.get( 0 ) );
    }

    @Override
    public AuthCodeEntity readUnexpiredAuthCode( String code ) {
        List<AuthCodeEntity> list = getEntityManager()
                .createQuery(
                        "SELECT code FROM AuthCodeEntity code " +
                        "WHERE code.code = :code " +
                        "AND ( code.dateExpired = NULL OR code.dateExpired > CURRENT_TIMESTAMP )", AuthCodeEntity.class )
                .setParameter( "code", code )
                .getResultList();
        return ( list.isEmpty() ? null : list.get( 0 ) );
    }

}
