/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : AESUtils.java</p> <p>Package :
 * pers.linhai.nature.utils</p>
 * @Creator lilinhai 2018年4月9日 下午1:52:21
 * @Version V1.0
 */

package pers.linhai.nature.utils;


import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * AES对称加密解密算法
 * <p>ClassName      : AESUtils</p>
 * @author lilinhai 2018年4月9日 下午1:52:21
 * @version 1.0
 */
public abstract class AESUtils
{

    /**
     * 默认的key大小
     */
    private static final int DEFAULT_KEY_SIZE = 128;

    /**
     * 加密
     * @param content  需要加密的内容
     * @param password 加密密码,盐值
     * @return
     */
    public static byte[] encrypt(String content, String password)
    {
        return encrypt(content, password, DEFAULT_KEY_SIZE);
    }
    
    /**
     * 加密
     * @param content  需要加密的内容
     * @param password 加密密码,盐值
     * @return
     */
    public static String encryptToBase64Str(String content, String password)
    {
        return encryptToBase64Str(content, password, DEFAULT_KEY_SIZE);
    }
    
    /**
     * 加密
     * @param content  需要加密的内容
     * @param password 加密密码,盐值
     * @return
     */
    public static String encryptToBase64Str(String content, String password, int keySize)
    {
        return Base64Utils.encode(encrypt(content, password, keySize));
    }
    
    /**
     * 加密
     * @param content  需要加密的内容
     * @param password 加密密码,盐值
     * @return
     */
    public static byte[] encrypt(String content, String password, int keySize)
    {
        try
        {
            // 加密
            return encrypt(content, new SecureRandom(password.getBytes("utf-8")), keySize);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 加密
     * @param content  需要加密的内容
     * @param password 加密密码,盐值
     * @return
     */
    public static byte[] encrypt(String content, SecureRandom secureRandom, int keySize)
    {
        try
        {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keySize, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            
            Cipher cipher = Cipher.getInstance("AES");
            
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byteContent = content.getBytes("utf-8");
            
            // 加密
            return cipher.doFinal(byteContent);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param content  待解密内容
     * @param password 解密密钥,盐值
     * @return
     */
    public static byte[] decrypt(byte[] content, String password)
    {
        return decrypt(content, password, DEFAULT_KEY_SIZE);
    }
    
    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥,盐值
     * @return
     */
    public static String decryptFromBase64Str(String content, String password)
    {
        return decryptFromBase64Str(content, password, DEFAULT_KEY_SIZE);
    }
    
    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥,盐值
     * @return
     */
    public static String decryptFromBase64Str(String content, String password, int keySize)
    {
        return new String(decrypt(Base64Utils.decodeToByte(content), password, keySize));
    }
    
    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥,盐值
     * @return
     */
    public static byte[] decrypt(byte[] content, String password, int keySize)
    {
        try
        {
            // 解密
            return decrypt(content, new SecureRandom(password.getBytes("utf-8")), keySize);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥,盐值
     * @return
     */
    public static byte[] decrypt(byte[] content, SecureRandom secureRandom, int keySize)
    {
        try
        {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keySize, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            
            Cipher cipher = Cipher.getInstance("AES");
            
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            // 解密
            return cipher.doFinal(content); 
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
