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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

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
    
    private static final List<Field> FIELD_LIST = new ArrayList<Field>();
    static
    {
        parse(BaseEntity.class.getDeclaredFields(), FIELD_LIST);
    }
    
    /**
     * 
     * <p>Title        : EntityBean lilinhai 2018年2月17日 下午7:33:13</p>
     * @param entity
     */
    public <Key extends Serializable, Entity extends BaseEntity<Key>> EntityBean(Entity entity)
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
     * 解析
     * <p>Title         : parse lilinhai 2018年2月21日 上午9:13:25</p>
     * @param fs
     * @param fieldList 
     * void
     */
    private static void parse(Field[] fs, List<Field> fieldList)
    {
        for (Field field : fs)
        {
            // 静态成员跳过处理
            if (Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }

            if (",persistentFieldList,persistentFieldNameSet,".contains(field.getName()))
            {
                continue;
            }
            
            field.setAccessible(true);
            fieldList.add(field);
        }
    }
}
