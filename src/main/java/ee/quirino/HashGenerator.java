package ee.quirino;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {


    static String md5(String stringToHash){
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");

        messageDigest.update(stringToHash.getBytes());
        byte[] digiest = messageDigest.digest();
        return new BigInteger(1, digiest).toString(16);
        } catch (Exception e) {
           return null;
        }
    }

}
