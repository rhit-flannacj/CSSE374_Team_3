package org.team3;

class SingletonDetectionRule implements Analyzer {

    public SingletonDetectionRule() {
        super();
    }

    @Override
    public void formatClass(MyClass myClass) {
        if(myClass.isSingleton) {
            myClass.color = "#lightred";
            myClass.additionalText = "<singleton>";
            myClass.lineColor = "[#red]";
        }
    }
}

