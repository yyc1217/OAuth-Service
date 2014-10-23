package tw.edu.ncu.cc.oauth.server;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.servlet.ServletProperties;
import org.hibernate.Session;
import tw.edu.ncu.cc.manage.openid.OpenIDManager;
import tw.edu.ncu.cc.oauth.server.db.HibernateUtil;
import tw.edu.ncu.cc.oauth.server.db.model.AccessTokenModel;
import tw.edu.ncu.cc.oauth.server.db.model.AuthCodeModel;
import tw.edu.ncu.cc.oauth.server.db.model.ClientModel;
import tw.edu.ncu.cc.oauth.server.db.model.UserModel;
import tw.edu.ncu.cc.oauth.server.db.model.impl.AccessTokenModelImpl;
import tw.edu.ncu.cc.oauth.server.db.model.impl.AuthCodemModelImpl;
import tw.edu.ncu.cc.oauth.server.db.model.impl.ClientModelImpl;
import tw.edu.ncu.cc.oauth.server.db.model.impl.UserModelImpl;
import tw.edu.ncu.cc.oauth.server.factory.*;
import tw.edu.ncu.cc.oauth.server.view.AuthBean;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;

public class OAuthApplication extends ResourceConfig {
    public OAuthApplication() {
        packages( "tw.edu.ncu.cc" );
        property( JspMvcFeature.TEMPLATES_BASE_PATH, "/jsp/" );
        property( ServletProperties.FILTER_STATIC_CONTENT_REGEX, "/resources/.*" );
        register( JspMvcFeature.class );
        register( new AbstractBinder() {
            @Override
            protected void configure() {

                bind( ClientModelImpl.class ).to( ClientModel.class );
                bind( AuthCodemModelImpl.class ).to( AuthCodeModel.class );
                bind( AccessTokenModelImpl.class ).to( AccessTokenModel.class );
                bind( UserModelImpl.class ).to( UserModel.class );

                bindFactory( OpenIDFactory.class ).to( OpenIDManager.class ).in( Singleton.class );
                bindFactory( AuthBeanFactory.class ).to( AuthBean.class );

                bindFactory( HttpSessionFactory.class ).to( HttpSession.class );

                bindFactory( HibernateSessionFactory.class ).to( Session.class ).in( RequestScoped.class );
                bindFactory( HibernateUtilFactory.class ).to( HibernateUtil.class ).in( Singleton.class );

                bindFactory( OAuthIssuerFactory.class ).to( OAuthIssuer.class );

            }
        } );
    }
}
