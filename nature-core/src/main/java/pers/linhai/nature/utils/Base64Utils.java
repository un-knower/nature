/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : Base64Utils.java</p> <p>Package
 * : pers.linhai.nature.utils</p>
 * @Creator lilinhai 2018年4月9日 下午12:50:57
 * @Version V1.0
 */

package pers.linhai.nature.utils;


import java.nio.charset.Charset;
import java.util.Base64;


/**
 * Base64工具类
 * <p>ClassName      : Base64Utils</p>
 * @author lilinhai 2018年4月9日 下午12:50:57
 * @version 1.0
 */
public abstract class Base64Utils
{

    private static final Charset UTF_8_SET = Charset.forName("utf-8");
    
    /**
     * Base64加密
     * <p>Title         : encode lilinhai 2018年4月9日 下午1:05:03</p>
     * @param source
     * @return 
     * String
     */
    public static String encode(String source)
    {
        return encode(source.getBytes(UTF_8_SET));
    }

    /**
     * Base64加密
     * <p>Title         : encode lilinhai 2018年4月9日 下午1:05:03</p>
     * @param source
     * @return 
     * String
     */
    public static String encode(byte[] source)
    {
        return new String(Base64.getEncoder().encode(source), UTF_8_SET);
    }

    /**
     * Base64解密
     * <p>Title         : decode lilinhai 2018年4月9日 下午1:05:15</p>
     * @param source
     * @return 
     * String
     */
    public static String decode(String source)
    {
        return decode(source.getBytes(UTF_8_SET));
    }

    /**
     * Base64解密
     * <p>Title         : decode lilinhai 2018年4月9日 下午1:05:15</p>
     * @param source
     * @return 
     * String
     */
    public static String decode(byte[] source)
    {
        return new String(decodeToByte(source), UTF_8_SET);
    }

    /**
     * Base64解密
     * <p>Title         : decode lilinhai 2018年4月9日 下午1:05:15</p>
     * @param source
     * @return 
     * String
     */
    public static byte[] decodeToByte(byte[] source)
    {
        return Base64.getDecoder().decode(source);
    }

    /**
     * Base64解密
     * <p>Title         : decode lilinhai 2018年4月9日 下午1:05:15</p>
     * @param source
     * @return 
     * String
     */
    public static byte[] decodeToByte(String source)
    {
        return Base64.getDecoder().decode(source.getBytes(UTF_8_SET));
    }
}
