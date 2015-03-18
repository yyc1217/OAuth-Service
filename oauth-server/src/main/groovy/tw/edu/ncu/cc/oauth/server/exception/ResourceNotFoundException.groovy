package tw.edu.ncu.cc.oauth.server.exception


class ResourceNotFoundException extends RuntimeException {

    ResourceNotFoundException() {
        super()
    }

    ResourceNotFoundException( String message ) {
        super( message )
    }

    ResourceNotFoundException( String message, Throwable cause ) {
        super( message, cause )
    }

    ResourceNotFoundException( Throwable cause ) {
        super( cause )
    }

    protected ResourceNotFoundException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace )
    }

}
