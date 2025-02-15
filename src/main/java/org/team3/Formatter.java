package org.team3;

import java.util.ArrayList;

public class Formatter {
    private UMLDisplay umlDisplay = new UMLDisplay();
    private ArrayList<MyClass> classes;
    private String uml;
    private static Formatter formatter;

    private Formatter() {
    }

    public void format(ArrayList<MyClass> classes, ArrayList<Analyzer> selectedRules) throws Exception {
        this.classes = classes;
        compileClassDetails(selectedRules);
        uml = "@startuml\n";
        for (MyClass curClass : classes) {
            classHead(curClass);
            classFields(curClass);
            classMethods(curClass);
            uml += "}\n";
            supperClasses(curClass);
            interfaces(curClass);
            associations(curClass);
            dependencies(curClass);
        }
        uml += "@enduml";
        System.out.println(uml);
        umlDisplay.renderUML(uml);
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
            uml += curClass.supperClassName + " <|-- " + curClass.className + "\n";
        }
    }

    private void interfaces(MyClass curClass) {
        for (String curInterface : curClass.interfaces) {
            uml += curInterface.substring(curInterface.lastIndexOf("/") + 1) + " <-. " + curClass.className + "\n";
        }
    }

    private void associations(MyClass curClass) {
        for (String curAssociation : curClass.associations) {
            uml += curAssociation + " <-- " + curClass.className + "\n";
        }
    }

    private void dependencies(MyClass curClass) {
        for (String curField : curClass.fields) {
            for(MyClass tempClass : classes) {
                String temp = curField.substring(curField.indexOf(':') + 2);
                if(temp.endsWith(";")) {
                    temp = temp.substring(0, temp.length() - 1);
                }
                if(temp.equals(tempClass.className) && !curClass.className.equals(tempClass.className)) {
                    uml += tempClass.className + " <-up- " + curClass.className + "\n";
                    break;
                }
            }
        }
    }
}

