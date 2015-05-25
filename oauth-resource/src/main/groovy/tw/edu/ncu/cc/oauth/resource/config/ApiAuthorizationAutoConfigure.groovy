package tw.edu.ncu.cc.oauth.resource.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.embedded.FilterRegistrationBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tw.edu.ncu.cc.oauth.resource.filter.AccessTokenDecisionFilter
import tw.edu.ncu.cc.oauth.resource.filter.ApiTokenDecisionFilter
import tw.edu.ncu.cc.oauth.resource.service.TokenConfirmService
import tw.edu.ncu.cc.oauth.resource.service.TokenConfirmServiceImpl


@Configuration
@EnableConfigurationProperties( RemoteConfig )
class ApiAuthorizationAutoConfigure {

    private Logger logger = LoggerFactory.getLogger( this.getClass() )

    @Autowired
    RemoteConfig remoteConfig

    @Bean
    @ConditionalOnMissingBean( TokenConfirmService )
    TokenConfirmService tokenConfirmService(  ) {
        new TokenConfirmServiceImpl( remoteConfig )
    }

    @Bean
    @ConditionalOnMissingBean( ApiTokenDecisionFilter )
    ApiTokenDecisionFilter apiTokenDecisionFilter() {
        logger.info( "auto-configure oauth api token decision filter with server path : " + remoteConfig.serverPath )
        ApiTokenDecisionFilter filter = new ApiTokenDecisionFilter()
        filter.setTokenConfirmService( tokenConfirmService() )
        filter
    }

    @Bean
    FilterRegistrationBean apiTokenDecisionFilterRegistration( ApiTokenDecisionFilter apiTokenDecisionFilter ) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean( apiTokenDecisionFilter )
        registrationBean.setEnabled( false )
        registrationBean
    }

    @Bean
    @ConditionalOnMissingBean( AccessTokenDecisionFilter )
    AccessTokenDecisionFilter accessTokenDecisionFilter() {
        logger.info( "auto-configure oauth access token decision filter with server path : " + remoteConfig.serverPath )
        AccessTokenDecisionFilter filter = new AccessTokenDecisionFilter()
        filter.setTokenConfirmService( tokenConfirmService() )
        filter
    }

    @Bean
    FilterRegistrationBean accessTokenDecisionFilterRegistration( AccessTokenDecisionFilter accessTokenDecisionFilter ) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean( accessTokenDecisionFilter )
        registrationBean.setEnabled( false )
        registrationBean
    }

}
