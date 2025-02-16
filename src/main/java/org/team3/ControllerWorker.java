package org.team3;

import java.util.ArrayList;

public class ControllerWorker {
    ArrayList<Analyzer> rules = new ArrayList<>();
    String filePath;

    public ControllerWorker(String filePath) {
        this.filePath = filePath;
        fillRules();
    }

    public void fillRules() {
        Analyzer base = myClass -> {};
        rules.add(new SingletonDetectionRule(base));
        rules.add(new DecoratorDetectionRule(null));
    }

}
