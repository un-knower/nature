/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : TestAes.java</p>
 * <p>Package     : org.nature.core</p>
 * @Creator lilinhai 2018年4月9日 下午2:07:40
 * @Version  V1.0  
 */ 

package org.nature.core;

import pers.linhai.nature.utils.AESUtils;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : TestAes</p>
 * @author lilinhai 2018年4月9日 下午2:07:40
 * @version 1.0
 */
public class TestAes
{

    public static void main(String[] args)
    {
        String phone = "13668142438";
        String pass = "12345";
        System.out.println(AESUtils.encryptToBase64Str(phone, pass));
        System.out.println(AESUtils.decryptFromBase64Str(AESUtils.encryptToBase64Str(phone, pass), pass));
    }
}
