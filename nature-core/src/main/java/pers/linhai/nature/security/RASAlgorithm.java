/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : AESEncryptionAlgorithm.java</p>
 * <p>Package     : pers.linhai.nature.security</p>
 * @Creator lilinhai 2018年4月9日 下午11:16:57
 * @Version  V1.0  
 */ 

package pers.linhai.nature.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import javax.crypto.Cipher;

/**
 *RSA非对称加密解密算法
 * <p>ClassName      : AESEncryptionAlgorithm</p>
 * @author lilinhai 2018年4月9日 下午11:16:57
 * @version 1.0
 */
public class RASAlgorithm extends SecurityAlgorithm
{

    /**
     * 默认的key大小
     */
    private static final int DEFAULT_KEY_SIZE = 512;

    /**
     * <p>Title        : AESEncryptionAlgorithm lilinhai 2018年4月9日 下午11:20:34</p>
     * @param cipher 
     */ 
    public RASAlgorithm(String secret)
    {
        this(secret, DEFAULT_KEY_SIZE);
    }
    
    /**
     * <p>Title        : AESEncryptionAlgorithm lilinhai 2018年4月9日 下午11:20:34</p>
     * @param cipher 
     */ 
    public RASAlgorithm(String secret, int keySize)
    {
        try
        {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keySize, new SecureRandom(getBytes(secret)));
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            
            // 初始化加密的Cipher
            this.encryptCipher = Cipher.getInstance("RSA");
            this.encryptCipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            
            // 初始化解密的Cipher
            this.decryptCipher = Cipher.getInstance("RSA");
            this.decryptCipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
