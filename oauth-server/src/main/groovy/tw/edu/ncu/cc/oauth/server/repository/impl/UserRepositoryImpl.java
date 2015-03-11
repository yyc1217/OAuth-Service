package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.domain.UserEntity;
import tw.edu.ncu.cc.oauth.server.repository.UserRepository;

@Repository
public class UserRepositoryImpl extends ApplicationRepository implements UserRepository {

    @Override
    public UserEntity createUser( UserEntity user ) {
        return getEntityManager().merge( user );
    }

    @Override
    public UserEntity readUserByID( int id ) {
        return getEntityManager()
                .createQuery(
                        "SELECT user FROM UserEntity user " +
                        "WHERE user.id = :id",
                        UserEntity.class )
                .setParameter( "id", id )
                .getSingleResult();
    }

    @Override
    public UserEntity readUserByName( String name ) {
        return getEntityManager()
                .createQuery(
                        "SELECT user FROM UserEntity user " +
                        "WHERE user.name = :name",
                        UserEntity.class )
                .setParameter( "name", name )
                .getSingleResult();
    }

}
