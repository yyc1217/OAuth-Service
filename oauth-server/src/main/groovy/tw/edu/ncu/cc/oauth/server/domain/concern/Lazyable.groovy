package tw.edu.ncu.cc.oauth.server.domain.concern


trait Lazyable {

    static def attrFetchModeMap( List fields ) {
        this.attrFetchModes.subMap( fields )
    }

}