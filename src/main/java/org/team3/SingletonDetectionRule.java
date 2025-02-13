package org.team3;

import org.objectweb.asm.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.File;

public class SingletonDetectionRule implements Analyzer {
    @Override
    public void analyze(String filePath) {
        File f = new File(filePath);
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files == null) return;
            for (File sub : files) {
                analyze(sub.getAbsolutePath());
            }
            return;
        }
        if (f.isFile() && f.getName().endsWith(".class")) {
            try {
                ClassReader reader = new ClassReader(Files.newInputStream(Paths.get(filePath)));
                reader.accept(new ClassVisitor(Opcodes.ASM9) {
                    private String className;
                    private boolean hasPrivateConstructor;
                    private boolean hasStaticInstance;
                    private boolean hasStaticGetter;

                    @Override
                    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                        className = name;
                    }

                    @Override
                    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                        if ((access & Opcodes.ACC_PRIVATE) != 0 && name.equals("<init>")) {
                            hasPrivateConstructor = true;
                        }
                        if ((access & Opcodes.ACC_STATIC) != 0) {
                            hasStaticGetter = true;
                        }
                        return super.visitMethod(access, name, descriptor, signature, exceptions);
                    }

                    @Override
                    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                        if ((access & Opcodes.ACC_STATIC) != 0) {
                            hasStaticInstance = true;
                        }
                        return super.visitField(access, name, descriptor, signature, value);
                    }

                    @Override
                    public void visitEnd() {
                        if (hasPrivateConstructor && hasStaticInstance && hasStaticGetter) {
                            System.out.println("Detected Singleton Pattern: " + className);
                        }
                    }
                }, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
