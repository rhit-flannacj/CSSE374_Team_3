package org.team3;

import java.util.List;

public class DecoratorDetectionRule implements Analyzer {
    private List<MyClass> allClasses;

    public DecoratorDetectionRule() {
        super();
    }

    // This method allows us to inject the full list of classes.
    public void setClasses(List<MyClass> allClasses) {
        this.allClasses = allClasses;
    }

    @Override
    public void formatClass(MyClass myClass) {
        if (allClasses == null) {
            return;
        }
        if (myClass.isDecorator) {
            // For each interface that the decorator implements...
            for (String ifcPath : myClass.interfaces) {
                String ifcName = ifcPath.substring(ifcPath.lastIndexOf('/') + 1);
                // Only mark the interface if the decorator actually holds a field of that interface type
                if (myClass.fields != null && myClass.fields.stream().anyMatch(field -> field.contains(": " + ifcName))) {
                    // Look up the corresponding MyClass for the interface
                    for (MyClass maybeInterface : allClasses) {
                        if (maybeInterface.className.equals(ifcName)) {
                            // Insert a leading space so PlantUML parses it as " #blue"
                            maybeInterface.color = " #blue";
                            maybeInterface.additionalText = " <decorator>";
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setMax(int max) {

    }
}