import aes.ctr.AES_128_CTR_Converter;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;

public class Test_AES_128_CTR {

    static final File inputFile = new File(
            ".\\resources\\EMS_DE_DB_9180540618423_20011217T093500_Response_IV_416c6d61766976614032303231213f5f.xml");
    static final File outputFileEncoded = new File(".\\resources\\outputEncoded.txt");
    static final File outputFileDecoded = new File(".\\resources\\outputDecoded.txt");

    private static final String CIPHER_ALGORITHM = "AES/CTR/NoPadding";
    private static final String SECURE_KEY_ALGORITHM = "AES";
    private static final String SECURE_KEY = "***";

    public static void main(String[] args) {

        try{
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            FileOutputStream fileOutputStream = new FileOutputStream(outputFileEncoded);

            SecretKey key = AES_128_CTR_Converter.generateKeyFromHexString(SECURE_KEY, SECURE_KEY_ALGORITHM);

            IvParameterSpec iv = AES_128_CTR_Converter.generateIvFromFile(inputFile);

            AES_128_CTR_Converter.encodeFile(key, iv, CIPHER_ALGORITHM, inputFile, outputFileEncoded);
            AES_128_CTR_Converter.decodeFile(key, iv, CIPHER_ALGORITHM, outputFileEncoded, outputFileDecoded);

            System.out.println("File operati");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));

            String inputString = "";
            String str = "";
            while((inputString = bufferedReader.readLine()) != null) {
                str = str + inputString;
            }

            System.out.println("----------------------------------------\nFile trasformato in stringa:\n"+str+"\n");

            String codedString = AES_128_CTR_Converter.encodeString(key, iv, CIPHER_ALGORITHM, str);
            String decodedString = AES_128_CTR_Converter.decodeString(key, iv, CIPHER_ALGORITHM, codedString);

            System.out.println("File trasformato in stringa, codificato:\n"+codedString+"\n");
            System.out.println("File trasformato in stringa codificato, decodificato:\n"+decodedString);

            fileInputStream.close();
            fileOutputStream.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
