package tw.edu.ncu.cc.oauth.server.concepts.log


interface LogService {

    def info( String action, String operator )
    def info( String action, String operator, String extra )
    def error( String extra, Throwable throwable )
    def error( String action, String operator, String extra, Throwable throwable )

}