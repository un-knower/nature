/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BaseBean.java</p>
 * <p>Package     : com.meme.crm.model.core</p>
 * @Creator lilinhai 2018年2月7日 下午4:29:28
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : BaseBean</p>
 * @author lilinhai 2018年2月7日 下午4:29:28
 * @version 1.0
 */
public abstract class BaseBean extends HashMap<String, Serializable>
{

    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年2月10日 下午11:08:37</p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 时间格式化对象
     */
    protected final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 添加一个属性
     * <p>Title         : putAttribute lilinhai 2018年2月11日 下午10:08:07</p>
     * @param key
     * @param value 
     * void
     */
    protected <Value extends Serializable> Serializable putAttribute(String key, Value value)
    {
        Serializable rel = null;
        if (value != null)
        {
            rel = super.put(key, value);
            if (value instanceof Date)
            {
                super.put(key.concat("Str"), dateFormat.format((Date)value));
            }
        }
        return rel;
    }
}
