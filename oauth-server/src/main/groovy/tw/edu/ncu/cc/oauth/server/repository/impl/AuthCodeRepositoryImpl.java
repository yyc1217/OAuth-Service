package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.domain.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.repository.AuthCodeRepository;

import java.util.Date;

@Repository
public class AuthCodeRepositoryImpl extends ApplicationRepository implements AuthCodeRepository {

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
    public AuthCodeEntity readUnexpiredAuthCodeByID( int id ) {
        return getEntityManager()
                .createQuery(
                        "SELECT code FROM AuthCodeEntity code " +
                        "WHERE code.id = :id " +
                        "AND ( code.dateExpired = NULL OR code.dateExpired > CURRENT_TIMESTAMP )",
                        AuthCodeEntity.class )
                .setParameter( "id", id )
                .getSingleResult();
    }

    @Override
    public AuthCodeEntity readUnexpiredAuthCodeByCode( String code ) {
        return getEntityManager()
                .createQuery(
                        "SELECT code FROM AuthCodeEntity code " +
                        "WHERE code.code = :code " +
                        "AND ( code.dateExpired = NULL OR code.dateExpired > CURRENT_TIMESTAMP )",
                        AuthCodeEntity.class )
                .setParameter( "code", code )
                .getSingleResult();
    }

}
