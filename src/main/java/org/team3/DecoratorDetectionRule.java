package org.team3;

public class DecoratorDetectionRule extends AnalyzerDecorator {

    public DecoratorDetectionRule(Analyzer inner) {
        super(inner);
    }

    @Override
    protected void applyDecoration(MyClass myClass) {
        if (myClass.isDecorator) {
            myClass.color = "#blue";
            myClass.additionalText = "<decorator>";
            myClass.lineColor = "[#blue]";
        }
    }
}