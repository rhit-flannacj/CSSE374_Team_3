package org.team3;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DependencieAbuse implements Analyzer {
    public int max = 0;
    private List<MyClass> dependencyClasses = new ArrayList<>();
    private boolean warningShown = false;

    @Override
    public void formatClass(MyClass myClass) {
        int dependencyCount = myClass.associations.size();
        if (dependencyCount > max) {
            dependencyClasses.add(myClass);
        }
        if (dependencyClasses.size() > max) {
            if (!warningShown) {
                warningShown = true;
                SwingUtilities.invokeLater(() -> {
                    StringBuilder message = new StringBuilder("<html>Dependency abuse count exceeded maximum allowed.<br>Affected classes:<br>");
                    for (MyClass dc : dependencyClasses) {
                        message.append(dc.className).append("<br>");
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
