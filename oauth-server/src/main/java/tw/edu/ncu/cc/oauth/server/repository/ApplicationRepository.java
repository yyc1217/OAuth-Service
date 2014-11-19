package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.ApplicationEntity;

public interface ApplicationRepository {
    public ApplicationEntity getApplication( int clientID ) ;
    public void persistApplication( ApplicationEntity application ) ;
}
