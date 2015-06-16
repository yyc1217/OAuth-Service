package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.manager.ManagerService
import tw.edu.ncu.cc.oauth.server.concepts.user.User


class ManagerServiceImplTest extends SpringSpecification {

    @Autowired
    ManagerService managerService

    @Transactional
    def "it can create manager"() {
        given:
            def manager = new User( name: "MANAGERTEST" )
        when:
            managerService.create( manager )
        then:
            managerService.findByName( "MANAGERTEST" ) != null
    }

    @Transactional
    def "it can delete manager"() {
        given:
            def manager = get_manager( 1 )
        when:
            managerService.delete( manager )
        then:
            managerService.findByName( manager.name ) == null
    }

    @Transactional
    def "it can find all managers in page"() {
        expect:
            managerService.findAllManagers( new PageRequest( 0, 3 ) ).size() != 0
    }



}
