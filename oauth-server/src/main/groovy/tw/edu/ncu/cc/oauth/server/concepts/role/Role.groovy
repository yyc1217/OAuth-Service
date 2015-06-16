package tw.edu.ncu.cc.oauth.server.concepts.role

import tw.edu.ncu.cc.oauth.server.domain.BasicEntity

import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Role extends BasicEntity {

    @Column( unique = true, nullable = false )
    def String name

}