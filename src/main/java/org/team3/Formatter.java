package org.team3;

import java.util.ArrayList;

public class Formatter {
    private UMLDisplay umlDisplay = new UMLDisplay();
    private ArrayList<MyClass> classes;
    private ArrayList<String> classNames = new ArrayList<>();
    private String uml;
    private static Formatter formatter;

    private Formatter() {
    }

    public void format(ArrayList<MyClass> classes, ArrayList<Analyzer> selectedRules) throws Exception {
        this.classes = classes;
        fill();
        compileClassDetails(selectedRules);
        uml = "@startuml\n";
        for (MyClass curClass : this.classes) {
            classHead(curClass);
            classFields(curClass);
            classMethods(curClass);
            uml += "}\n";
            supperClasses(curClass);
            interfaces(curClass);
            associations(curClass);
        }
        uml += "@enduml";
//        System.out.println(uml);
        umlDisplay.renderUML(uml);
    }

    private void fill() {
        for (MyClass myClass : classes) {
            if (myClass.className.endsWith("$1")) {
                myClass.className = myClass.className.substring(0, myClass.className.length() - 2);
            }
            classNames.add(myClass.className);
        }
    }

    public static Formatter getInstance(){
        if (formatter == null) {
            formatter = new Formatter();
        }
        return formatter;
    }

    private void compileClassDetails(ArrayList<Analyzer> rules) {
        for (MyClass curClass: classes) {
            for (Analyzer rule: rules) {
                rule.formatClass(curClass);
            }
        }
    }

    private void classHead(MyClass curClass) {
        if(curClass.isInterface) {
            uml += "interface " + curClass.className + curClass.additionalText + curClass.color + " {\n";
        } else {
            uml += "class " + curClass.className + curClass.additionalText + curClass.color + " {\n";
        }
    }

    private void classFields(MyClass curClass) {
        for (String curField : curClass.fields) {
            uml += curField + "\n";
        }
    }

    private void classMethods(MyClass curClass) {
        for (String curMethod : curClass.methods) {
            uml += curMethod + "\n";
        }
    }

    private void supperClasses(MyClass curClass) {
        if (curClass.supperClassName != null) {
            uml += curClass.supperClassName + " <|-up- " + curClass.className + "\n";
        }
    }

    private void interfaces(MyClass curClass) {
        for (String curInterface : curClass.interfaces) {
            if (!curInterface.contains("ActionListener")) {
                uml += curInterface.substring(curInterface.lastIndexOf("/") + 1) + " <-up. " + curClass.className + "\n";
            }
        }
    }

    private void associations(MyClass curClass) {
        for (String curAssociation : curClass.associations) {
            String associationName = curAssociation.substring(curAssociation.lastIndexOf('.') + 1);
            if(classNames.contains(associationName) && !associationName.equals(curClass.className)) {
                for (MyClass assocClass : classes) {
                    if(assocClass.className.equals(associationName)) {
                        uml += associationName + " <-up-" + curClass.className + "\n";
                        break;
                    }
                }
            }
        }
    }
}

