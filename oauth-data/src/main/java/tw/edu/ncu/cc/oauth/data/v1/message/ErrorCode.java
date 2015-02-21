package tw.edu.ncu.cc.oauth.data.v1.message;

public class ErrorCode {

    public static final int NOT_EXIST = 0;
    public static final int INVALID_FIELD = NOT_EXIST + 1;
    public static final int INVALID_BODY = INVALID_FIELD + 1;
    public static final int INVALID_METHOD = INVALID_BODY + 1;
    public static final int INVALID_HEADER = INVALID_METHOD + 1;
    public static final int SERVER_ERROR = INVALID_HEADER + 1;

}
