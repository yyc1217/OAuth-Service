package tw.edu.ncu.cc.oauth.server.domain

import org.springframework.data.jpa.domain.Specification

import javax.persistence.criteria.*
import javax.persistence.metamodel.Attribute

abstract class BasicSpecifications< T extends BasicEntity > {

    static Specification< T > idEquals( Integer id ) {
        return new Specification< T >() {
            @Override
            public Predicate toPredicate( Root< T > root, CriteriaQuery< ? > query, CriteriaBuilder cb ) {
                return cb.equal( root.get( BasicEntity_.id ), id )
            }
        }
    }

    static Specification< T > include( Attribute...attributes ) {
        new Specification< T >() {
            @Override
            Predicate toPredicate( Root< T > root, CriteriaQuery<?> query, CriteriaBuilder cb ) {
                for ( Attribute attribute : attributes ) {
                    if( attribute.isCollection(  ) ) {
                        root.fetch( attribute.name, JoinType.LEFT )
                    } else {
                        root.fetch( attribute.name )
                    }
                }
                return null
            }
        }
    }

}
