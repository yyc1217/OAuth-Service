package tw.edu.ncu.cc.oauth.server.config

import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
public class SecurityConfig {

    @Order( 1 )
    @Configuration
    public static class OauthConfig1 extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure( HttpSecurity http ) throws Exception {
            http.antMatcher( "/management/**" )
                .authorizeRequests()
                    .antMatchers( "/management/v1/**" ).access( "hasIpAddress('127.0.0.1') or hasIpAddress('0:0:0:0:0:0:0:1')" )
                    .and()
                .csrf().disable()
        }
    }

    @Order( 2 )
    @Configuration
    public static class OauthConfig2 extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure( HttpSecurity http ) throws Exception {
            http.antMatcher( "/oauth/token" )
                .authorizeRequests()
                    .antMatchers( "/oauth/token" ).permitAll()
                    .and()
                .csrf().disable()
        }
    }

    @Order( 3 )
    @Configuration
    public static class OauthConfig3 extends WebSecurityConfigurerAdapter {
        @Override
        void configure( WebSecurity web ) throws Exception {
            web.ignoring().antMatchers( "/resource/**" )
        }
        @Override
        protected void configure( HttpSecurity http ) throws Exception {
            http.authorizeRequests()
                    .antMatchers( "/oauth/authorize" ).hasRole( "USER" )
                    .antMatchers( "/login_page" ).permitAll()
                    .antMatchers( "/login_confirm" ).permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage( "/login_page" ).permitAll()
        }
    }

}
