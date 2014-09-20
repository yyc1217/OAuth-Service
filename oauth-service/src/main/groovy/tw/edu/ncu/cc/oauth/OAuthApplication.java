package tw.edu.ncu.cc.oauth;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.hibernate.Session;
import tw.edu.ncu.cc.oauth.db.HibernateUtil;
import tw.edu.ncu.cc.oauth.db.data.model.*;
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.*;
import tw.edu.ncu.cc.oauth.factory.HibernateSessionFactory;
import tw.edu.ncu.cc.oauth.factory.HibernateUtilFactory;
import tw.edu.ncu.cc.oauth.factory.HttpSessionFactory;
import tw.edu.ncu.cc.oauth.factory.OAuthIssuerFactory;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;

public class OAuthApplication extends ResourceConfig {
    public OAuthApplication() {
        packages( "tw.edu.ncu.cc" );
        property( JspMvcFeature.TEMPLATES_BASE_PATH, "/" );
        register( JspMvcFeature.class );
        register( new AbstractBinder() {
            @Override
            protected void configure() {

                bind( ClientModelImpl.class ).to( ClientModel.class );
                bind( AuthCodemModelImpl.class ).to( AuthCodeModel.class );
                bind( AccessTokenModelImpl.class ).to( AccessTokenModel.class );
                bind( PermissionModelImpl.class ).to( PermissionModel.class );
                bind( PortalUserModelImpl.class ).to( PortalUserModel.class ).in( Singleton.class );

                bindFactory( HttpSessionFactory.class ).to( HttpSession.class );
                bindFactory( HibernateSessionFactory.class ).to( Session.class );
                bindFactory( HibernateUtilFactory.class ).to( HibernateUtil.class ).in( Singleton.class );
                bindFactory( OAuthIssuerFactory.class ).to( OAuthIssuer.class ).in( Singleton.class );

            }
        } );
    }
}
