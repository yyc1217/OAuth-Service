package tw.edu.ncu.cc.oauth.server.helper;

import java.util.List;

public class ResultBuilder {

    private List list;

    private ResultBuilder( List list ) {
        this.list = list;
    }

    public static ResultBuilder result( List list ) {
        return new ResultBuilder( list );
    }

    public <T> T singleResult( Class<T> tClass ) {
        return ( list.size() == 1 ? tClass.cast( list.get( 0 ) ) : null );
    }

}
