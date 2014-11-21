package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.data.Permission;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.entity.UserEntity;

import java.util.Set;

public interface AuthCodeService {

    public AuthCodeEntity getAuthCode( String code );

    public void deleteAuthCode ( AuthCodeEntity authCode );

    public AuthCodeEntity generateAuthCodeFor( UserEntity user, ClientEntity client, Set<Permission> scope );

}
