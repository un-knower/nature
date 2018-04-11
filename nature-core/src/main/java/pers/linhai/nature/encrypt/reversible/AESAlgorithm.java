/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : AESEncryptionAlgorithm.java</p>
 * <p>Package     : pers.linhai.nature.security</p>
 * @Creator lilinhai 2018年4月9日 下午11:16:57
 * @Version  V1.0  
 */ 

package pers.linhai.nature.encrypt.reversible;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES对称加密解密可逆算法
 * <p>ClassName      : AESEncryptionAlgorithm</p>
 * @author lilinhai 2018年4月9日 下午11:16:57
 * @version 1.0
 */
public class AESAlgorithm extends ReversibleAlgorithm
{

    /**
     * 默认的key大小
     */
    private static final int DEFAULT_KEY_SIZE = 128;

    /**
     * <p>Title        : AESEncryptionAlgorithm lilinhai 2018年4月9日 下午11:20:34</p>
     * @param cipher 
     */ 
    public AESAlgorithm(String secret)
    {
        this(secret, DEFAULT_KEY_SIZE);
    }
    
    /**
     * <p>Title        : AESAlgorithm lilinhai 2018年4月11日 下午9:07:55</p>
     * @param salt
     * @param keySize
     */
    public AESAlgorithm(String salt, int keySize)
    {
        try
        {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keySize, new SecureRandom(getBytes(salt)));
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            
            // 初始化加密的Cipher
            this.encryptCipher = Cipher.getInstance("AES");
            this.encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            
            // 初始化解密的Cipher
            this.decryptCipher = Cipher.getInstance("AES");
            this.decryptCipher.init(Cipher.DECRYPT_MODE, key);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
