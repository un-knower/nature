/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : SHA256Algorithm.java</p>
 * <p>Package     : pers.linhai.nature.security.irreversible</p>
 * @Creator lilinhai 2018年4月11日 上午9:48:46
 * @Version  V1.0  
 */ 

package pers.linhai.nature.encrypt.irreversible;

import pers.linhai.nature.constant.MessageDigestAlgorithms;

/**
 * MD5不可逆加密算法
 * <p>ClassName      : SHA256Algorithm</p>
 * @author lilinhai 2018年4月11日 上午9:48:46
 * @version 1.0
 */
public class MD5Algorithm extends IrreversibleAlgorithm
{

    /**
     * <p>Title        : SHA256Algorithm lilinhai 2018年4月11日 下午10:33:25</p>
     * @param salt 
     */ 
    public MD5Algorithm(String salt)
    {
        super(salt);
        this.algorithm = MessageDigestAlgorithms.MD5;
    }
    
    public MD5Algorithm()
    {
        this(null);
    }
}
