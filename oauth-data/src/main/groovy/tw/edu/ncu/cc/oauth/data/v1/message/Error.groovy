package tw.edu.ncu.cc.oauth.data.v1.message;

public class Error {

    def int error
    def String error_description

    public Error( int error, String error_description ) {
        this.error = error;
        this.error_description = error_description;
    }

}
