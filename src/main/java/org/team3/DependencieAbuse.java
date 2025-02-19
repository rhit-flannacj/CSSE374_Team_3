package org.team3;

public class DependencieAbuse implements Analyzer{
    public int max = 0;
    @Override
    public void formatClass(MyClass myClass) {

    }

    @Override
    public void setMax(int max) {
        this.max = max;
    }
}
