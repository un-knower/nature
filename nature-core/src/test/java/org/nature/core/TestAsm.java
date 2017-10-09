/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2017, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : TestAsm.java</p> <p>Package :
 * org.nature.core</p>
 * @Creator cdlilinhai1 2017年10月8日 下午9:59:22
 * @Version V1.0
 */

package org.nature.core;


import java.io.File;
import java.io.FileOutputStream;

import pers.linhai.nature.asm.ClassWriter;
import pers.linhai.nature.asm.Opcodes;


/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : TestAsm</p>
 * @author cdlilinhai1 2017年10月8日 下午9:59:22
 * @version 1.0
 */
public class TestAsm
{

    public static void main(String[] args)
        throws Exception
    {
        // 生成一个类只需要ClassWriter组件即可
        ClassWriter cw = new ClassWriter(0);
        
        // 通过visit方法确定类的头部信息
        cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE
            , "com/asm3/Comparable", null, "java/lang/Object", new String[] {"com/asm3/Mesurable"});
        
        // 定义类的属性
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "LESS", "I", null, new Integer(-1)).visitEnd();
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "EQUAL", "I", null, new Integer(0)).visitEnd();
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "GREATER", "I", null, new Integer(1)).visitEnd();
        
        // 定义类的方法
        cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd(); 
        
        // 使cw类已经完成
        // 将cw转换成字节数组写到文件里面去
        byte[] data = cw.toByteArray();
        File file = new File("D://Comparable.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();

    }
}
