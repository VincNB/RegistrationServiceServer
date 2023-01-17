package nmbai.accounts;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.util.Objects;

public class AccountAuthenticator {
    private static final int DEFAULT_ROUNDS = 10;
    private final int saltRounds;
    private final SecureRandom random = new SecureRandom();

    public AccountAuthenticator() {
        this(DEFAULT_ROUNDS);
    }

    public AccountAuthenticator(int rounds) {
        if (rounds <= 0) {
            throw new IllegalArgumentException("rounds must be greater than 0");
        }
        this.saltRounds = rounds;
    }

    public String generatePassword(String plaintext) {
        return BCrypt.hashpw(Objects.requireNonNull(plaintext), BCrypt.gensalt(saltRounds, random));
    }

    public boolean authenticate(String inputPassword, String storedPassword) {
        return BCrypt.checkpw(Objects.requireNonNull(inputPassword), Objects.requireNonNull(storedPassword));
    }

}
