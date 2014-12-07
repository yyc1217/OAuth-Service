package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.entity.UserEntity;
import tw.edu.ncu.cc.oauth.server.repository.UserRepository;
import tw.edu.ncu.cc.oauth.server.repository.impl.base.EntityManagerBean;

import java.util.List;

@Repository
public class UserRepositoryImpl extends EntityManagerBean implements UserRepository {

    @Override
    public UserEntity createUser( UserEntity user ) {
        return getEntityManager().merge( user );
    }

    @Override
    public UserEntity readUser( int id ) {
        return getEntityManager().find( UserEntity.class, id );
    }

    @Override
    public UserEntity readUser( String name ) {
        List<UserEntity> list = getEntityManager()
                .createQuery(
                        "SELECT user FROM UserEntity user " +
                        "WHERE user.name = :name", UserEntity.class )
                .setParameter( "name", name )
                .getResultList();
        return ( list.isEmpty() ? null : list.get( 0 ) );
    }

}