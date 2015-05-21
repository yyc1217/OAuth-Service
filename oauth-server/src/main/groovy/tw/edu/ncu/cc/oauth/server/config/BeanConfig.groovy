package tw.edu.ncu.cc.oauth.server.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tw.edu.ncu.cc.manage.openid.OpenIDManager

@Configuration
class BeanConfig {

    @Bean
    @Autowired
    public OpenIDManager openIDManager( @Value( '${custom.openid.config-path}' ) String openidConfigPath ) {
        return new OpenIDManager( openidConfigPath );
    }

}
