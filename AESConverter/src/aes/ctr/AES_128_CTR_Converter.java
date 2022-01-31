package aes.ctr;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AES_128_CTR_Converter {

    /*
     * Metodo di codifica partendo da file
     */
    public static void encodeFile(SecretKey key, IvParameterSpec iv, String algorithm, File inputFile, File outputFile)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        byte[] buffer = new byte[64];

        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }

        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            outputStream.write(outputBytes);
        }

        inputStream.close();
        outputStream.close();
    }

    /*
     * Metodo di decodifica partendo da file
     */
    public static void decodeFile(SecretKey key, IvParameterSpec iv, String algorithm, File inputFile, File outputFile)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        byte[] buffer = new byte[64];

        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }

        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            outputStream.write(outputBytes);
        }

        inputStream.close();
        outputStream.close();
    }

    /*
     * Metodo di codifica da stringa
     */
    public static String encodeString(SecretKey key, IvParameterSpec iv, String algorithm, String input) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    /*
     * Metodo di decodifica da stringa
     */
    public static String decodeString(SecretKey key, IvParameterSpec iv, String algorithm, String cipherText) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    /*
     * Crea una chiave per il vettore di inizializzazione Randomica
     */
    public static IvParameterSpec generateRandomIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        IvParameterSpec temp = new IvParameterSpec(iv);
        return temp;
    }

    public static IvParameterSpec generateIvFromFile(File inputFile){
        String fileName = inputFile.getName();

        /*
         * Questi indici tengono conto del fatto che la stringa IV esadecimale sia l'ultima stringa nel nome del file
         */
        int ivIndex = fileName.lastIndexOf("IV");
        int dotIndex = fileName.lastIndexOf(".");

        String ivFromFileName = (String) fileName.subSequence(ivIndex+3, dotIndex);
        byte[] decodedFileName =  new byte[16];
        for (int i = 0; i < decodedFileName.length; i++){
            int index = i * 2;
            int j = Integer.parseInt(ivFromFileName.substring(index, index+2), 16);
            decodedFileName[i] = (byte) j;
        }

        IvParameterSpec iv = new IvParameterSpec(decodedFileName);
        return iv;
    }


    public static SecretKey generateKeyFromHexString(String secureKey, String secureKeyAlgorithm){
        byte[] decodedKey = Base64.getDecoder().decode(secureKey);
        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, secureKeyAlgorithm);
        return key;
    }
}
