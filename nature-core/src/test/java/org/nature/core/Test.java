/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2017, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : Test.java</p>
 * <p>Package     : org.nature.core</p>
 * @Creator cdlilinhai1 2017年10月8日 下午11:23:18
 * @Version  V1.0  
 */ 

package org.nature.core;

import java.security.KeyPair;

import pers.linhai.nature.utils.RSAUtils;

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
        KeyPair keyPair = RSAUtils.getKeyPair(512);
        String publicKeyStr = RSAUtils.getPublicKey(keyPair);  
        String privateKeyStr = RSAUtils.getPrivateKey(keyPair); 
        System.out.println("RSA公钥Base64编码:" + publicKeyStr);  
        System.out.println("RSA私钥Base64编码:" + privateKeyStr);
        
        //hello, i am infi, good night!加密  
        String message = "hello, i am infi, good night!";  
        
        for (int i = 0; i < 10; i++ )
        {
            //用公钥加密  
            byte[] publicEncrypt = RSAUtils.publicEncrypt(message.getBytes(), keyPair.getPublic());
            
            //加密后的内容Base64编码  
            String byte2Base64 = RSAUtils.byte2Base64Str(publicEncrypt);  
            System.out.println("公钥加密并Base64编码的结果：" + byte2Base64); 
            System.out.println(byte2Base64.length());
            
            //加密后的内容Base64解码  
            byte[] base642Byte = RSAUtils.str2Base64Byte(byte2Base64); 
            
            //用私钥解密  
            byte[] privateDecrypt = RSAUtils.privateDecrypt(base642Byte, keyPair.getPrivate());  

            //解密后的明文  
            System.out.println("解密后的明文: " + new String(privateDecrypt)); 
        }
    }
}
