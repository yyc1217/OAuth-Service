package tw.edu.ncu.cc.oauth.server.concepts.client

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import tw.edu.ncu.cc.oauth.data.v1.management.client.IdClientObject
import tw.edu.ncu.cc.oauth.server.concepts.security.SecretService
import tw.edu.ncu.cc.oauth.server.concepts.user.UserService

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import javax.persistence.metamodel.Attribute

import static org.springframework.data.jpa.domain.Specifications.where

@Service
@CompileStatic
class ClientServiceImpl implements ClientService {

    @Autowired
    def UserService userService

    @Autowired
    def SecretService secretService

    @Autowired
    def ClientRepository clientRepository

    @Override
    Client create( Client client ) {
        String newSecret = secretService.generateToken()
        client.encryptedSecret = secretService.encrypt( newSecret )
        clientRepository.save( client )
    }

    @Override
    Client refreshSecret( Client client ) {
        String newSecret = secretService.generateToken()
        client.encryptedSecret = secretService.encrypt( newSecret )
        clientRepository.save( client )
    }

    @Override
    Client update( Client client ) {
        clientRepository.save( client )
    }

    @Override
    Client delete( Client client ) {
        client.deleted = true
        clientRepository.save( client )
    }

    @Override
    Client findUndeletedBySerialId( String serialId, Attribute...attributes = [] ) {
        long id = secretService.decodeHashId( serialId )
        clientRepository.findOne(
                where( ClientSpecifications.idEquals( id as Integer ) )
                        .and( ClientSpecifications.undeleted() )
                        .and( ClientSpecifications.include( attributes ) )
        )
    }

    @Override
    boolean isCredentialValid( String serialId, String secret ) {
        Client client = findUndeletedBySerialId( serialId )
        if( client == null ) {
            return false
        } else {
            return secretService.matches( secret, client.encryptedSecret )
        }
    }

    @Override
    List<Client> findByDTO(IdClientObject dto)  {
        clientRepository.findAll(new Specification<Client>() {
            @Override
            public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                def predicates = []

                if (!StringUtils.isEmpty(dto.id)) {
                    predicates.add(cb.equal(root.get("id"), dto.id as Integer))
                }

                if (!StringUtils.isEmpty(dto.name)) {
                    predicates.add(cb.like(root.get(Client_.name), "%${dto.name}%"))
                }

                if (!StringUtils.isEmpty(dto.owner)) {
                    def user = userService.findByName(dto.owner)
                    if (user != null) {
                        predicates.add(cb.equal(root.get(Client_.owner), user))
                    }
                }

                predicates.add(cb.equal(root.get(Client_.deleted), dto.isDeleted))

                cb.and(predicates as Predicate[])
            }
        })
    }
}
