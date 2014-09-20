package tw.edu.ncu.cc.oauth.db.data.model;

import tw.edu.ncu.cc.oauth.db.data.model.abstracts.PortalUserModel;

public class PortalUserModelImpl implements PortalUserModel {
    @Override
    public boolean isUserValid( String account, String password ) {
        return false;
    }
}
