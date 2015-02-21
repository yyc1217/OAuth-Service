package helper;

public class CustomMockMvcResponseMatchers {

    private CustomMockMvcResponseMatchers() {

    }

    public static ResponseURLMatcher url() {
        return new ResponseURLMatcher();
    }

}
