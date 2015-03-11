package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.domain.PermissionEntity;
import tw.edu.ncu.cc.oauth.server.repository.PermissionRepository;

import java.util.List;

@Repository
public class PermissionRepositoryImpl extends ApplicationRepository implements PermissionRepository {

    @Override
    public void deletePermission( PermissionEntity permission ) {
        getEntityManager().remove( permission );
    }

    @Override
    public List< PermissionEntity > readAllPermissions() {
        return getEntityManager()
                .createQuery( "SELECT permission FROM PermissionEntity permission", PermissionEntity.class )
                .getResultList();
    }

    @Override
    public PermissionEntity readPermissionByName( String name ) {
        return getEntityManager()
                .createQuery(
                        "SELECT permission FROM PermissionEntity permission " +
                        "WHERE permission.name = :name",
                        PermissionEntity.class )
                .setParameter( "name", name )
                .getSingleResult();
    }

    @Override
    public PermissionEntity readPermissionByID( int id ) {
        return getEntityManager()
                .createQuery(
                        "SELECT permission FROM PermissionEntity permission " +
                        "WHERE permission.id = :id ",
                        PermissionEntity.class )
                .setParameter( "id", id )
                .getSingleResult();
    }

    @Override
    public PermissionEntity createPermission( PermissionEntity permission ) {
        return getEntityManager().merge( permission );
    }

}
