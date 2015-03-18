package tw.edu.ncu.cc.oauth.server.helper

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import tw.edu.ncu.cc.oauth.data.v1.message.Error
import tw.edu.ncu.cc.oauth.data.v1.message.ErrorCode
import tw.edu.ncu.cc.oauth.server.exception.ResourceNotFoundException


class Responder {

    private BindingResult bindingResult
    private List< Closure > pipes

    private Responder() {
        pipes = []
    }

    static def respondWith( Responder responder ) {
        responder.response()
    }

    static def resource() {
        return new Responder()
    }

    def validate( BindingResult bindingResult ) {
        this.bindingResult = bindingResult
        this
    }

    def pipe( Closure closure ) {
        this.pipes << closure
        this
    }

    private ResponseEntity response() {
        if( bindingResult != null && bindingResult.hasErrors() ) {
            responseWithValidationError()
        } else {
            responseWithPipeResource()
        }
    }

    private ResponseEntity responseWithValidationError() {
        return new ResponseEntity<>(
                new Error(
                        ErrorCode.INVALID_FIELD, bindingResult.fieldError.defaultMessage
                ), HttpStatus.BAD_REQUEST
        );
    }

    private ResponseEntity responseWithPipeResource() {
        try {

            def lastResource = pipes.inject( null ) { previousResource, pipe ->
                def currentResource = pipe( previousResource )
                if( currentResource == null ) {
                    throw new ResourceNotFoundException()
                } else {
                    return currentResource
                }
            }
            return new ResponseEntity<>(
                    lastResource , HttpStatus.OK
            );

        } catch ( ResourceNotFoundException ignore ) {
            return new ResponseEntity<>(
                    "resource not found" , HttpStatus.NOT_FOUND
            );
        }
    }

}
