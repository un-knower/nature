/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2017, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : BaseEntity.java</p> <p>Package
 * : com.leloven.wanka.dao.sdk.entity</p>
 * @Creator lilinhai 2017年12月28日 下午6:54:46
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;

/**
 * 抽象实体
 * <p>ClassName      : BaseEntity</p>
 * @author lilinhai 2017年12月28日 下午6:54:46
 * @version 1.0
 */
public abstract class BaseEntity<Key> extends BaseModel
{
    
    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年2月4日 下午2:36:32</p>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 待更新的字段的值的Map集合
     */
    private Map<String, PersistentField> persistentFieldMap = new HashMap<String, PersistentField>();
    
    /**
     * 主键ID
     */
    protected Key id;
    
    /**
     *  入库时间
     */
    @ApiModelProperty(value = "实体创建时间，若前端传入该参数了，则用传入的时间作为创建时间，若未传，则由框架自身获取当前系统时间作为创建时间。")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    protected Date createTime;
    
    /**
     *  修改时间
     */
    @ApiModelProperty(value = "实体修改时间，若前端传入该参数了，则用传入的时间作为修改时间，若未传，则由框架自身获取当前系统时间作为修改 时间。")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    protected Date updateTime;
    
    /**
     * <p>Get Method   :   id Key</p>
     * @return id
     */
    public Key getId()
    {
        return id;
    }
    
    /**
     * <p>Set Method   :   id Key</p>
     * @param id
     */
    public void setId(Key id)
    {
        this.id = id;
        addPersistentField(ModelField.ID, id);
    }
    
    /**
     * <p>Get Method   :   createTime Date</p>
     * @return createTime
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    
    /**
     * <p>Set Method   :   createTime Date</p>
     * @param createTime
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
        
        // 为了兼容没有这两个字段的表
        if (existsField(ModelField.CREATE_TIME.getField()))
        {
            addPersistentField(ModelField.CREATE_TIME, createTime);
        }
    }
    
    /**
     * <p>Get Method   :   updateTime Date</p>
     * @return updateTime
     */
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    /**
     * <p>Set Method   :   updateTime Date</p>
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
        
        // 为了兼容没有这两个字段的表
        if (existsField(ModelField.UPDATE_TIME.getField()))
        {
            addPersistentField(ModelField.UPDATE_TIME, updateTime);
        }
    }
    
    /**
     * 移除某个字段的修改
     * <p>Title         : remove lilinhai 2018年3月13日 下午2:49:26</p>
     * @param fieldName 
     * void
     */
    public void removePersistentField(ModelField field)
    {
        persistentFieldMap.remove(field.getColumn());
    }
    
    /**
     * 是否有持久化字段
     * <p>Title         : hasPersistentField lilinhai 2018年3月13日 下午7:39:25</p>
     * @return 
     * boolean
     */
    public boolean hasPersistentField()
    {
        return !persistentFieldMap.isEmpty();
    }
    
    /**
     * 是否有持久化字段
     * <p>Title         : hasPersistentField lilinhai 2018年3月13日 下午7:39:25</p>
     * @return 
     * boolean
     */
    public boolean hasPersistentField(ModelField field)
    {
        return persistentFieldMap.containsKey(field.getColumn());
    }
    
    /**
     * 添加一个持久化字段
     * <p>Title         : addPersistentField lilinhai 2018年3月13日 上午9:44:12</p>
     * @param fieldName
     * @param value 
     * void
     */
    protected void addPersistentField(ModelField field, Object value)
    {
        validField(field.getField());
        PersistentField fv = new PersistentField();
        fv.setColumn(field.getColumn());
        fv.setValue(value);
        fv.setJdbcType(field.getJdbcType());
        persistentFieldMap.put(field.getColumn(), fv);
    }
    
    public abstract EntityBean toEntityBean();
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月4日 下午3:00:30</p>
     * <p>Title: hashCode</p>
     * @return 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月4日 下午3:00:30</p>
     * <p>Title: equals</p>
     * @param obj
     * @return 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BaseEntity< ? > other = (BaseEntity< ? >) obj;
        if (id == null)
        {
            if (other.id != null) return false;
        }
        else if (!id.equals(other.id)) return false;
        return true;
    }
}
