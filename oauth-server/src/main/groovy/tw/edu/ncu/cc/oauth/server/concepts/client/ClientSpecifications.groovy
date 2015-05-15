package tw.edu.ncu.cc.oauth.server.concepts.client

import org.springframework.data.jpa.domain.Specification
import tw.edu.ncu.cc.oauth.server.domain.BasicSpecifications

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class ClientSpecifications extends BasicSpecifications< Client > {

    static Specification< Client > undeleted() {
        return new Specification< Client >() {
            @Override
            public Predicate toPredicate( Root< Client > root, CriteriaQuery< ? > query, CriteriaBuilder cb ) {
                return cb.equal( root.get( Client_.deleted ), false )
            }
        }
    }

}
