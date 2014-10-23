package tw.edu.ncu.cc.oauth.server.rule;

public class PermissionRule {

    public static int LENGTH = 32;
    public static char NO  = ' ';
    public static char YES = '1';
    public static String INIT_PATTERN;

    static {
        StringBuilder stringBuilder = new StringBuilder( LENGTH );
        for( int i = 0; i < LENGTH; i ++ ) {
            stringBuilder.append( NO );
        }
        INIT_PATTERN = stringBuilder.toString();
    }
}
