package org.team3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomActionListener implements ActionListener {
    private final ArrayList<JCheckBox> boxes;
    private final ArrayList<Analyzer> rules;
    private final ArrayList<Analyzer> selected;
    private final JFrame frame;
    private final String path;

    public CustomActionListener(ArrayList<JCheckBox> boxes, ArrayList<Analyzer> rules,
                                ArrayList<Analyzer> selected, JFrame frame, String path) {
        this.boxes = boxes;
        this.rules = rules;
        this.selected = selected;
        this.frame = frame;
        this.path = path;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JCheckBox box : boxes) {
            if (box.isSelected()) {
                selected.add(rules.get(boxes.indexOf(box)));
            }
        }
        frame.dispose();
        try {
            // Assuming Compiler is defined elsewhere
            Compiler.getInstance().createData(path, selected);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}