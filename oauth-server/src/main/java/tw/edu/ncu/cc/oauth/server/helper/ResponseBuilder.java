package tw.edu.ncu.cc.oauth.server.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tw.edu.ncu.cc.oauth.data.v1.message.Error;
import tw.edu.ncu.cc.oauth.data.v1.message.ErrorCode;

public class ResponseBuilder {

    public static ResponseEntity buildGET( Object resource ) {
        if( resource == null ) {
            return new ResponseEntity<>(
                    new Error( ErrorCode.NOT_EXIST, "resource not exist" ), HttpStatus.NOT_FOUND
            );
        } else {
            return new ResponseEntity<>(
                    resource, HttpStatus.OK
            );
        }
    }

}
