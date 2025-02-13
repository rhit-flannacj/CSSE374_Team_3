package org.team3.test;

public class MySingleton {
    private static MySingleton instance;

    private MySingleton() {
        // private constructor
    }

    public static MySingleton getInstance() {
        if (instance == null) {
            instance = new MySingleton();
        }
        return instance;
    }
}
