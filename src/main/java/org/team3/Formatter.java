package org.team3;

import java.util.ArrayList;

public class Formatter {
    public String  format(ArrayList<MyClass> classes) {
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
        System.out.println(uml);
        return uml;
    }
}

