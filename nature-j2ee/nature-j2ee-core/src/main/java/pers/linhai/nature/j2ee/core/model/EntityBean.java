/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : EntityBean.java</p>
 * <p>Package     : com.meme.crm.model.core</p>
 * @Creator lilinhai 2018年2月16日 下午5:00:25
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.io.Serializable;

import pers.linhai.nature.utils.NamingUtils;

/**
 * <p>ClassName      : EntityBean</p>
 * @author lilinhai 2018年2月16日 下午5:00:25
 * @version 1.0
 */
public class EntityBean extends ModelBean
{

    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年2月16日 下午5:00:36</p>
     */
    private static final long serialVersionUID = 1L;
    
    private boolean isInited;
    
    /**
     * <p>Title        : EntityBean lilinhai 2018年3月14日 上午11:27:01</p>
     */ 
    public EntityBean()
    {
        
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年3月14日 上午11:38:38</p>
     * <p>Title: put</p>
     * @param key
     * @param value
     * @return 
     * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
     */ 
    public Serializable put(String key, Serializable value)
    {
        if (!isInited)
        {
            putAttribute(NamingUtils.getCamelCaseString(key, false), value);
        }
        else
        {
            putAttribute(key, value);
        }
        return null;
    }

    /**
     * <p>Set Method   :   isInited boolean</p>
     * @param isInited
     */
    public void setInited(boolean isInited)
    {
        this.isInited = isInited;
    }
}
