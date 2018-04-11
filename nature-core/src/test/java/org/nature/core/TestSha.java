/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : TestSha.java</p>
 * <p>Package     : org.nature.core</p>
 * @Creator lilinhai 2018年4月11日 下午10:01:33
 * @Version  V1.0  
 */ 

package org.nature.core;

import pers.linhai.nature.encryption.irreversible.MD2Algorithm;
import pers.linhai.nature.encryption.irreversible.MD5Algorithm;
import pers.linhai.nature.encryption.irreversible.SHA1Algorithm;
import pers.linhai.nature.encryption.irreversible.SHA224Algorithm;
import pers.linhai.nature.encryption.irreversible.SHA256Algorithm;
import pers.linhai.nature.encryption.irreversible.SHA384Algorithm;
import pers.linhai.nature.encryption.irreversible.SHA512Algorithm;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : TestSha</p>
 * @author lilinhai 2018年4月11日 下午10:01:33
 * @version 1.0
 */
public class TestSha
{

    public static void main(String[] args)
    {
        System.out.println(new SHA256Algorithm("aaaa").encryptToHexString("13668142438"));
        System.out.println(new SHA256Algorithm("aa").encryptToBase64String("13668142438"));
        
        System.out.println(new SHA512Algorithm("aa").encryptToBase64String("13668142438"));
        System.out.println(new SHA1Algorithm("aa2").encryptToBase64String("13668142438"));
        
        System.out.println(new SHA224Algorithm("aa2").encryptToBase64String("13668142438"));
        
        System.out.println(new SHA384Algorithm("aa2").encryptToBase64String("13668142438"));
        
        System.out.println(new MD5Algorithm("aa2").encryptToBase64String("13668142438"));
        System.out.println(new MD2Algorithm("aa2").encryptToBase64String("13668142438"));
    }
}
