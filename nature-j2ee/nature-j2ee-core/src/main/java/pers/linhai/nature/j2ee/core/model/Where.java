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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import pers.linhai.nature.j2ee.core.dao.exception.ConditionFormatException;
import pers.linhai.nature.j2ee.core.dao.exception.ConditionIsNullException;
import pers.linhai.nature.j2ee.core.dao.exception.IllegalExpression;
import pers.linhai.nature.j2ee.core.model.condition.ConditionSegment;
import pers.linhai.nature.j2ee.core.model.condition.StringSegment;

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
    private List<Condition> conditionList;
    
    /**
     * 条件片段集合
     */
    private List<ConditionSegment> conditionSegmentList;
    
    /**
     * 逻辑表达式
     */
    private String expression;
    
    /**
     * 是否已经初始化
     */
    private boolean isInitialized;
    
    /**
     * <p>Get Method   :   conditionList List<Condition></p>
     * @return conditionList
     */
    public List<Condition> getConditionList()
    {
        return conditionList;
    }
    
    /**
     * <p>Set Method   :   conditionList List<Condition></p>
     * @param conditionList
     */
    public void setConditionList(List<Condition> conditionList)
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
     * <p>Get Method   :   isInitialized boolean</p>
     * @return isInitialized
     */
    boolean isInitialized()
    {
        return isInitialized;
    }
    
    /**
     * <p>Set Method   :   isInitialized boolean</p>
     * @param isInitialized
     */
    void setInitialized(boolean isInitialized)
    {
        this.isInitialized = isInitialized;
    }
    
    /**
     * <p>Get Method   :   expressionSegmentList List<Object></p>
     * @return expressionSegmentList
     */
    public List<ConditionSegment> getConditionSegmentList()
    {
        return conditionSegmentList;
    }
    
    void initialize(BaseModel baseModel)
    {
        if (this.isInitialized())
        {
            return;
        }
        
        List<Condition> conditionTempList = getConditionList();
        if (conditionTempList == null || conditionTempList.isEmpty())
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
            baseModel.validField(conditionTemp.getFieldName());
            
            i++;
            if (getExpression() == null)
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
            conditionTemp.setJdbcType(baseModel.getJdbcType(conditionTemp.getFieldName()));
            
            // 校验SQL注入 TODO
            conditionTemp.setFieldName(baseModel.getTableField(conditionTemp.getFieldName()));
            
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
        setConditionList(null);
        
        if (getExpression() == null)
        {
            setExpression(expressionBuff.toString());
        }
        
        // 解析表达式
        parseExpression(conditionMap);
        
        // 初始化完成
        setInitialized(true);
    }
    
    public void parseExpression(Map<String, ConditionSegment> conditionMap)
    {
        if (!CHAR_LIMIT_PATTERN.matcher(expression).matches())
        {
            throw new IllegalExpression("The expression contains illegal characters, such as spaces in Chinese parentheses, please check: " + expression);
        }
        
        expression = expression.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ").replaceAll("\\s+", " ");
        String[] expressionSegmentArr = expression.split("\\s+");
        conditionSegmentList = new ArrayList<ConditionSegment>();
        for (String expressionSegment : expressionSegmentArr)
        {
            LogicalOperator logicalOperator = LogicalOperator.transfer(expressionSegment);
            if (logicalOperator == null)
            {
                // 如果是condition的id，则替换为实体对象
                if (CONDITION_ID_PATTERN.matcher(expressionSegment).matches())
                {
                    ConditionSegment con = conditionMap.get(expressionSegment);
                    if (con == null)
                    {
                        throw new IllegalExpression("Unable to find an expression with id " + expressionSegment + ", check:" + expression);
                    }
                    conditionSegmentList.add(con);
                }
                else
                {
                    conditionSegmentList.add(new StringSegment(expressionSegment));
                }
            }
            else
            {
                conditionSegmentList.add(new StringSegment(logicalOperator.getValue()));
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
    public static class Condition
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
         * <p>Title        : Condition lilinhai 2018年5月19日 下午3:19:39</p>
         */ 
        public Condition()
        {
            
        }

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
