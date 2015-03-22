package tw.edu.ncu.cc.oauth.server.helper


class StringHelper {

    static String first( String str, int length ) {
        if( str.length() <= length ) {
            return str
        } else {
            str.substring( 0, length )
        }
    }
}
