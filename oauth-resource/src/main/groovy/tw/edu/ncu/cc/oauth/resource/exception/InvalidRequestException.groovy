package tw.edu.ncu.cc.oauth.resource.exception

import org.springframework.http.HttpStatus

class InvalidRequestException extends RuntimeException {

    def HttpStatus httpStatus
    def String message

    InvalidRequestException( HttpStatus httpStatus, String message ) {
        this.httpStatus = httpStatus
        this.message = message
    }

}
