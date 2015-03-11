package tw.edu.ncu.cc.oauth.server.exception.handler

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import tw.edu.ncu.cc.oauth.data.v1.message.ErrorCode

import javax.persistence.NoResultException

@ControllerAdvice
public class ApplicationExceptionHandler {

    private Logger logger = LoggerFactory.getLogger( this.getClass() );

    @ExceptionHandler( [ NoResultException.class, EmptyResultDataAccessException.class ] )
    public ResponseEntity resourseNotFound() {
        return new ResponseEntity<>(
                new tw.edu.ncu.cc.oauth.data.v1.message.Error(
                        ErrorCode.NOT_EXIST, "resourse not exist"
                ), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler( [ HttpMessageNotReadableException.class, HttpMediaTypeNotSupportedException.class ] )
    public ResponseEntity invalidRequestBody() {
        return new ResponseEntity<>(
                new tw.edu.ncu.cc.oauth.data.v1.message.Error(
                        ErrorCode.INVALID_BODY, "expect request in json format"
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler( HttpRequestMethodNotSupportedException.class )
    public ResponseEntity invalidMethod( HttpRequestMethodNotSupportedException e ) {
        return new ResponseEntity<>(
                new tw.edu.ncu.cc.oauth.data.v1.message.Error(
                        ErrorCode.INVALID_METHOD, ( "method not supported:" + e.getMethod() + ", expect:" + Arrays.toString( e.getSupportedMethods()  ) ) as String
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity exceptionHandler( Exception exception ) {
        logger.error( "INTERNAL SERVER", exception );
        return new ResponseEntity<>(
                new tw.edu.ncu.cc.oauth.data.v1.message.Error(
                        ErrorCode.SERVER_ERROR, exception.getMessage()
                ), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
