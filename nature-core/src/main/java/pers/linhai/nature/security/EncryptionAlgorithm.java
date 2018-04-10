/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : EncryptionAlgorithm.java</p>
 * <p>Package     : pers.linhai.nature.security</p>
 * @Creator lilinhai 2018年4月10日 下午9:45:09
 * @Version  V1.0  
 */ 

package pers.linhai.nature.security;

import java.nio.charset.Charset;

import javax.crypto.Cipher;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : EncryptionAlgorithm</p>
 * @author lilinhai 2018年4月10日 下午9:45:09
 * @version 1.0
 */
public abstract class EncryptionAlgorithm
{
    protected static final Charset UTF_8_SET = Charset.forName("utf-8");
    
    /**
     * 加密的Cipher
     */
    protected Cipher encryptCipher;
    
    /**
     * 解密的Cipher
     */
    protected Cipher decryptCipher;
    
    
    protected byte[] getBytes(String source)
    {
        return source.getBytes(UTF_8_SET);
    }
    
    /**
     * 将目标字符串加密，输出base64格式的字符串
     * <p>Title         : encryptToBase64Str lilinhai 2018年4月9日 下午11:34:30</p>
     * @param source
     * @return 
     * String
     */
    public abstract String encryptToBase64Str(String source);
    
    /**
     * 加密，返回明文被加密后的字节数组
     * <p>Title         : encrypt lilinhai 2018年4月9日 下午11:33:09</p>
     * @param source
     * @return 
     * byte[]
     */
    public abstract byte[] encrypt(String source);
    
    /**
     * 将一个base64格式的加密字符串解密成明文
     * <p>Title         : decryptFromBase64Str lilinhai 2018年4月9日 下午11:48:26</p>
     * @param content
     * @return 
     * String
     */
    public abstract String decryptFromBase64Str(String content);
    
    /**
     * 解密，返回明文对应的字节数组
     * <p>Title         : decrypt lilinhai 2018年4月9日 下午11:38:29</p>
     * @param source
     * @return 
     * byte[]
     */
    public abstract byte[] decrypt(byte[] source);
}
