package org.team3;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SingeltonAbuse implements Analyzer {
    public int max = 0;
    private List<MyClass> singletonClasses = new ArrayList<>();
    private boolean warningShown = false;

    @Override
    public void formatClass(MyClass myClass) {
        if (myClass.isSingleton) {
            singletonClasses.add(myClass);
        }
        if (singletonClasses.size() > max) {
            if (!warningShown) {
                warningShown = true;
                SwingUtilities.invokeLater(() -> {
                    StringBuilder message = new StringBuilder("<html>Singleton abuse count exceeded maximum allowed.<br>Affected classes:<br>");
                    for (MyClass sc : singletonClasses) {
                        message.append(sc.className).append("<br>");
                    }
                    message.append("</html>");
                    JFrame frame = new JFrame("Warning");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.add(new JLabel(message.toString()));
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                });
            }
        }
    }

    @Override
    public void setMax(int max) {
        this.max = max;
    }
}