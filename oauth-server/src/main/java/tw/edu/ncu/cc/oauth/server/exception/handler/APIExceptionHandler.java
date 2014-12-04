package tw.edu.ncu.cc.oauth.server.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ncu.cc.oauth.data.v1.message.ErrorCode;

@RestController
public class APIExceptionHandler {

    private Logger logger = LoggerFactory.getLogger( this.getClass() );

    @ExceptionHandler( HttpMessageNotReadableException.class )
    public ResponseEntity invalidRequestBody() {
        return new ResponseEntity<>(
                new tw.edu.ncu.cc.oauth.data.v1.message.Error(
                        ErrorCode.INVALID_BODY, "expect request in json format"
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity exceptionHandler( Exception exception ) {
        logger.error( "SEVERE-INTERNAL-ERROR", exception );
        return new ResponseEntity<>(
                new tw.edu.ncu.cc.oauth.data.v1.message.Error(
                        ErrorCode.SERVER_ERROR, exception.getMessage()
                ), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
