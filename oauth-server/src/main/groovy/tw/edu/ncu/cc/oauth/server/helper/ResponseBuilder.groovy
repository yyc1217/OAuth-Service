package tw.edu.ncu.cc.oauth.server.helper

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import tw.edu.ncu.cc.oauth.data.v1.message.Error
import tw.edu.ncu.cc.oauth.data.v1.message.ErrorCode

public class ResponseBuilder {

    public static interface ResourceBuilder {
        public Object build();
    }

    protected ResponseBuilder() {}

    public static ValidationResponseBuilder validation() {
        return new ValidationResponseBuilder();
    }

    public static NoneValidationResponseBuilder noneValidation() {
        return new NoneValidationResponseBuilder();
    }

    public static class ValidationResponseBuilder {

        private BindingResult result;
        private ResourceBuilder resourceBuilder;

        ValidationResponseBuilder() {}

        public ValidationResponseBuilder errors( BindingResult result ) {
            this.result = result;
            return this;
        }

        public ValidationResponseBuilder resource( ResourceBuilder resourceBuilder ) {
            this.resourceBuilder = resourceBuilder;
            return this;
        }

        public ResponseEntity build() {
            if( result.hasErrors() ) {
                return new ResponseEntity<>(
                        new Error(
                                ErrorCode.INVALID_FIELD, result.getFieldError().getDefaultMessage()
                        ), HttpStatus.BAD_REQUEST
                );
            } else {
                Object resource = resourceBuilder.build()
                if( resource == null ) {
                    return new ResponseEntity<>(
                            "resource not found" , HttpStatus.NOT_FOUND
                    );
                } else {
                    return new ResponseEntity<>(
                            resource , HttpStatus.OK
                    );
                }
            }
        }
    }

    public static class NoneValidationResponseBuilder {

        private ResourceBuilder resourceBuilder;

        NoneValidationResponseBuilder() {}

        public NoneValidationResponseBuilder resource( ResourceBuilder resourceBuilder ) {
            this.resourceBuilder = resourceBuilder;
            return this;
        }

        public ResponseEntity build() {
            Object resource = resourceBuilder.build()
            if( resource == null ) {
                return new ResponseEntity<>(
                        "resource not found" , HttpStatus.NOT_FOUND
                );
            } else {
                return new ResponseEntity<>(
                        resource , HttpStatus.OK
                );
            }
        }
    }


}
