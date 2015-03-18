package tw.edu.ncu.cc.oauth.server.exception.handler

import org.grails.datastore.mapping.validation.ValidationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import tw.edu.ncu.cc.oauth.data.v1.message.ErrorCode

@ControllerAdvice
public class ApplicationExceptionHandler {

    private Logger logger = LoggerFactory.getLogger( this.getClass() );

    @ExceptionHandler( [ ValidationException ] )
    def ResponseEntity validationError( ValidationException e ) {
        return new ResponseEntity<>(
                new tw.edu.ncu.cc.oauth.data.v1.message.Error(
                        ErrorCode.INVALID_FIELD, "field validation error:" + e.message
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler( [ HttpMessageNotReadableException.class, HttpMediaTypeNotSupportedException.class ] )
    def ResponseEntity invalidRequestBody() {
        return new ResponseEntity<>(
                new tw.edu.ncu.cc.oauth.data.v1.message.Error(
                        ErrorCode.INVALID_BODY, "expect request in json format"
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler( HttpRequestMethodNotSupportedException.class )
    def ResponseEntity invalidMethod( HttpRequestMethodNotSupportedException e ) {
        return new ResponseEntity<>(
                new tw.edu.ncu.cc.oauth.data.v1.message.Error(
                        ErrorCode.INVALID_METHOD, ( "method not supported:" + e.method + ", expect:" + Arrays.toString( e.getSupportedMethods()  ) ) as String
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler( Exception )
    def ResponseEntity exceptionHandler( Exception exception ) {
        logger.error( "UNEXPECTED ERROR:", exception );
        return new ResponseEntity<>(
                new tw.edu.ncu.cc.oauth.data.v1.message.Error(
                        ErrorCode.SERVER_ERROR, exception.message
                ), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
