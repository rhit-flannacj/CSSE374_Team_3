package org.team3;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class CustomMethodVisitor extends MethodVisitor {
    private MyClass newClass; // Reference to class metadata

    public CustomMethodVisitor(int api, MethodVisitor mv, MyClass newClass) {
        super(api, mv);
        this.newClass = newClass;
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        // Check if the constructor takes an instance of its own interface
        if (opcode == Opcodes.ALOAD && !newClass.interfaces.isEmpty()) {
            // We check if the constructor parameter type is one of the interfaces
            newClass.isPotentialDecorator++;
        }
        super.visitVarInsn(opcode, var);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        // Check if the class contains a field of its own interface type
        if (!newClass.interfaces.isEmpty() && descriptor.contains(newClass.interfaces.get(0))) {
            newClass.isPotentialDecorator++;
        }
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        if (!owner.startsWith("java/")) {
            // If the method call is to a non-Java standard class, it's an association
            newClass.associations.add(owner.replace("/", "."));
        }
        if (opcode == Opcodes.INVOKEVIRTUAL || opcode == Opcodes.INVOKEINTERFACE) {
            if (!newClass.interfaces.isEmpty() && owner.equals(newClass.interfaces.get(0))) {
                newClass.isPotentialDecorator++;
            }
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        if (opcode == Opcodes.NEW) {
            // Detected an object creation, so it's an association
            newClass.associations.add(type.replace("/", "."));
        }
        super.visitTypeInsn(opcode, type);
    }

}