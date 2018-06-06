/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : EntityBean.java</p>
 * <p>Package     : com.meme.crm.model.core</p>
 * @Creator lilinhai 2018年2月16日 下午5:00:25
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model;

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
    
    /**
     * 删除指定键值
     * <p>Title         : remove lilinhai 2018年6月6日 下午11:21:15</p>
     * @param field
     * @return 
     * Object
     */
    public Object remove(ModelField field)
    {
        return super.remove(field.getField());
    }
}
