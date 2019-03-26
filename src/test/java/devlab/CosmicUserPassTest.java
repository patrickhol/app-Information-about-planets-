package devlab;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CosmicUserPassTest {

    @Test
    public void getPass() {
        getPassHash();
    }

    public PasswordEncoder getPassHash() {
        PasswordEncoder pe = new BCryptPasswordEncoder();
        System.out.println(pe.encode("admin"));
        return new BCryptPasswordEncoder();

    }
}
