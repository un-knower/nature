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
import java.util.Map;
import java.util.regex.Pattern;

import pers.linhai.nature.j2ee.core.dao.exception.ConditionFormatException;
import pers.linhai.nature.j2ee.core.dao.exception.ConditionIsNullException;
import pers.linhai.nature.j2ee.core.dao.exception.IllegalExpression;
import pers.linhai.nature.j2ee.core.model.condition.ConditionSegment;
import pers.linhai.nature.j2ee.core.model.condition.StringSegment;
import pers.linhai.nature.j2ee.core.model.enumer.LogicalOperator;
import pers.linhai.nature.j2ee.core.model.exception.QueryBuildException;

/**
 * 修改语句或查询的where条件
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
     * <p>Get Method   :   expressionSegmentList List<Object></p>
     * @return expressionSegmentList
     */
    public List<ConditionSegment> getConditionSegmentList()
    {
        return conditionSegmentList;
    }
    
    void initialize(ModelHelper validator)
    {
        if (conditionList == null || conditionList.isEmpty())
        {
            throw new ConditionIsNullException("Where-Condition can't be empty.");
        }
        
        boolean isValidatorNull = (validator == null);
        
        // 条件集合
        Map<String, ConditionSegment> conditionMap = new HashMap<String, ConditionSegment>();
        StringBuilder expressionBuff = null;
        int i = 0;
        for (Condition condition : conditionList)
        {
            if(isValidatorNull)
            {
                ModelHelper modelHelper = ModelHelperCache.getInstance().get(condition.getEntity());
                if (modelHelper == null)
                {
                    throw new QueryBuildException(" Illegal entity name : " + condition.getEntity());
                }
                validator = modelHelper;
            }
            
            // 校验字段名
            validator.validField(condition.getField());
            
            i++;
            if (getExpression() == null)
            {
                if (condition.getId() == null || condition.getId().trim().isEmpty())
                {
                    condition.setId(String.valueOf(i));
                }
                
                if (expressionBuff == null)
                {
                    expressionBuff = new StringBuilder();
                }
                
                if (expressionBuff.length() > 0)
                {
                    expressionBuff.append(' ').append(LogicalOperator.AND.getValue()).append(' ');
                }
                expressionBuff.append(condition.getId());
            }
            else if (condition.getId() == null || condition.getId().isEmpty())
            {
                throw new ConditionFormatException("The Condition's id can't be empty while the expression is seted, fieldName:" + condition.getField() + ", id:" + condition.getId());
            }
            
            // 获取改该字段对应的JDBC类型
            condition.setJdbcType(validator.getJdbcType(condition.getField()));
            
            // 校验SQL注入 TODO
            condition.setColumn(validator.getTableField(condition.getField()));
            
            // 解析封装成Condition对象
            ConditionSegment conditionSegment = ConditionSegment.parse(condition);
            
            // 存入hashMap集合中，方便快速读取
            ConditionSegment last = conditionMap.put(conditionSegment.getId(), conditionSegment);
            if (last != null)
            {
                throw new ConditionFormatException("Condition ID repeats, fieldName:" + condition.getField() + ", id:" + condition.getId());
            }
        }
        
        if (getExpression() == null)
        {
            setExpression(expressionBuff.toString());
        }
        
        // 解析表达式
        parseExpression(conditionMap);
    }
    
    private void parseExpression(Map<String, ConditionSegment> conditionMap)
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
