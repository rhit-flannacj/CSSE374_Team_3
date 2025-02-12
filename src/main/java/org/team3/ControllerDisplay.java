package org.team3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControllerDisplay {

    //From Colin Flannagan Hw3
    //Following section from https://www.geeksforgeeks.org/java-swing-jfilechooser/
    public String findFilePath() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Source Root");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile().getAbsolutePath();
    }
    //End

    public ArrayList<Analyzer> getSelectedRules(ArrayList<Analyzer> rules, String path) {
        ArrayList<Analyzer> selected = new ArrayList<>();
        ArrayList<JCheckBox> boxes = new ArrayList<>();
        JFrame frame = new JFrame("Select Desired Rules");
        frame.setLayout(new FlowLayout());
        for(Analyzer rule: rules) {
             boxes.add(new JCheckBox(rule.getClass().getName().substring(rule.getClass().getName().lastIndexOf('.') + 1)));
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for(JCheckBox box : boxes) {
             panel.add(box);
        }

        frame.add(panel);

        JButton select = new JButton("Select");
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(JCheckBox box : boxes) {
                    if(box.isSelected()) {
                        selected.add(rules.get(boxes.indexOf(box)));
                    }
                }
                frame.dispose();
                try {
                    Compiler compiler = new Compiler(path, selected);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        frame.add(select);
        frame.setSize(300,300);
        frame.show();
        return selected;
    }
}
