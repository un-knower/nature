/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : TestAes.java</p>
 * <p>Package     : org.nature.core</p>
 * @Creator lilinhai 2018年4月9日 下午2:07:40
 * @Version  V1.0  
 */ 

package org.nature.core;

import pers.linhai.nature.encryption.reversible.RASAlgorithm;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : TestAes</p>
 * @author lilinhai 2018年4月9日 下午2:07:40
 * @version 1.0
 */
public class TestRsa
{

    public static void main(String[] args)
    {
        String phone = "13668142438";
        String pass = "12345";
//        System.out.println(AESUtils.encryptToBase64Str(phone, pass));
//        System.out.println(AESUtils.decryptFromBase64Str(AESUtils.encryptToBase64Str(phone, pass), pass));
        RASAlgorithm rsaEncryptionAlgorithm = new RASAlgorithm(pass, 512);
        
        long s1 = System.currentTimeMillis();
        for (int i = 0; i < 10 * 1000; i++ )
        {
            String s = rsaEncryptionAlgorithm.encryptToBase64String(phone);
            System.out.println(s);
            rsaEncryptionAlgorithm.decryptFromBase64String(s);
        }
        System.out.println(System.currentTimeMillis() - s1);
        /*for (int i = 0; i < 10000; i++ )
        {
            new Thread()
            {

                *//** 
                 * <p>Overriding Method: lilinhai 2018年4月9日 下午11:47:04</p>
                 * <p>Title: run</p>
                 * <p>Description: TODO</p> 
                 * @see java.lang.Thread#run()
                 *//* 
                public void run()
                {
                    String s = rsaEncryptionAlgorithm.encryptToBase64Str(phone);
                    System.out.println(rsaEncryptionAlgorithm.decryptFromBase64Str(s));
                }
                
            }.start();
        }*/
    }
}
