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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>ClassName      : EntityBean</p>
 * @author lilinhai 2018年2月16日 下午5:00:25
 * @version 1.0
 */
public class ModelBean extends HashMap<String, Serializable>
{
    
    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年2月16日 下午5:00:36</p>
     */
    private static final long serialVersionUID = 1L;
    
    private static final Map<Class< ? >, List<Field>> ENTITY_FIELD_MAP = new HashMap<Class< ? >, List<Field>>();
    
    public ModelBean()
    {
        
    }
    
    /**
     * 
     * <p>Title        : EntityBean lilinhai 2018年2月17日 下午7:33:13</p>
     * @param entity
     */
    public ModelBean(Object object)
    {
        try
        {
            List<Field> fieldList = parse(object);
            for (Field field : fieldList)
            {
                put(field.getName(), (Serializable) (field.get(object)));
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * <p>Title         : g lilinhai 2018年2月21日 上午9:00:10</p>
     * @param entity
     * @return 
     * List<Field> 
     */
    protected List<Field> parse(Object object)
    {
        List<Field> fieldList = ENTITY_FIELD_MAP.get(object.getClass());
        if (fieldList == null)
        {
            fieldList = new ArrayList<Field>();
            parse(object.getClass().getDeclaredFields(), fieldList);
            ENTITY_FIELD_MAP.put(object.getClass(), fieldList);
        }
        return fieldList;
    }
    
    /**
     * 解析
     * <p>Title         : parse lilinhai 2018年2月21日 上午9:13:25</p>
     * @param fs
     * @param fieldList 
     * void
     */
    protected static void parse(Field[] fs, List<Field> fieldList)
    {
        parse(fs, fieldList, null);
    }
    
    /**
     * 解析
     * <p>Title         : parse lilinhai 2018年2月21日 上午9:13:25</p>
     * @param fs
     * @param fieldList 
     * void
     */
    protected static void parse(Field[] fs, List<Field> fieldList, Set<String> excludeFieldSet)
    {
        for (Field field : fs)
        {
            // 静态成员跳过处理
            if (Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }
            
            if (excludeFieldSet != null && excludeFieldSet.contains(field.getName()))
            {
                continue;
            }
            
            field.setAccessible(true);
            fieldList.add(field);
        }
    }
}
