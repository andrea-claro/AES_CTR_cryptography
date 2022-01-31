import aes.ctr.AES_128_CTR_Converter;
import aes.utils.GZIPHelper;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.util.Base64;
import java.util.logging.Logger;

public class Test_RFI_decompression_and_decrypt_AES_128_CTR {

    private static final Logger LOGGER = Logger.getLogger(Test_RFI_decompression_and_decrypt_AES_128_CTR.class.getName());

    private static final String gzipFileCompressed = "./resources/rfi/IT_RFI_1234567890121_20211118T165547_Event_IV_bb56efebb64cdd23e7d6611734....gzip";
    private static final String gzipFileCompressed_2 = "./resources/rfi/IT_RFI_1234567890121_20211118T165547_Event_IV_416c6d61766976614032303231213f5f.txt";
    private static final String gzipFileDecompressed = "./resources/rfi/rfi_decompressed.txt";

    private static final String gzipFileDecompressedAndBase64Decoded = "./resources/rfi/rfi_base64_decoded.txt";
    private static final String gzipFileFullyDecoded = "./resources/rfi/rfi_fully_decoded.txt";

    private static final String CIPHER_ALGORITHM = "AES/CTR/NoPadding";
    private static final String SECURE_KEY_ALGORITHM = "AES";
    private static final String SECURE_KEY = "***";

    public static void main(String[] args) {

        try {
            String bufferedLine;
            String bufferedText = "";


            GZIPHelper.unGunzipFile(gzipFileCompressed, gzipFileDecompressed);


            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(gzipFileDecompressed)));


            while ((bufferedLine = bufferedReader.readLine()) != null)
                bufferedText = bufferedText + bufferedLine;


            //String textBase64Decoded = new String(Base64.getDecoder().decode(bufferedText));//DatatypeConverter.parseBase64Binary(bufferedText));//Base64.getDecoder().decode(bufferedText));


            //LOGGER.info("\n\nFile trasformato in stringa codificato, decodificato:\n" + textBase64Decoded);


            SecretKey key = AES_128_CTR_Converter.generateKeyFromHexString(SECURE_KEY, SECURE_KEY_ALGORITHM);
            IvParameterSpec iv = AES_128_CTR_Converter.generateIvFromFile(new File(gzipFileCompressed_2));


            String textFullyDecoded = AES_128_CTR_Converter.decodeString(key, iv, CIPHER_ALGORITHM, bufferedText);

            FileOutputStream fileOutputStream = new FileOutputStream(gzipFileFullyDecoded);
            fileOutputStream.write(textFullyDecoded.getBytes(), 0, textFullyDecoded.getBytes().length);


            LOGGER.info("\n\nFile trasformato in stringa codificato, decodificato:");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
