package org.team3;

import org.objectweb.asm.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Scraper extends ClassVisitor {
    private MyClass newClass;
    private String path;

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
        return super.visitField(access, name, descriptor, signature, value);
    }
}
