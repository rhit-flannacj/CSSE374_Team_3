package org.team3;

import org.objectweb.asm.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;

class DecoratorDetectionRule implements Analyzer {
    @Override
    public void analyze(String filePath) {
        try {
            ClassReader reader = new ClassReader(Files.newInputStream(Paths.get(filePath)));
            reader.accept(new ClassVisitor(Opcodes.ASM9) {
                private String className;
                private String superName;
                private List<String> interfaces = new ArrayList<>();
                private List<String> fields = new ArrayList<>();

                @Override
                public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                    this.className = name;
                    this.superName = superName;
                    this.interfaces.addAll(Arrays.asList(interfaces));
                }

                @Override
                public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                    fields.add(descriptor);
                    return super.visitField(access, name, descriptor, signature, value);
                }

                @Override
                public void visitEnd() {
                    for (String iface : interfaces) {
                        if (fields.contains("L" + iface + ";")) {
                            System.out.println("Detected Decorator Pattern: " + className);
                        }
                    }
                }
            }, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}