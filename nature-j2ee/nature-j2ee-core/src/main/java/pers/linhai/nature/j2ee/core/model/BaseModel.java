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
public abstract class BaseModel implements Serializable
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
     * <p>Title        : JdbcFunctionModel lilinhai 2018年2月15日 上午9:09:43</p>
     * @param tableName 
     */ 
    public BaseModel(String tableName)
    {
        this.tableName = tableName;
    }

    /**
     * 获取java字段名对应的数据库表字段名
     * <p>Title         : validField lilinhai 2018年2月8日 下午11:06:40</p>
     * @param javaField 
     * void
     */
    public abstract String getTableField(String javaField);

    /**
     * 获取改该字段对应的JDBC类型
     * <p>Title         : getJdbcType lilinhai 2018年2月10日 上午11:14:56</p>
     * @param jdbcType
     * @return 
     * String
     */
    public abstract String getJdbcType(String javaField);
    
    /**
     * 校验字段名
     * <p>Title         : validField lilinhai 2018年2月13日 下午2:47:59</p>
     * @param javaField 
     * void
     */
    public abstract void validField(String javaField);
    
    /**
     * 判断是否存在某个字段
     * <p>Title         : existsField lilinhai 2018年4月3日 下午4:26:08</p>
     * @param fieldName
     * @return 
     * boolean
     */
    public abstract boolean existsField(String fieldName);
    
    
    /**
     * <p>Get Method   :   tableName String</p>
     * @return tableName
     */
    public String getTableName()
    {
        return tableName;
    }

    /**
     * <p>Get Method   :   where Where</p>
     * @return where
     */
    public Where getWhere()
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
