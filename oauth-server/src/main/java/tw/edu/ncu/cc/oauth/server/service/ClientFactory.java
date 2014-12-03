package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.data.v1.management.application.Application;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;

public interface ClientFactory {

    public ClientEntity createClient( Application application );
}
