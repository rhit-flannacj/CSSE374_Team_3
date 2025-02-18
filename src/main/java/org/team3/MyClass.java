package org.team3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyClass {
    public String className;
    public String supperClassName;
    public ArrayList<String> interfaces = new ArrayList<>();
    public ArrayList<String> fields = new ArrayList<>();
    public ArrayList<String> methods = new ArrayList<>();
    public Set<String> associations = new HashSet<>();
    public boolean isInterface;
    public boolean isSingleton;
    public boolean isDecorator;
    public String color = "white";
    public String additionalText = "";
    public String lineColor = "black";
    public int isPotentialDecorator;
}
