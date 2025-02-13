package org.team3;

import org.objectweb.asm.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scraper extends ClassVisitor {
    private MyClass newClass;
    private String path;
    private boolean hasPrivateConstructor = false;
    private boolean hasStaticInstance = false;
    private boolean hasStaticGetter = false;
    private List<String> fields = new ArrayList<>();


    protected Scraper(int api, MyClass newClass, String path) {
        super(api);
        this.newClass = newClass;
        this.path = path;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println(name);
        newClass.className = name.substring(name.lastIndexOf('/') + 1);
        newClass.isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
        if (!superName.startsWith("java") && !superName.startsWith("org")) {
            newClass.supperClassName = superName.substring(superName.lastIndexOf('/') + 1);
        }
        newClass.interfaces = new ArrayList<>(Arrays.asList(interfaces));
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        newClass.methods.add(" - " + name + " : " + descriptor.substring(descriptor.lastIndexOf('/') + 1));
        if ((access & Opcodes.ACC_PRIVATE) != 0 && name.equals("<init>")) {
            hasPrivateConstructor = true;
        }
        if ((access & Opcodes.ACC_STATIC) != 0 && descriptor.contains("(")) {
            hasStaticGetter = true;
        }

        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        String type = Type.getType(descriptor).getClassName();
        if (type.startsWith(path.substring(path.lastIndexOf('/') + 1))) {
            newClass.associations.add(type.substring(type.lastIndexOf('.') + 1));
        } else {
            System.out.println(type + "   " + path.substring(path.lastIndexOf('/') + 1));
            newClass.fields.add(" - " + name + " : " + descriptor.substring(descriptor.lastIndexOf('/') + 1));
        }
        if ((access & Opcodes.ACC_STATIC) != 0) {
            hasStaticInstance = true;
        }
        fields.add(descriptor);
        return super.visitField(access, name, descriptor, signature, value);
    }

    public void visitEnd() {
        if (hasPrivateConstructor && hasStaticInstance && hasStaticGetter) {
            System.out.println("Detected Singleton Pattern: " + this.newClass.className);
            this.newClass.isSingleton = true;
        }
        for (String i : newClass.interfaces) {
            if (fields.contains(i)) {
                System.out.println("Detected Decorator Pattern: " + this.newClass.className);
                this.newClass.isSingleton = true;
            }
        }
    }
}
