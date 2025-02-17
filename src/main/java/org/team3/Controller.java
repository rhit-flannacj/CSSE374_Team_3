package org.team3;

import java.util.ArrayList;

public class Controller {
    public static void main(String[] args) throws InterruptedException {
        ControllerDisplay display = new ControllerDisplay();
        String filePath = display.findFilePath();
        ControllerWorker worker = new ControllerWorker(filePath, true, true);
        ArrayList<Analyzer> rules = worker.rules;
        display.getSelectedRules(rules, filePath);
    }
}