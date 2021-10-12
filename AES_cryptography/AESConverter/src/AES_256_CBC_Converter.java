import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES_256_CBC_Converter {

    public static byte[] encodeString(SecretKey key, IvParameterSpec iv, String algorithm, String input) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

/*
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return DatatypeConverter.printBase64Binary((cipherText));
 */

        byte[] cipherText = cipher.doFinal(input.getBytes());
        //return Base64.getEncoder().encodeToString(cipherText);
        return cipherText;
    }

    /*
     * Metodo di decodifica da stringa
     */
    public static String decodeString(SecretKey key, IvParameterSpec iv, String algorithm, String cipherText) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

/*
        byte[] plainText = cipher.doFinal(DatatypeConverter.parseBase64Binary(cipherText));
        return new String(plainText);
*/
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }

    public static SecretKey generateKeyFromHexString(String secureKey, String secureKeyAlgorithm){
        byte[] decodedKey =  //new byte[] {
              //(byte)0xcc, (byte)0xbd, (byte)0x48, (byte)0x98, (byte)0xc3, (byte)0xa7, (byte)0x44, (byte)0x7a, (byte)0xcf, (byte)0x97, (byte)0xd4, (byte)0x61, (byte)0xdb, (byte)0x34, (byte)0x7b, (byte)0x20, (byte)0xae, (byte)0xe5, (byte)0x6b, (byte)0xce, (byte)0xa5, (byte)0x06, (byte)0x2f, (byte)0x01, (byte)0xb7, (byte)0xce, (byte)0xc9, (byte)0x6f, (byte)0x5b, (byte)0xad, (byte)0xf0, (byte)0x29 };
        //Base64.getDecoder().decode(secureKey);
                DatatypeConverter.parseHexBinary(secureKey);

        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, secureKeyAlgorithm);
        return key;
    }

    public static IvParameterSpec generateIVFromHexString(String ivString) {
        byte[] decodedIV =  new byte[16];
        for (int i = 0; i < decodedIV.length; i++){
            int index = i * 2;
            int j = Integer.parseInt(ivString.substring(index, index+2), 16);
            decodedIV[i] = (byte) j;
        }

        IvParameterSpec iv = new IvParameterSpec(decodedIV);
        return iv;
    }
}
