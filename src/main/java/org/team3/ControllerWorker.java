package org.team3;

import java.util.ArrayList;

public class ControllerWorker {
    ArrayList<Analyzer> rules = new ArrayList<>();
    String filePath;

    public ControllerWorker(String filePath) {
        this.filePath = filePath;
        fillRules();
        analyzeCode();
    }

    public void fillRules() {
        rules.add(new DecoratorDetectionRule());
        rules.add(new SingletonDetectionRule());
    }

     public void analyzeCode() {
        for (Analyzer rule : rules) {
            rule.analyze(filePath);
        }

}
}
