package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;

public interface ClientTokenRepository {

    public void deleteAllAccessTokenOfClient( ClientEntity client );
}
