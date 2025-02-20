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
            for (MyClass sc : singletonClasses) {
                if (sc.additionalText == null || !sc.additionalText.contains("<singleton abuse>")) {
                    sc.additionalText = (sc.additionalText == null ? "" : sc.additionalText) + " <singleton abuse>";
                }

            }
            if (!warningShown) {
                warningShown = true;
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame("Warning");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.add(new JLabel("Singleton abuse count exceeded maximum allowed."));
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
