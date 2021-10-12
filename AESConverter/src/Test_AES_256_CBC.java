import org.codehaus.jettison.json.JSONObject;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Test_AES_256_CBC {

    private static final String SECURE_KEY = "72B894FC6F9CD2BCC63CFF33DFC0CE33DA2BB5FE86E4703721DA670DEF9CC3B4";
    private static final String IV = "15C17719D893F6CDC05914948345614A";

    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";
    private static final String SECURE_KEY_ALGORITHM = "AES";

    public static void main(String[] args) {

        try {
            SecretKey key = AES_256_CBC_Converter.generateKeyFromHexString(SECURE_KEY, SECURE_KEY_ALGORITHM);
            IvParameterSpec iv = AES_256_CBC_Converter.generateIVFromHexString(IV);

            String input = new String(Files.readAllBytes(Paths.get("resources/snap_test.txt")));

            byte[] codedString = AES_256_CBC_Converter.encodeString(key, iv, CIPHER_ALGORITHM, input);
            Files.write(Paths.get("resources/snap_byte.txt"), codedString);

            String newString = new String(codedString);
            String base64String = Base64.getEncoder().encodeToString(codedString);
            //System.out.println(newString);
            //System.out.println(base64String);

            String decodedString = AES_256_CBC_Converter.decodeString(key, iv, CIPHER_ALGORITHM, base64String);
            //System.out.println(decodedString);

            JSONObject jo = new JSONObject();
            jo.put("StringaByte", newString);

            System.out.println(jo.getString("StringaByte"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
