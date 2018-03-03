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
import pers.linhai.nature.j2ee.core.model.Where.ConditionBean;
import pers.linhai.nature.j2ee.core.model.Where.LogicalOperator;
import pers.linhai.nature.j2ee.core.model.condition.Condition;

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
     * 待更新的字段的值的集合
     */
    private List<PersistentField> persistentFieldList;
    
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
        List<ConditionBean> conditionTempList = where.getConditionList();
        if(conditionTempList == null || conditionTempList.isEmpty())
        {
            throw new ConditionIsNullException("Where-Condition can't be empty.");
        }
        
        // 条件集合
        Map<String, Condition> conditionMap = new HashMap<String, Condition>();
        StringBuilder expressionBuff = null;
        int i = 0;
        for (ConditionBean conditionTemp : conditionTempList)
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
            Condition condition = Condition.parse(conditionTemp);
            
            // 存入hashMap集合中，方便快速读取
            Condition last = conditionMap.put(condition.getId(), condition);
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
     * 字段值
     * <p>ClassName      : FieldValue</p>
     * @author lilinhai 2018年2月13日 下午3:29:28
     * @version 1.0
     */
    public static class PersistentField
    {
        /**
         * 数据库表的字段名
         */
        private String tableFieldName;
        
        /**
         * java实体的字段名
         */
        private String fieldName;

        /**
         * 查询字段的值
         */
        private Object value;

        /**
         * 该自定义条件字段对饮的JDBC类型
         */
        private String jdbcType;

        /**
         * <p>Get Method   :   value String</p>
         * @return value
         */
        public Object getValue()
        {
            return value;
        }

        /**
         * <p>Set Method   :   value String</p>
         * @param value
         */
        public void setValue(Object value)
        {
            this.value = value;
        }

        /**
         * <p>Get Method   :   jdbcType String</p>
         * @return jdbcType
         */
        public String getJdbcType()
        {
            return jdbcType;
        }

        /**
         * <p>Set Method   :   jdbcType String</p>
         * @param jdbcType
         */
        public void setJdbcType(String jdbcType)
        {
            this.jdbcType = jdbcType;
        }
        
        /**
         * <p>Get Method   :   fieldName String</p>
         * @return fieldName
         */
        public String getFieldName()
        {
            return fieldName;
        }

        /**
         * <p>Set Method   :   fieldName String</p>
         * @param fieldName
         */
        public void setFieldName(String fieldName)
        {
            this.fieldName = fieldName;
        }
        
        /**
         * <p>Get Method   :   tableFieldName String</p>
         * @return tableFieldName
         */
        public String getTableFieldName()
        {
            return tableFieldName;
        }

        /**
         * <p>Set Method   :   tableFieldName String</p>
         * @param tableFieldName
         */
        public void setTableFieldName(String tableFieldName)
        {
            this.tableFieldName = tableFieldName;
        }

        /** 
         * <p>Overriding Method: lilinhai 2018年2月13日 下午3:29:50</p>
         * <p>Title: toString</p>
         * @return 
         * @see java.lang.Object#toString()
         */
        public String toString()
        {
            return "PersistentField [value=" + value + ", jdbcType=" + jdbcType + "]";
        }
    }
}
