package tw.edu.ncu.cc.oauth.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import tw.edu.ncu.cc.oauth.server.entity.UserEntity;
import tw.edu.ncu.cc.oauth.server.repository.UserRepository;


public class OpenIDUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository( UserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        if( userRepository.getUser( username ) == null ) {
            throw new UsernameNotFoundException( username );
        }
        return new User( username, "", AuthorityUtils.createAuthorityList( "ROLE_USER" ) );
    }

    @Override
    public UserDetails loadUserDetails( OpenIDAuthenticationToken token ) throws UsernameNotFoundException {

        if( userRepository.getUser( token.getName() ) == null ) {
            for ( OpenIDAttribute attribute : token.getAttributes() ) {
                if ( attribute.getName().equals( "student_id" ) ) {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setName( attribute.getValues().get( 0 ) );
                    userRepository.persistUser( userEntity );
                    break;
                }
            }
        }

        return new User( token.getName(), "", AuthorityUtils.createAuthorityList( "ROLE_USER" ) );
    }

}
