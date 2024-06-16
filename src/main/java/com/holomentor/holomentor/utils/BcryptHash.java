package com.holomentor.holomentor.utils;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import org.hibernate.cfg.Environment;

public class BcryptHash {

    private final String password;
    private final String secret = Environment.getProperties().getProperty("env.hash");

    public BcryptHash(String password) {
        this.password = password;
    }

    public String hash() {
        BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

        Hash hash = Password.hash(this.password)
                .addPepper(this.secret)
                .with(bcrypt);

        return hash.getResult();
    }

    public Boolean verify(String hash) {
        BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

        return Password.check(this.password, hash)
                .addPepper(this.secret)
                .with(bcrypt);
    }
}
