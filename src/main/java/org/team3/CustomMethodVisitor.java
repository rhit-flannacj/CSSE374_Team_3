package org.team3;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class CustomMethodVisitor extends MethodVisitor {
    public CustomMethodVisitor(int api, MethodVisitor mv) {
        super(api, mv);
    }
    
   
}