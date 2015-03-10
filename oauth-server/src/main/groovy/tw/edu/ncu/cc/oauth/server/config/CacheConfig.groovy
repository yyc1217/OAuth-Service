package tw.edu.ncu.cc.oauth.server.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.ehcache.EhCacheCacheManager
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource

@Profile( 'production' )
@EnableCaching
@Configuration
class CacheConfig extends CachingConfigurerSupport {

    @Value( '${custom.cache.config-path}' )
    def String cacheConfigPath

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactory() {
        return new EhCacheManagerFactoryBean(
                configLocation: new ClassPathResource( cacheConfigPath ),
                shared: false
        );
    }

    @Override
    CacheManager cacheManager() {
        return new EhCacheCacheManager(
                cacheManager: ehCacheManagerFactory().getObject()
        )
    }

}
