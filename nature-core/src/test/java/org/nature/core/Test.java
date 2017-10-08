/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2017, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : Test.java</p>
 * <p>Package     : org.nature.core</p>
 * @Creator cdlilinhai1 2017年10月8日 下午11:23:18
 * @Version  V1.0  
 */ 

package org.nature.core;

import pers.linhai.nature.core.reflect.ConstructorAccess;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : Test</p>
 * @author cdlilinhai1 2017年10月8日 下午11:23:18
 * @version 1.0
 */
public class Test
{

    public static void main(String[] args) throws Exception
    {
        ConstructorAccess<Persion> pc = ConstructorAccess.get(Persion.class);
        long s = System.currentTimeMillis();
        for (int i = 0; i < 1000000000l; i++ )
        {
            pc.newInstance();
        }
        System.out.println(System.currentTimeMillis() - s);
        
        
        long s1 = System.currentTimeMillis();
//        Constructor<Persion> c = Persion.class.getConstructor();
//        c.setAccessible(false);
        for (int i = 0; i < 1000000000l; i++ )
        {
            new Persion();
        }
        System.out.println(System.currentTimeMillis() - s1);
    }
}
