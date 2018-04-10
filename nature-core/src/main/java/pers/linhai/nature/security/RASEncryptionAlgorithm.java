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

import pers.linhai.nature.utils.Base64Utils;

/**
 *RSA非对称加密解密算法
 * <p>ClassName      : AESEncryptionAlgorithm</p>
 * @author lilinhai 2018年4月9日 下午11:16:57
 * @version 1.0
 */
public class RASEncryptionAlgorithm extends EncryptionAlgorithm
{

    /**
     * 默认的key大小
     */
    private static final int DEFAULT_KEY_SIZE = 512;

    /**
     * <p>Title        : AESEncryptionAlgorithm lilinhai 2018年4月9日 下午11:20:34</p>
     * @param cipher 
     */ 
    public RASEncryptionAlgorithm(String secret)
    {
        this(secret, DEFAULT_KEY_SIZE);
    }
    
    /**
     * <p>Title        : AESEncryptionAlgorithm lilinhai 2018年4月9日 下午11:20:34</p>
     * @param cipher 
     */ 
    public RASEncryptionAlgorithm(String secret, int keySize)
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
    
    /**
     * 将目标字符串加密，输出base64格式的字符串
     * <p>Title         : encryptToBase64Str lilinhai 2018年4月9日 下午11:34:30</p>
     * @param source
     * @return 
     * String
     */
    public String encryptToBase64Str(String source) 
    {
        return Base64Utils.encode(encrypt(source));
    }
    
    /**
     * 加密，返回明文被加密后的字节数组
     * <p>Title         : encrypt lilinhai 2018年4月9日 下午11:33:09</p>
     * @param source
     * @return 
     * byte[]
     */
    public synchronized byte[] encrypt(String source)
    {
        try
        {
            return encryptCipher.doFinal(getBytes(source));
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 将一个base64格式的加密字符串解密成明文
     * <p>Title         : decryptFromBase64Str lilinhai 2018年4月9日 下午11:48:26</p>
     * @param content
     * @return 
     * String
     */
    public String decryptFromBase64Str(String content)
    {
        return new String(decrypt(Base64Utils.decodeToByte(content)), UTF_8_SET);
    }
    
    /**
     * 解密，返回明文对应的字节数组
     * <p>Title         : decrypt lilinhai 2018年4月9日 下午11:38:29</p>
     * @param source
     * @return 
     * byte[]
     */
    public synchronized byte[] decrypt(byte[] source)
    {
        try
        {
            return decryptCipher.doFinal(source);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
