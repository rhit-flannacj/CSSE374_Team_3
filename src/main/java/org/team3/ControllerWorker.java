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
        rules.add(new DecoratorDetectionRule());
        rules.add(new SingletonDetectionRule());
        rules.add(new DependencieAbuse());
        rules.add(new SingeltonAbuse());
    }

}
