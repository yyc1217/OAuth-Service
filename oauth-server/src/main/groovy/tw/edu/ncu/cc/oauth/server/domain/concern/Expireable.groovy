package tw.edu.ncu.cc.oauth.server.domain.concern

trait Expireable {

    Date dateExpired

    def revoke() {
        dateExpired = new Date( System.currentTimeMillis() )
    }

    static Date timeNow() {
        new Date()
    }

}