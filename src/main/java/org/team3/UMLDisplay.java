package org.team3;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UMLDisplay {
    public void renderUML(String uml) throws Exception {
        Encoder encoder = new Encoder();
        URL url = new URL(encoder.encode(uml));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() == 200) {
            InputStream inputStream = connection.getInputStream();
            Image image = ImageIO.read(inputStream);
            JFrame frame = new JFrame("Linter");
            frame.setDefaultCloseOperation(3);
            frame.setSize(800, 600);
            JLabel label = new JLabel(new ImageIcon(image));
            frame.add(label, "Center");
            frame.setVisible(true);
        }
    }
}
