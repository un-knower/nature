/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2017, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : BaseEntity.java</p> <p>Package
 * : com.leloven.wanka.dao.sdk.entity</p>
 * @Creator lilinhai 2017年12月28日 下午6:54:46
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 抽象实体
 * <p>ClassName      : BaseEntity</p>
 * @author lilinhai 2017年12月28日 下午6:54:46
 * @version 1.0
 */
public abstract class BaseEntity<Key extends Serializable> extends JdbcModel implements Serializable
{

    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年2月4日 下午2:36:32</p>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 待更新的字段的值的集合
     */
    private List<PersistentField> persistentFieldList;
    
    /**
     * 主键ID
     */
    public Key id;

    /**
     *  入库时间
     */
    public Date createTime;

    /**
     *  修改时间
     */
    public Date updateTime;

    /**
     * <p>Title        : BaseEntity lilinhai 2018年2月13日 下午2:53:51</p>
     * @param tableName 
     */
    public BaseEntity(String tableName)
    {
        super(tableName);
    }

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
    }
    
    /**
     * <p>Get Method   :   persistentFieldList List<FieldValue></p>
     * @return persistentFieldList
     */
    public List<PersistentField> getPersistentFieldList()
    {
        return persistentFieldList;
    }

    /**
     * <p>Set Method   :   updateFieldList List<FieldValue></p>
     * @param persistentFieldList
     */
    public void setPersistentFieldList(List<PersistentField> persistentFieldList)
    {
        if (persistentFieldList != null)
        {
            for (PersistentField persistentField : persistentFieldList)
            {
                // 校验字段名合法性
                validField(persistentField.getFieldName());
                persistentField.setTableFieldName(getTableField(persistentField.getFieldName()));
                persistentField.setJdbcType(getJdbcType(persistentField.getFieldName()));
            }
            this.persistentFieldList = persistentFieldList;
        }
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月4日 下午3:00:30</p>
     * <p>Title: hashCode</p>
     * <p>Description: TODO</p>
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
        BaseEntity<?> other = (BaseEntity<?>)obj;
        if (id == null)
        {
            if (other.id != null) return false;
        }
        else if (!id.equals(other.id)) return false;
        return true;
    }
}
