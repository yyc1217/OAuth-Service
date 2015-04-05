package tw.edu.ncu.cc.oauth.server.service.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LogServiceImpl implements LogService {

    private Logger logger = LoggerFactory.getLogger( this.getClass() )

    @Override
    def info( String action, String operator ) {
        logger.info( formatMessage( action, operator, null ) )
    }

    @Override
    def info( String action, String operator, String extra ) {
        logger.info( formatMessage( action, operator, extra ) )
    }

    @Override
    def error( String extra, Throwable throwable ) {
        logger.info( extra, throwable )
    }

    @Override
    def error( String action, String operator, String extra, Throwable throwable ) {
        logger.error( formatMessage( action, operator, extra ), throwable )
    }

    static def formatMessage( String action, String operator, String extra ) {
        if( extra == null ) {
            String.format( "[ %s ] [ %s ]", action, operator )
        } else {
            String.format( "[ %s ] [ %s ] [ %s ]", action, operator, extra )
        }
    }

}
