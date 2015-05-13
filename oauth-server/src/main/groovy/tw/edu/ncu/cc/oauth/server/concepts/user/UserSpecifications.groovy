package tw.edu.ncu.cc.oauth.server.concepts.user

import org.springframework.data.jpa.domain.Specification
import tw.edu.ncu.cc.oauth.server.domain.BasicSpecifications

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class UserSpecifications extends BasicSpecifications< User > {

    static Specification< User > nameEquals( final String name ) {
        return new Specification< User >() {
            @Override
            public Predicate toPredicate( Root< User > root, CriteriaQuery< ? > query, CriteriaBuilder cb ) {
                return cb.equal( root.get( User_.name ), name )
            }
        }
    }
}
