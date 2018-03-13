/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : IBaseJdbc.java</p>
 * <p>Package     : com.meme.crm.model.core</p>
 * @Creator lilinhai 2018年2月13日 下午2:21:14
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pers.linhai.nature.j2ee.core.exception.ConditionFormatException;
import pers.linhai.nature.j2ee.core.exception.ConditionIsNullException;
import pers.linhai.nature.j2ee.core.model.Where.Condition;
import pers.linhai.nature.j2ee.core.model.Where.LogicalOperator;
import pers.linhai.nature.j2ee.core.model.condition.ConditionSegment;

/**
 * <p>ClassName      : IBaseJdbc</p>
 * @author lilinhai 2018年2月13日 下午2:21:14
 * @version 1.0
 */
public abstract class JdbcModel
{
    
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
    public JdbcModel(String tableName)
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
        List<Condition> conditionTempList = where.getConditionList();
        if(conditionTempList == null || conditionTempList.isEmpty())
        {
            throw new ConditionIsNullException("Where-Condition can't be empty.");
        }
        
        // 条件集合
        Map<String, ConditionSegment> conditionMap = new HashMap<String, ConditionSegment>();
        StringBuilder expressionBuff = null;
        int i = 0;
        for (Condition conditionTemp : conditionTempList)
        {
            // 校验字段名
            validField(conditionTemp.getFieldName());
            
            i++;
            if (where.getExpression() == null)
            {
                if (conditionTemp.getId() == null || conditionTemp.getId().trim().isEmpty())
                {
                    conditionTemp.setId(String.valueOf(i));
                }
                
                if (expressionBuff == null)
                {
                    expressionBuff = new StringBuilder();
                }
                
                if (expressionBuff.length() > 0)
                {
                    expressionBuff.append(' ').append(LogicalOperator.AND.getValue()).append(' ');
                }
                expressionBuff.append(conditionTemp.getId());
            }
            else if (conditionTemp.getId() == null || conditionTemp.getId().isEmpty())
            {
                throw new ConditionFormatException("The Condition's id can't be empty while the expression is seted, fieldName:" + conditionTemp.getFieldName() + ", id:" + conditionTemp.getId());
            }
            
            
            // 获取改该字段对应的JDBC类型
            conditionTemp.setJdbcType(getJdbcType(conditionTemp.getFieldName()));
            
            // 校验SQL注入 TODO
            conditionTemp.setFieldName(getTableField(conditionTemp.getFieldName()));
            
            // 解析封装成Condition对象
            ConditionSegment condition = ConditionSegment.parse(conditionTemp);
            
            // 存入hashMap集合中，方便快速读取
            ConditionSegment last = conditionMap.put(condition.getId(), condition);
            if (last != null)
            {
                throw new ConditionFormatException("Condition ID repeats, fieldName:" + conditionTemp.getFieldName() + ", id:" + conditionTemp.getId());
            }
        }
        
        // 释放临时条件对象
        conditionTempList.clear();
        where.setConditionList(null);
        
        if (where.getExpression() == null)
        {
            where.setExpression(expressionBuff.toString());
        }
        
        // 解析表达式
        where.parseExpression(conditionMap);
        
        this.where = where;
    }
}
