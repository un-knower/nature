/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : Where.java</p>
 * <p>Package     : com.meme.crm.model.core</p>
 * @Creator lilinhai 2018年2月13日 下午2:42:43
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import pers.linhai.nature.j2ee.core.exception.IllegalExpression;
import pers.linhai.nature.j2ee.core.model.condition.Condition;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : Where</p>
 * @author lilinhai 2018年2月13日 下午2:42:43
 * @version 1.0
 */
public class Where
{

    /**
     * expression表达式的字符串限制
     */
    private static final Pattern CHAR_LIMIT_PATTERN = Pattern.compile("[\\w\\s\\(\\)]+");
    
    /**
     * 表达式的ID字符范围限制
     */
    private static final Pattern CONDITION_ID_PATTERN = Pattern.compile("\\w+");
    
    /**
     * 查询条件集合
     */
    private List<ConditionBean> conditionList;
    
    /**
     * 表达式片段集合
     */
    private List<Object> expressionSegmentList;
    
    /**
     * 逻辑表达式
     */
    private String expression;
   
    /**
     * <p>Get Method   :   conditionList List<Condition></p>
     * @return conditionList
     */
    public List<ConditionBean> getConditionList()
    {
        return conditionList;
    }

    /**
     * <p>Set Method   :   conditionList List<Condition></p>
     * @param conditionList
     */
    public void setConditionList(List<ConditionBean> conditionList)
    {
        this.conditionList = conditionList;
    }

    /**
     * <p>Get Method   :   expression String</p>
     * @return expression
     */
    public String getExpression()
    {
        return expression;
    }

    /**
     * <p>Set Method   :   expression String</p>
     * @param expression
     */
    public void setExpression(String expression)
    {
        this.expression = expression;
    }
    
    /**
     * <p>Get Method   :   expressionSegmentList List<Object></p>
     * @return expressionSegmentList
     */
    public List<Object> getExpressionSegmentList()
    {
        return expressionSegmentList;
    }

    public void parseExpression(Map<String, Condition> conditionMap)
    {
        if(!CHAR_LIMIT_PATTERN.matcher(expression).matches())
        {
            throw new IllegalExpression("The expression contains illegal characters, such as spaces in Chinese parentheses, please check: " + expression);
        }
        
        expression = expression.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ").replaceAll("\\s+", " ");
        String[] expressionSegmentArr = expression.split("\\s+");
        expressionSegmentList = new ArrayList<Object>();
        for (String expressionSegment : expressionSegmentArr)
        {
            LogicalOperator logicalOperator = LogicalOperator.transfer(expressionSegment);
            if (logicalOperator == null)
            {
                // 如果是condition的id，则替换为实体对象
                if (CONDITION_ID_PATTERN.matcher(expressionSegment).matches())
                {
                    Condition con = conditionMap.get(expressionSegment);
                    if (con == null)
                    {
                        throw new IllegalExpression("Unable to find an expression with id " + expressionSegment + ", check:" + expression);
                    }
                    expressionSegmentList.add(con);
                }
                else
                {
                    expressionSegmentList.add(expressionSegment);
                }
            }
            else
            {
                expressionSegmentList.add(logicalOperator.getValue());
            }
        }
    }
    
    public static enum LogicalOperator
    {
        AND("and"),
        
        OR("or");
        
        /**
         * <p>Title        : LogicalOperator lilinhai 2018年2月10日 下午2:29:51</p>
         * @param value 
         */ 
        private LogicalOperator(String value)
        {
            this.value = value;
        }

        /**
         * 值
         */
        private String value;

        /**
         * <p>Get Method   :   value String</p>
         * @return value
         */
        public String getValue()
        {
            return value;
        }
        
        public static LogicalOperator transfer(String value)
        {
            return value.toLowerCase(Locale.ENGLISH).equals("and") ? AND : value.toLowerCase(Locale.ENGLISH).equals("or") ? OR : null;
        }
    }

    /**
     * 子查询条件封装
     * <p>ClassName      : Condition</p>
     * @author lilinhai 2018年2月9日 下午5:07:13
     * @version 1.0
     */
    public static class ConditionBean
    {
        
        /**
         * 子查询条件ID
         */
        private String id;
        
        /**
         * 字段名
         */
        private String fieldName;
        
        /**
         * 运算符
         */
        private String operator;
        
        /**
         * 查询字段的值
         */
        private Object value;
        
        /**
         * 该自定义条件字段对饮的JDBC类型
         */
        private String jdbcType;
        
        /**
         * <p>Get Method   :   id String</p>
         * @return id
         */
        public String getId()
        {
            return id;
        }

        /**
         * <p>Set Method   :   id String</p>
         * @param id
         */
        public void setId(String id)
        {
            this.id = id;
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
         * <p>Get Method   :   operator String</p>
         * @return operator
         */
        public String getOperator()
        {
            return operator;
        }

        /**
         * <p>Set Method   :   operator String</p>
         * @param operator
         */
        public void setOperator(String operator)
        {
            this.operator = operator;
        }

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
         * <p>Overriding Method: lilinhai 2018年2月10日 下午3:53:07</p>
         * <p>Title: toString</p>
         * @return 
         * @see java.lang.Object#toString()
         */ 
        public String toString()
        {
            return "Condition [id=" + id + ", fieldName=" + fieldName + ", operator=" + operator + ", value=" + value + ", jdbcType=" + jdbcType + "]";
        }
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月10日 下午1:42:07</p>
     * <p>Title: toString</p>
     * @return 
     * @see java.lang.Object#toString()
     */ 
    public String toString()
    {
        return "Where [conditionList=" + conditionList + ", expression=" + expression + "]";
    }

}
