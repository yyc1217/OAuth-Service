package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ncu.cc.oauth.server.entity.PermissionEntity;
import tw.edu.ncu.cc.oauth.server.helper.ResultBuilder;
import tw.edu.ncu.cc.oauth.server.repository.PermissionRepository;
import tw.edu.ncu.cc.oauth.server.repository.impl.base.EntityManagerBean;

import java.util.List;

@Repository
public class PermissionRepositoryImpl extends EntityManagerBean implements PermissionRepository {

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
    public PermissionEntity readPermission( String name ) {
        return ResultBuilder.result(
                getEntityManager()
                        .createQuery(
                                "SELECT permission FROM PermissionEntity permission " +
                                "WHERE permission.name = :name" )
                        .setParameter( "name", name )
                        .getResultList()
        ).singleResult( PermissionEntity.class );
    }

    @Override
    public PermissionEntity createPermission( PermissionEntity permission ) {
        return getEntityManager().merge( permission );
    }

}
