package view;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {
    static String secretKey;
    public static void main(String[] args) {

    }

    public static String getSecretKey() {
        if ((secretKey==null)||secretKey.equals("")) {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[32]; // 256-bit key
            random.nextBytes(key);
            secretKey = Base64.getEncoder().encodeToString(key);
            System.out.println("Generated Secret Key: " + secretKey);
            return secretKey;
        }else {
            return secretKey;
        }
    }
}
