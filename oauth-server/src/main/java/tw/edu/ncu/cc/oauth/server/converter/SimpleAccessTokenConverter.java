package tw.edu.ncu.cc.oauth.server.converter;

import org.springframework.core.convert.converter.Converter;
import tw.edu.ncu.cc.oauth.data.accesstoken.SimpleAccessToken;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;

public class SimpleAccessTokenConverter implements Converter< AccessTokenEntity, SimpleAccessToken > {

    @Override
    public SimpleAccessToken convert( AccessTokenEntity source ) {
        return null;
    }

}
