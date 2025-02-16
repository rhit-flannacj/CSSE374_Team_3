package org.team3;

public abstract class AnalyzerDecorator implements Analyzer {
    protected Analyzer inner;

    public AnalyzerDecorator(Analyzer inner) {
        this.inner = inner;
    }

    @Override
    public void formatClass(MyClass myClass) {
        if (inner != null) {
            inner.formatClass(myClass);
        }
        applyDecoration(myClass);
    }

    protected abstract void applyDecoration(MyClass myClass);
}