package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.repository.AuthCodeRepository;
import tw.edu.ncu.cc.oauth.server.repository.impl.base.EntityManagerBean;

@Repository
public class AuthCodemRepositoryImpl extends EntityManagerBean implements AuthCodeRepository {

    @Override
    public void persistAuthCode( AuthCodeEntity authCode ) {
       getEntityManager().persist( authCode );
    }

    @Override
    public void deleteAuthCode( AuthCodeEntity authCode ) {
        getEntityManager().remove( authCode );
    }

    @Override
    public AuthCodeEntity getAuthCode( String code ) {
        return getEntityManager()
                .createQuery(
                        "SELECT authCode FROM AuthCodeEntity authCode " +
                                "WHERE authCode.code = :code", AuthCodeEntity.class )
                .setParameter( "code", code )
                .getSingleResult();
    }

}
