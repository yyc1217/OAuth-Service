package tw.edu.ncu.cc.oauth.data.v1.message;

public class Error {

    private int error;
    private String error_description;

    public Error( int error, String error_description ) {
        this.error = error;
        this.error_description = error_description;
    }

    public int getError() {
        return error;
    }

    public void setError( int error ) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description( String error_description ) {
        this.error_description = error_description;
    }

}
