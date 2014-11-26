package tw.edu.ncu.cc.oauth.server.helper;

import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

public class TokenGenerator {

    private StringKeyGenerator generator = KeyGenerators.string();

    public String generate() {
        return generator.generateKey();
    }

}
