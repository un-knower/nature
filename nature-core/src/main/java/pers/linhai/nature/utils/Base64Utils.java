/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : Base64Utils.java</p> <p>Package
 * : pers.linhai.nature.utils</p>
 * @Creator lilinhai 2018年4月9日 下午12:50:57
 * @Version V1.0
 */

package pers.linhai.nature.utils;


import java.util.Base64;


/**
 * Base64工具类
 * <p>ClassName      : Base64Utils</p>
 * @author lilinhai 2018年4月9日 下午12:50:57
 * @version 1.0
 */
public abstract class Base64Utils
{

    /**
     * Base64加密
     * <p>Title         : encode lilinhai 2018年4月9日 下午1:05:03</p>
     * @param source
     * @return 
     * String
     */
    public static String encode(String source)
    {
        try
        {
            return encode(source.getBytes("utf-8"));
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
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
        try
        {
            return new String(Base64.getEncoder().encode(source), "utf-8");
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
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
        try
        {
            return decode(source.getBytes("utf-8"));
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
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
        try
        {
            return new String(decodeToByte(source), "utf-8");
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
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
        try
        {
            return Base64.getDecoder().decode(source.getBytes("utf-8"));
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
