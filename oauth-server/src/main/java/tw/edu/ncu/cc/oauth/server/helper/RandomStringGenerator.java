package tw.edu.ncu.cc.oauth.server.helper;

import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.stereotype.Service;

@Service
public class RandomStringGenerator implements StringKeyGenerator{

    private StringKeyGenerator generator = KeyGenerators.string();

    @Override
    public String generateKey() {
        return generator.generateKey() + generator.generateKey();
    }

}
