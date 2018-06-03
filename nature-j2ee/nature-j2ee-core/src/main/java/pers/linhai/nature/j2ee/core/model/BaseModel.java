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
    private Where where;
    
    /**
     * 数据库表名
     */
    private String tableName;
    
    /**
     * <p>Title        : BaseModel lilinhai 2018年2月15日 上午9:09:43</p>
     * @param tableName 
     */
    public BaseModel()
    {
        this.tableName = table();
    }
    
    /**
     * <p>Get Method   :   tableName String</p>
     * @return tableName
     */
    public String tableName()
    {
        return tableName;
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
