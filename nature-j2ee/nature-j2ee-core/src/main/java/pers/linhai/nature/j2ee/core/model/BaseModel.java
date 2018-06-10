/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : IBaseJdbc.java</p>
 * <p>Package     : com.meme.crm.model.core</p>
 * @Creator lilinhai 2018年2月13日 下午2:21:14
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>ClassName      : IBaseJdbc</p>
 * @author lilinhai 2018年2月13日 下午2:21:14
 * @version 1.0
 */
public abstract class BaseModel implements ModelHelper, Serializable
{
    
    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年4月22日 下午9:39:08</p>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 查询条件集合
     */
    @ApiModelProperty(value = "查询条件，或实体修改条件，在实体创建时，传入该条件无效 。")
    private Where where;
    
    /**
     * 数据库表名
     */
    private String table;
    
    /**
     * <p>Title        : BaseModel lilinhai 2018年2月15日 上午9:09:43</p>
     * @param tableName 
     */
    public BaseModel()
    {
        this.table = table();
    }
    
    /**
     * <p>Get Method   :   tableName String</p>
     * @return tableName
     */
    @JsonIgnore
    public String getTable()
    {
        return table;
    }
    
    /**
     * <p>Get Method   :   where Where</p>
     * @return where
     */
    public Where where()
    {
        return where;
    }
    
    /**
     * <p>Set Method   :   where Where</p>
     * @param where
     */
    public void setWhere(Where where)
    {
        where.initialize(this);
        this.where = where;
    }
}
