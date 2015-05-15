package tw.edu.ncu.cc.oauth.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.scheduling.annotation.EnableScheduling
import tw.edu.ncu.cc.oauth.server.config.BeanConfig
import tw.edu.ncu.cc.oauth.server.config.CacheConfig
import tw.edu.ncu.cc.oauth.server.config.MvcConfig
import tw.edu.ncu.cc.oauth.server.config.SecurityConfig

@EnableScheduling
@EnableJpaRepositories
@EnableSpringDataWebSupport
@SpringBootApplication( exclude = [ ErrorMvcAutoConfiguration ] )
@Import( [ BeanConfig, MvcConfig, SecurityConfig, CacheConfig ] )
public class Application extends SpringBootServletInitializer {

    public static void main( String[] args ) {
        SpringApplication.run( Application.class, args )
    }

    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder builder ) {
        return builder.sources( Application.class )
    }

}
