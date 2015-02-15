package tw.edu.ncu.cc.oauth.server.repository.impl;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ApplicationRepository {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager( EntityManager entityManager ) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}
