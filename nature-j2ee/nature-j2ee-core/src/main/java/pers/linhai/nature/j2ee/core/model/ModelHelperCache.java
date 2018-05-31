/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : ModelHelperCache.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年5月31日 下午2:12:11
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 模型实体帮助器缓存
 * <p>ClassName      : ModelHelperCache</p>
 * @author lilinhai 2018年5月31日 下午2:12:11
 * @version 1.0
 */
public abstract class ModelHelperCache
{
    
    private static final ModelHelperCache CACHE = new ModelHelperCache() {};
    
    private Map<String, ModelHelper> modelHelperMap = new HashMap<String, ModelHelper>();
    
    private ModelHelperCache()
    {
        
    }
    
    public void put(String key, ModelHelper modelHelper)
    {
        modelHelperMap.put(key, modelHelper);
    }
    
    /**
     * 获取实体的帮助器
     * <p>Title         : get lilinhai 2018年5月31日 下午3:23:02</p>
     * @param key
     * @return 
     * ModelHelper
     */
    public ModelHelper get(String key)
    {
        return modelHelperMap.get(key);
    }
    
    /**
     * 获取数据库表的名字
     * <p>Title         : getTableName lilinhai 2018年5月31日 下午3:22:50</p>
     * @param key
     * @return 
     * String
     */
    public String getTableName(String key)
    {
        return ((BaseModel)modelHelperMap.get(key)).tableName();
    }
    
    public static ModelHelperCache getInstance()
    {
        return CACHE;
    }
}
