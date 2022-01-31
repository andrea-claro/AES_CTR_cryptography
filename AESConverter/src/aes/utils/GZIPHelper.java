package aes.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

public class GZIPHelper {

    private static final Logger LOGGER = Logger.getLogger(GZIPHelper.class.getName());

    public static void unGunzipFile(String compressedFilePath, String decompressedFilePath) throws Exception {

        FileInputStream fileInputStream;
        GZIPInputStream gZIPInputStream;
        FileOutputStream fileOutputStream;


        byte[] buffer = new byte[1024];
        int bytes_read;


        fileInputStream = new FileInputStream(compressedFilePath);
        fileOutputStream = new FileOutputStream(decompressedFilePath);
        gZIPInputStream = new GZIPInputStream(fileInputStream);


        while ((bytes_read = gZIPInputStream.read(buffer)) > 0)
            fileOutputStream.write(buffer, 0, bytes_read);


        LOGGER.info("\n\nIl file:\n" + compressedFilePath + "\nè stato decompresso nel file:\n" + decompressedFilePath);


        gZIPInputStream.close();
        fileOutputStream.close();
        fileInputStream.close();
    }
}
