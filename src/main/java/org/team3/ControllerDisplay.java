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
        JLabel label1 = new JLabel("Enter max number of dependencies");
        JTextField text1 = new JTextField();
        JLabel label2 = new JLabel("Enter max number of singletons");
        JTextField text2 = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for(JCheckBox box : boxes) {
             panel.add(box);
        }

        panel.add(label1);
        panel.add(text1);
        panel.add(label2);
        panel.add(text2);

        frame.add(panel);

        JButton select = new JButton("Select");
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JCheckBox box : boxes) {
                    if (box.isSelected()) {
                        selected.add(rules.get(boxes.indexOf(box)));
                    }
                }

                frame.dispose();

                try {
                    if (text1.getText() == null || text1.getText().trim().isEmpty()) {
                        rules.get(2).setMax(Integer.MAX_VALUE); // Set to maximum (no restriction)
                    } else {
                        rules.get(2).setMax(Integer.parseInt(text1.getText().trim()));
                    }

                    if (text2.getText() == null || text2.getText().trim().isEmpty()) {
                        rules.get(3).setMax(Integer.MAX_VALUE); // Set to maximum (no restriction)
                    } else {
                        rules.get(3).setMax(Integer.parseInt(text2.getText().trim()));
                    }

                    Compiler.getInstance().createData(path, selected);
                } catch (NumberFormatException ex) {
                    // Handle invalid numeric input
                    JOptionPane.showMessageDialog(frame,
                            "Please enter valid numeric values for dependencies and singletons.",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        System.out.println("Selected Rules:");
        for (Analyzer rule : selected) {
            System.out.println("Rule: " + rule.getClass().getName());
        }
        frame.add(select);
        frame.setSize(300,300);
        frame.show();
        return selected;
    }
}
