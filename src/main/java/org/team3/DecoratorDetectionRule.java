package org.team3;

import org.objectweb.asm.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DecoratorDetectionRule implements Analyzer {
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
                    private List<String> interfaces = new ArrayList<>();
                    private List<String> fields = new ArrayList<>();

                    @Override
                    public void visit(int version, int access, String name, String signature, String superName, String[] ifaces) {
                        className = name;
                        if (ifaces != null) {
                            interfaces.addAll(Arrays.asList(ifaces));
                        }
                    }

                    @Override
                    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                        fields.add(descriptor);
                        return super.visitField(access, name, descriptor, signature, value);
                    }

                    @Override
                    public void visitEnd() {
                        for (String iface : interfaces) {
                            String ifaceDescriptor = "L" + iface + ";";
                            if (fields.contains(ifaceDescriptor)) {
                                System.out.println("Detected Decorator Pattern: " + className);
                                break;
                            }
                        }
                    }
                }, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
