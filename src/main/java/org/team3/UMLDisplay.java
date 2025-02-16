package org.team3;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UMLDisplay {
    public void renderUML(String uml) throws Exception {
        String save = "C:\\Users\\flannacj\\Downloads\\uml.svg";
        Encoder encoder = new Encoder();
        URL url = new URL(encoder.encode(uml));
        System.out.println(url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() == 200) {
            try (InputStream inputStream = connection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(save)) {
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            }
        }
    }
}
