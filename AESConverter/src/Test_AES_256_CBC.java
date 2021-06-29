import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Test_AES_256_CBC {

    private static final String SECURE_KEY = "DBF3C21770D81FE133C4D8C39103425C7797E0C79966CD27";
    private static final String IV = "2520F776B1D062E909C94B6DD7D600EE";

    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";
    private static final String SECURE_KEY_ALGORITHM = "AES";

    public static void main(String[] args) {

        try{
            SecretKey key = AES_256_CBC_Converter.generateKeyFromHexString(SECURE_KEY, SECURE_KEY_ALGORITHM);
            IvParameterSpec iv = AES_256_CBC_Converter.generateIVFromHexString(IV);

            String str = "Almaviva digitaltec";

            String codedString = AES_256_CBC_Converter.encodeString(key, iv, CIPHER_ALGORITHM, str);
            String decodedString = AES_256_CBC_Converter.decodeString(key, iv, CIPHER_ALGORITHM, codedString);

            System.out.println(codedString);
            System.out.println(decodedString);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
