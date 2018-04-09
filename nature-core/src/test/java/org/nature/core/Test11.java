/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : Test11.java</p>
 * <p>Package     : org.nature.core</p>
 * @Creator lilinhai 2018年4月9日 上午11:49:29
 * @Version  V1.0  
 */ 

package org.nature.core;

import pers.linhai.nature.utils.Base64Utils;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : Test11</p>
 * @author lilinhai 2018年4月9日 上午11:49:29
 * @version 1.0
 */
public class Test11
{

    private static String SUB_STR = Base64Utils.encode("mmcrm2018");
    
    private static String S1 = SUB_STR.substring(0, SUB_STR.length() / 3);
    
    private static String S2 = SUB_STR.substring(SUB_STR.length() / 3, SUB_STR.length() / 3 * 2);
    
    private static String S3 = SUB_STR.substring(SUB_STR.length() / 3 * 2, SUB_STR.length());
    
    public static void main(String[] args) throws Exception
    {
        /*byte[] bs = "13668142438".getBytes("utf-8");
        String encodeStr = Base64Utils.encodeToString(bs);
        System.out.println(encodeStr);
        
        bs = Base64Utils.decodeFromString(encodeStr);
        System.out.println(new String(bs, "utf-8"));*/
        
        System.out.println(Base64Utils.encode("13668142438"));
        System.out.println(Base64Utils.decode(Base64Utils.encode("13668142438")));
        System.out.println(SUB_STR);
        main1(Base64Utils.encode("13668142438"));
        System.out.println(S1 + S2 + S3);
    }
    
    public static void main1(String str)
    {
        String str1 = str.substring(0, str.length() / 2);
        String str2 = str.substring(str.length() / 2, str.length());
        System.out.println(S1 + str1 + S2 + str2 + S3);
    }
}
