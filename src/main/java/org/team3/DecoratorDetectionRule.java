package org.team3;


class DecoratorDetectionRule implements Analyzer {

    public DecoratorDetectionRule() {
        super();
    }

    @Override
    public void formatClass(MyClass myClass) {
        if(myClass.isDecorator) {
            myClass.color = "#blue";
            myClass.additionalText = "<decorator>";
        }
    }
}