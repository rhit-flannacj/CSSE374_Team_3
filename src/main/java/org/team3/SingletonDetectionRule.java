package org.team3;

class SingletonDetectionRule extends AnalyzerDecorator {

    public SingletonDetectionRule(Analyzer inner) {
        super(inner);
    }

    @Override
    protected void applyDecoration(MyClass myClass) {
        if (myClass.isSingleton) {
            myClass.color = "#red";
            myClass.additionalText = "<singleton>";
            myClass.lineColor = "[red]";
        }
    }
}

