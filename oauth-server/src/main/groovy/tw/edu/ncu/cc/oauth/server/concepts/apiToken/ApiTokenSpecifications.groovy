package tw.edu.ncu.cc.oauth.server.concepts.apiToken

import org.springframework.data.jpa.domain.Specification
import tw.edu.ncu.cc.oauth.server.domain.BasicSpecifications

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class ApiTokenSpecifications extends BasicSpecifications< ApiToken > {

    static Specification< ApiToken > unexpired() {
        return new Specification< ApiToken >() {
            @Override
            public Predicate toPredicate( Root< ApiToken > root, CriteriaQuery< ? > query, CriteriaBuilder cb ) {
                return cb.greaterThan( root.get( ApiToken_.dateExpired ), new Date() )
            }
        }
    }

}
