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
        checkDuplicate();
        uml = "@startuml\n";
        for (MyClass curClass : classes) {
            classHead(curClass);
            classFields(curClass);
            classMethods(curClass);
            uml += "}\n";
            supperClasses(curClass);
            interfaces(curClass);
            associations(curClass);
        }
        uml += "@enduml";
        System.out.println(uml);
        umlDisplay.renderUML(uml);
    }

    private void checkDuplicate() {
        for (MyClass curClass : classes) {
            for (String association : curClass.associations) {
                String associationName = association.substring(association.lastIndexOf('.') + 1);
                for (String curInterface : curClass.interfaces) {
                    if (curInterface.substring(curInterface.lastIndexOf("/") + 1).equals(associationName)) {
                        curClass.associations.remove(association);
                    }
                }
            }
        }
    }

    private void fill() {
        for (MyClass myClass : classes) {
            if (myClass.className.endsWith("$1")) {
                myClass.className = myClass.className.substring(0, myClass.className.length() - 2);
            }
            classNames.add(myClass.className);
        }
    }

    public static Formatter getInstance() {
        if (formatter == null) {
            formatter = new Formatter();
        }
        return formatter;
    }

    private void compileClassDetails(ArrayList<Analyzer> rules) {
        for (MyClass curClass : classes) {
            for (Analyzer rule : rules) {
                rule.formatClass(curClass);
            }
        }
        for (MyClass decoratorClass : classes) {
            if (decoratorClass.isDecorator && !decoratorClass.interfaces.isEmpty()) {
                for (String ifcPath : decoratorClass.interfaces) {
                    String ifcDots = ifcPath.replace('/', '.');
                    String ifcName = ifcDots.substring(ifcDots.lastIndexOf('.') + 1);
                    for (MyClass maybeInterface : classes) {
                        if (maybeInterface.className.equals(ifcName)) {
                            maybeInterface.color = " #blue";
                            maybeInterface.additionalText = " <decorator>";
                        }
                    }
                }
            }
        }
    }

    private void classHead(MyClass curClass) {
        if (curClass.isInterface) {
            uml += "interface " + curClass.className + curClass.additionalText + curClass.color + " {\n";
            uml += "  .. Table Decorator ..\n";
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
                String interfaceName = curInterface.substring(curInterface.lastIndexOf("/") + 1);
                // Determine if the target interface class has been marked as a decorator interface
                boolean isDecoratorInterface = classes.stream()
                        .filter(c -> c.className.equals(interfaceName))
                        .anyMatch(c -> c.additionalText != null && c.additionalText.contains("<decorator>"));
                if (curClass.isDecorator && isDecoratorInterface) {
                    uml += interfaceName + " <-up[#blue]. " + curClass.className + "\n";
                } else {
                    uml += interfaceName + " <-up. " + curClass.className + "\n";
                }
            }
        }
    }


    private void associations(MyClass curClass) {
        for (String curAssociation : curClass.associations) {
            String assoc = curAssociation.replace('/', '.');
            String associationName = assoc.substring(assoc.lastIndexOf('.') + 1);

            if (classNames.contains(associationName) && !associationName.equals(curClass.className)) {
                for (MyClass assocClass : classes) {
                    if (assocClass.className.equals(associationName)) {
                        boolean isDecoratorInterface = curClass.isDecorator &&
                                curClass.interfaces.stream().anyMatch(ifc -> {
                                    String ifcDots = ifc.replace('/', '.');
                                    String ifcName = ifcDots.substring(ifcDots.lastIndexOf('.') + 1);
                                    return ifcName.equals(associationName);
                                });

                        if (isDecoratorInterface) {
                            uml += associationName + " <-up[#blue]. " + curClass.className + "\n"; // Blue color for decorator lines
                        } else {
                            uml += associationName + " <-up- " + curClass.className + "\n";
                        }
                        break;
                    }
                }
            }
        }
    }
}
