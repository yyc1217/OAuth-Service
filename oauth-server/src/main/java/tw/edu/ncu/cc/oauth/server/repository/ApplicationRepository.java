package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;

public interface ApplicationRepository {
    public ClientEntity getApplication( int clientID ) ;
    public void persistApplication( ClientEntity application ) ;
}
