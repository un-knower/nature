/*
 * 文 件 名: pers.linhai.esdao.core.Type.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月4日 下午2:48:06
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.indexaccess.model.type;

import java.io.Serializable;

/**
 * 抽象的索引表映射
 * @author  shinelon
 * @version  V100R001C00
 */
public abstract class Type implements Serializable
{
    
    private static final long serialVersionUID = 1L;

    /**
     * 所有type实例的id，对应ES的内置字段_id
     */
    protected String id;
    
    /**
     * 版本号
     */
    protected long version;
    
    /**
     * <pre>
     *      路由：A document is routed to a particular shard in an index using the following formula:
     *      shard_num = hash(_routing) % num_primary_shards
     * </pre>
     */
    protected String routing;
    
    /**
     * <pre>
     *   PUT my_index
     *   {
     *     "mappings": {
     *       "my_parent": {},
     *       "my_child": {
     *         "_parent": {
     *           "type": "my_parent" 
     *         }
     *       }
     *     }
     *   }
     * 
     *   PUT my_index/my_parent/1 
     *   {
     *     "text": "This is a parent document"
     *   }
     *   
     *   PUT my_index/my_child/2?parent=1 
     *   {
     *     "text": "This is a child document"
     *   }
     *   
     *   PUT my_index/my_child/3?parent=1&refresh=true 
     *   {
     *     "text": "This is another child document"
     *   }
     *   
     *   GET my_index/my_parent/_search
     *   {
     *     "query": {
     *       "has_child": { 
     *         "type": "my_child",
     *         "query": {
     *           "match": {
     *             "text": "child document"
     *           }
     *         }
     *       }
     *     }
     *   }
     * 
     * A parent-child relationship can be established between documents in the same index by making one mapping type the parent of another:
     * <b>Parent-child restrictions:</b>
     *      1. The parent and child types must be different — parent-child relationships cannot be established between documents of the same type.
     *      2. The _parent.type setting can only point to a type that doesn’t exist yet. This means that a type cannot become a parent type after it has been created.
     *      3. Parent and child documents must be indexed on the same shard. The parent ID is used as the routing value for the child, to ensure that the child is 
     *   indexed on the same shard as the parent. This means that the same parent value needs to be provided when getting, deleting, or updating a child document. 
     * </pre>
     */
    protected String parent;
    
    /**
     * 获取 id
     * @return 返回 id
     */
    public String getId()
    {
        return id;
    }
    
    /**
     * 设置 id
     * @param 对id进行赋值
     */
    public void setId(String id)
    {
        this.id = id;
    }
    
    /**
     * 返回 version
     *
     * @return version
     */
    public long getVersion()
    {
        return version;
    }
    
    /**
     * 对version进行赋值
     *
     * @param version
     */
    public void setVersion(long version)
    {
        this.version = version;
    }

    /**
     * 返回 routing
     *
     * @return routing
     */
    public String getRouting()
    {
        return routing;
    }

    /**
     * 对routing进行赋值
     *
     * @param routing
     */
    public void setRouting(String routing)
    {
        this.routing = routing;
    }

    /**
     * 返回 parent
     *
     * @return parent
     */
    public String getParent()
    {
        return parent;
    }

    /**
     * 对parent进行赋值
     *
     * @param parent
     */
    public void setParent(String parent)
    {
        this.parent = parent;
    }

    /**
     *
     * @return
     */
    public String toString()
    {
        return "Mapping [id=" + id + ", version=" + version + ", routing=" + routing + ", parent=" + parent + "]";
    }
}
