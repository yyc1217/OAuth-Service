package tw.edu.ncu.cc.oauth.server.concepts.user

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

import org.springframework.util.StringUtils

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import javax.persistence.metamodel.Attribute

import static org.springframework.data.jpa.domain.Specifications.where

@Service
@CompileStatic
class UserServiceImpl implements UserService {

    @Autowired
    def UserRepository userRepository

    @Override
    User findByName( String name, Attribute... attributes = [] ) {

        if (StringUtils.isEmpty(name)) {
            return null;
        }

        userRepository.findOne(
                where(UserSpecifications.nameEquals(name))
                        .and(UserSpecifications.include(User_.clients))
        )
    }

    @Override
    User create( User user ) {
        userRepository.save( user )
    }

    @Override
    User update( User user ) {
        userRepository.save( user )
    }

    @Override
    List<User> findByPartialName(String userName) {

        if (StringUtils.isEmpty(userName)) {
            return Collections.emptyList();
        }

        userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get(User_.name), "%${userName}%")
            }
        })
    }
}
