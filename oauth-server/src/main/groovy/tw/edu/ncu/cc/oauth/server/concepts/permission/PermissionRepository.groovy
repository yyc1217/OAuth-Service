package tw.edu.ncu.cc.oauth.server.concepts.permission

import org.springframework.data.jpa.repository.JpaRepository

public interface PermissionRepository extends JpaRepository< Permission, Integer > {

    Permission findByName( String name )

}
