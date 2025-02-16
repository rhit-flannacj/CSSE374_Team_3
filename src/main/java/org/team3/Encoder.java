package org.team3;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.DeflaterOutputStream;

public class Encoder {
    public String encode(String uml) throws Exception {
        String encoded = encodePlantUML(uml);
        return "http://www.plantuml.com/plantuml/svg/~1" + encoded;
    }

    private static String encodePlantUML(String uml) throws Exception {
        byte[] encoded = uml.getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DeflaterOutputStream deflater = new DeflaterOutputStream(byteStream);

        try {
            deflater.write(encoded);
        } catch (Throwable var7) {
            try {
                deflater.close();
            } catch (Throwable var6) {
                var7.addSuppressed(var6);
            }

            throw var7;
        }

        deflater.close();
        return encode64(byteStream.toByteArray());
    }

    private static String encode64(byte[] data) {
        String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_";
        StringBuilder result = new StringBuilder();
        int i = 0;

        while(i < data.length) {
            int b1 = data[i++] & 255;
            result.append(alphabet.charAt(b1 >> 2));
            if (i < data.length) {
                int b2 = data[i++] & 255;
                result.append(alphabet.charAt((b1 & 3) << 4 | b2 >> 4));
                if (i < data.length) {
                    int b3 = data[i++] & 255;
                    result.append(alphabet.charAt((b2 & 15) << 2 | b3 >> 6));
                    result.append(alphabet.charAt(b3 & 63));
                } else {
                    result.append(alphabet.charAt((b2 & 15) << 2));
                }
            } else {
                result.append(alphabet.charAt((b1 & 3) << 4));
            }
        }

        return result.toString();
    }
}
