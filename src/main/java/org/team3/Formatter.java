package org.team3;

import java.util.ArrayList;

public class Formatter {
    UMLDisplay umlDisplay = new UMLDisplay();
    private static Formatter formatter;

    private Formatter() {}

    public void format(ArrayList<MyClass> classes, ArrayList<Analyzer> selectedRules) throws Exception {
        String uml = "@startuml\n";
        for (MyClass curClass : classes) {
            if(curClass.isInterface) {
                uml += "interface " + curClass.className + " {\n";
            } else {
                uml += "class " + curClass.className + " {\n";
            }
            for (String curField : curClass.fields) {
                uml += curField + "\n";
            }
            for (String curMethod : curClass.methods) {
                uml += curMethod + "\n";
            }
            uml += "}\n";
            if (curClass.supperClassName != null) {
                uml += curClass.supperClassName + " <|-- " + curClass.className + "\n";
            }
            for (String curInterface : curClass.interfaces) {
                uml += curInterface.substring(curInterface.lastIndexOf("/") + 1) + " <-. " + curClass.className + "\n";
            }
            for (String curAssociation : curClass.associations) {
                uml += curAssociation + " <-- " + curClass.className + "\n";
            }
        }
        uml += "@enduml";
        //System.out.println(uml);
        umlDisplay.renderUML(uml);
    }

    public static Formatter getInstance(){
        if (formatter == null) {
            formatter = new Formatter();
        }
        return formatter;
    }

}

