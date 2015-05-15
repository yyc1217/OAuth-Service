package tw.edu.ncu.cc.oauth.server.concepts.permission

import tw.edu.ncu.cc.oauth.server.domain.BasicEntity

import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Permission extends BasicEntity {

    @Column( unique = true, nullable = false )
    def String name

}
