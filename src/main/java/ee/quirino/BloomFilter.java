package ee.quirino;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class BloomFilter {

    static Map<String, Boolean> filter = new HashMap<>();
    private final Integer SIZE = 6;

    public boolean input(String inputString) {
        String hash = HashGenerator.md5(inputString);
        String beginning = hash.substring(0, SIZE);
        String ending =  hash.substring(hash.length() - SIZE);
        filter.put(beginning, true);
        filter.put(ending, true);
        return true;
    }

    public boolean verify(String strToBeVerified){
        String hash = HashGenerator.md5(strToBeVerified);
        String beginning = hash.substring(0, SIZE);
        String ending =  hash.substring(hash.length() - SIZE);
        if(!filter.containsKey(beginning) || !filter.containsKey(ending)) {
            return false;
        }
        return true;
    }
}
