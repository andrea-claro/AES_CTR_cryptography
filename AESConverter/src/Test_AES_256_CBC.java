import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Test_AES_256_CBC {

    private static final String SECURE_KEY = "72B894FC6F9CD2BCC63CFF33DFC0CE33DA2BB5FE86E4703721DA670DEF9CC3B4";
    private static final String IV = "15C17719D893F6CDC05914948345614A";

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
