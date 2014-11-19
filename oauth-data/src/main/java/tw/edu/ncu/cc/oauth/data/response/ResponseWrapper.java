package tw.edu.ncu.cc.oauth.data.response;

public class ResponseWrapper< T > {

    private ResponseError error;
    private T data;

    public ResponseError getError() {
        return error;
    }

    public void setError( ResponseError error ) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData( T data ) {
        this.data = data;
    }

}
