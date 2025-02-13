package org.team3.test;

interface Component {
    void operation();
}

class ConcreteComponent implements Component {
    @Override
    public void operation() {
        System.out.println("ConcreteComponent operation");
    }
}

public class MyDecorator implements Component {
    private Component wrapped;  // same interface

    public MyDecorator(Component wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void operation() {
        System.out.println("Decorator before...");
        wrapped.operation();
        System.out.println("Decorator after...");
    }
}
