package org.team3;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Compiler {
    private ArrayList<Analyzer> selectedRules;
    private static Compiler compiler;
    private static ArrayList<MyClass> classes;

    private Compiler()  {
    }

    public void createData(String path, ArrayList<Analyzer> selectedRules) throws Exception {
        File directory = new File(path);
        classes = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (file.getName().endsWith(".class")) {
                MyClass newClass = new MyClass();
                analyze(file, newClass, directory);
                classes.add(newClass);
            }
        }
        Formatter.getInstance().format(classes, selectedRules);
    }

    public static void analyze(File classFile, MyClass newClass, File directory) {
        try (FileInputStream inputStream = new FileInputStream(classFile)) {
            ClassReader classReader = new ClassReader(inputStream);
            classReader.accept(new Scraper(Opcodes.ASM9, newClass, directory.getPath()), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Compiler getInstance(){
        if (compiler == null) {
            compiler = new Compiler();
        }
        return compiler;
    }
}
