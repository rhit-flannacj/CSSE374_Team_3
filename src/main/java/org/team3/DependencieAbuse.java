package org.team3;

import javax.swing.*;

public class DependencieAbuse implements Analyzer {
    public int max = 0;
    private boolean warningShown = false;

    @Override
    public void formatClass(MyClass myClass) {
        int dependencyCount = myClass.associations.size();
        if (dependencyCount > max) {
            myClass.additionalText = (myClass.additionalText == null ? "" : myClass.additionalText) + " <dependency abuse>";
            
            if (!warningShown) {
                warningShown = true;
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame("Warning");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.add(new JLabel("Dependency abuse count exceeded maximum allowed."));
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
