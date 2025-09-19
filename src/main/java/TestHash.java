import org.mindrot.jbcrypt.BCrypt;

public class TestHash {
    public static void main(String[] args) {
        String hash = BCrypt.hashpw("1234", BCrypt.gensalt());
        System.out.println(hash);
    }
}
