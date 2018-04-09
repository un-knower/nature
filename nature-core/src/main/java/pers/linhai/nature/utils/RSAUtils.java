/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : RSAUtils.java</p> <p>Package :
 * pers.linhai.nature.utils</p>
 * @Creator lilinhai 2018年4月9日 上午10:14:24
 * @Version V1.0
 */

package pers.linhai.nature.utils;


import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import org.springframework.util.Base64Utils;


/**
 * RSA非对称加密解密工具类
 * <p>ClassName      : RSAUtils</p>
 * @author lilinhai 2018年4月9日 上午10:14:24
 * @version 1.0
 */
public abstract class RSAUtils
{

    /**
     * 默认的key大小
     */
    private static final int DEFAULT_KEY_SIZE = 2048;

    /**
     * 生成秘钥对
     * <p>Title         : getKeyPair lilinhai 2018年4月9日 上午10:19:02</p>
     * @return
     * @throws Exception 
     * KeyPair
     */
    public static KeyPair getKeyPair()
    {
        return getKeyPair(DEFAULT_KEY_SIZE);
    }

    /**
     * 生成秘钥对
     * <p>Title         : getKeyPair lilinhai 2018年4月9日 上午10:19:02</p>
     * @return
     * @throws Exception 
     * KeyPair
     */
    public static KeyPair getKeyPair(int keySize)
    {
        try
        {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keySize);
            return keyPairGenerator.generateKeyPair();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取公钥(Base64编码)
     * <p>Title         : getPublicKey lilinhai 2018年4月9日 上午10:21:22</p>
     * @param keyPair
     * @return 
     * String
     */
    public static String getPublicKey(KeyPair keyPair)
    {
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64Str(bytes);
    }

    /**
     * 获取私钥(Base64编码)
     * <p>Title         : getPrivateKey lilinhai 2018年4月9日 上午10:21:37</p>
     * @param keyPair
     * @return 
     * String
     */
    public static String getPrivateKey(KeyPair keyPair)
    {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64Str(bytes);
    }

    /**
     * 将Base64编码后的公钥转换成PublicKey对象
     * <p>Title         : string2PublicKey lilinhai 2018年4月9日 上午10:21:44</p>
     * @param pubStr
     * @return
     * @throws InvalidKeySpecException 
     * @throws Exception 
     * PublicKey
     */
    public static PublicKey string2PublicKey(String pubStr)
    {
        try
        {
            byte[] keyBytes = str2Base64Byte(pubStr);
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(keyBytes));
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将Base64编码后的私钥转换成PrivateKey对象
     * <p>Title         : string2PrivateKey lilinhai 2018年4月9日 上午10:23:07</p>
     * @param priStr
     * @return
     * PrivateKey
     */
    public static PrivateKey string2PrivateKey(String priStr)
        throws InvalidKeySpecException
    {
        try
        {
            byte[] keyBytes = str2Base64Byte(priStr);
            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥加密
     * <p>Title         : publicEncrypt lilinhai 2018年4月9日 上午10:23:58</p>
     * @param content
     * @param publicKey
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     * byte[]
     */
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(content);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 私钥解密
     * <p>Title         : privateDecrypt lilinhai 2018年4月9日 上午10:25:24</p>
     * @param content
     * @param privateKey
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     * byte[]
     */
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey)
        throws InvalidKeyException,
        IllegalBlockSizeException,
        BadPaddingException
    {
        try
        {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(content);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字节数组转Base64编码
     * <p>Title         : byte2Base64Str lilinhai 2018年4月9日 上午10:25:30</p>
     * @param bytes
     * @return 
     * String
     */
    public static String byte2Base64Str(byte[] bytes)
    {
        return Base64Utils.encodeToString(bytes);
    }

    /**
     * Base64编码转字节数组
     * <p>Title         : str2Base64Byte lilinhai 2018年4月9日 上午10:25:37</p>
     * @param base64Key
     * @return 
     * byte[]
     */
    public static byte[] str2Base64Byte(String base64Key)
    {
        return Base64Utils.decodeFromString(base64Key);
    }
}
