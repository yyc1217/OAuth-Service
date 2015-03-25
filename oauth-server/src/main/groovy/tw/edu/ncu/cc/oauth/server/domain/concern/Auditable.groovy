package tw.edu.ncu.cc.oauth.server.domain.concern;

trait Auditable {

    Date dateCreated
    Date lastUpdated

    def updateTimeStamp() {
        lastUpdated = new Date()
    }

}