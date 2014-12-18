package tw.edu.ncu.cc.oauth.resource.config;

public class RemoteConfig {

    private String addrPrefix = "";
    private String addrSuffix = "";

    public String getAddrSuffix() {
        return addrSuffix;
    }

    public void setAddrSuffix( String addrSuffix ) {
        this.addrSuffix = addrSuffix;
    }

    public String getAddrPrefix() {
        return addrPrefix;
    }

    public void setAddrPrefix( String addrPrefix ) {
        this.addrPrefix = addrPrefix;
    }

}
