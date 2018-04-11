/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : NonInvertible.java</p>
 * <p>Package : pers.linhai.nature.security</p>
 * @Creator lilinhai 2018年4月11日 上午9:41:18
 * @Version V1.0
 */

package pers.linhai.nature.encrypt.irreversible;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import pers.linhai.nature.constant.Charsets;
import pers.linhai.nature.utils.Base64Utils;
import pers.linhai.nature.utils.HexUtils;


/**
 * 不可逆加密算法
 * <p>ClassName      : NonInvertible</p>
 * @author lilinhai 2018年4月11日 上午9:41:18
 * @version 1.0
 */
public class IrreversibleAlgorithm
{

    /**
     * 算法
     */
    protected String algorithm;
    
    /**
     * 盐值
     */
    protected byte[] saltByte;
    
    /**
     * <p>Title        : IrreversibleAlgorithm lilinhai 2018年4月11日 下午10:51:55</p>
     */ 
    public IrreversibleAlgorithm(String salt)
    {
        if (salt != null)
        {
            this.saltByte = salt.getBytes(Charsets.UTF_8);
        }
    }

    /**
     * 加密
     * <p>Title         : getDigest lilinhai 2018年4月11日 下午10:52:55</p>
     * @param algorithm
     * @return 
     * MessageDigest
     */
    protected MessageDigest getDigest(String algorithm)
    {
        try
        {
            return MessageDigest.getInstance(algorithm);
        }
        catch (final NoSuchAlgorithmException e)
        {
            throw new IllegalArgumentException(e);
        }
    }
    
    /**
     * 加密成16进制字符串，默认是大写输出结果
     * <p>Title         : encryptToHexString lilinhai 2018年4月11日 下午10:45:55</p>
     * @param data
     * @return 
     * String
     */
    public String encryptToHexString(String data)
    {
        return encryptToHexString(data, false);
    }
    
    /**
     * 加密成16进制字符串
     * <p>Title         : encryptToHexString lilinhai 2018年4月11日 下午10:45:39</p>
     * @param data
     * @param toLowerCase
     * @return 
     * String
     */
    public String encryptToHexString(String data, boolean toLowerCase)
    {
        return HexUtils.encode(encrypt(data), toLowerCase);
    }
    
    /**
     * 加密成base64字符串 
     * <p>Title         : encryptToBase64String lilinhai 2018年4月11日 下午10:45:28</p>
     * @param data
     * @return 
     * String
     */
    public String encryptToBase64String(String data)
    {
        return Base64Utils.encode(encrypt(data));
    }
    
    /**
     * 加密
     * <p>Title         : encrypt lilinhai 2018年4月11日 下午10:38:18</p>
     * @param data
     * @return 
     * byte[]
     */
    public byte[] encrypt(String data) 
    {
        MessageDigest ctx = getDigest(algorithm);
        ctx.update(data.getBytes(Charsets.UTF_8));
        if (saltByte != null)
        {
            ctx.update(saltByte);
        }
        return ctx.digest();
    }
}
