package org.team3;

import java.util.ArrayList;

public class ControllerWorker {
    ArrayList<Analyzer> rules = new ArrayList<>();
    String filePath;
    boolean highlightSingleton;
    boolean highlightDecorator;

    // Constructor accepts flags to decide which rule(s) to apply.
    public ControllerWorker(String filePath, boolean highlightSingleton, boolean highlightDecorator) {
        this.filePath = filePath;
        this.highlightSingleton = highlightSingleton;
        this.highlightDecorator = highlightDecorator;
        fillRules();
    }

    public void fillRules() {
        Analyzer base = myClass -> {};

        if (highlightSingleton && !highlightDecorator) {
            rules.add(new SingletonDetectionRule(base));
        } else if (!highlightSingleton && highlightDecorator) {
            rules.add(new DecoratorDetectionRule(base));
        } else if (highlightSingleton && highlightDecorator) {
            rules.add(new SingletonDetectionRule(base));
            rules.add(new DecoratorDetectionRule(base));
        }
    }
}