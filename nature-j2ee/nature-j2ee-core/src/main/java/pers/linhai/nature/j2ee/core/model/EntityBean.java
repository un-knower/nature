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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    
    private static final List<Field> FIELD_LIST = new ArrayList<Field>(2);
    static
    {
        Set<String> excludeFieldSet = new HashSet<String>();
        excludeFieldSet.add("persistentFieldMap");
        parse(BaseEntity.class.getDeclaredFields(), FIELD_LIST, excludeFieldSet);
    }
    
    private boolean isInited;
    
    /**
     * <p>Title        : EntityBean lilinhai 2018年3月14日 上午11:27:01</p>
     */ 
    public EntityBean()
    {
        
    }

    /**
     * 
     * <p>Title        : EntityBean lilinhai 2018年2月17日 下午7:33:13</p>
     * @param entity
     */
    public <Entity extends BaseEntity<?>> EntityBean(Entity entity)
    {
        super(entity);
        try
        {
            for (Field field : FIELD_LIST)
            {
                putAttribute(field.getName(), (Serializable)(field.get(entity)));
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
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
