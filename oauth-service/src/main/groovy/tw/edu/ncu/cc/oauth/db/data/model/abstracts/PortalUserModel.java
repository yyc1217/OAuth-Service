package tw.edu.ncu.cc.oauth.db.data.model.abstracts;

public interface PortalUserModel {
    public boolean isUserValid( String account, String password );
}
