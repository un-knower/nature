/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : QueryValidator.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年6月5日 下午12:46:23
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pers.linhai.nature.j2ee.core.constant.BaseErrorCode;
import pers.linhai.nature.j2ee.core.model.condition.CommonConditionSegment;
import pers.linhai.nature.j2ee.core.model.condition.ConditionSegment;
import pers.linhai.nature.j2ee.core.model.condition.InConditionSegment;
import pers.linhai.nature.j2ee.core.model.enumer.BaseField;
import pers.linhai.nature.j2ee.core.model.exception.QueryValidException;
import pers.linhai.nature.j2ee.core.model.join.SelectField;

/**
 * query对象校验器
 * <p>ClassName      : QueryValidator</p>
 * @author lilinhai 2018年6月5日 下午12:46:23
 * @version 1.0
 */
public class QueryValidator
{
    
    // 校验允许查询的字段
    private Set<String> allowSelectFieldSet = new HashSet<String>();
    
    // 校验查询条件的允许字段名和字段名对应的允许输入值
    private Map<String, Set<String>> allowConditionFieldMap = new HashMap<String, Set<String>>();
    
    // 校验返回最大条数
    private Integer allowReturnSize;

    /**
     * <p>Set Method   :   allowSelectFieldSet Set<String></p>
     * @param allowSelectFieldSet
     */
    public void addAllowSelectField(ModelField modelField)
    {
        if (modelField.getClass() == BaseField.class)
        {
            throw new QueryValidException(BaseErrorCode.QUERY_VALIDATOR_BUILD_NOT_SUPPORT, " Query validator does not support BaseField type.");
        }
        this.allowSelectFieldSet.add(modelField.getEntity() + "." + modelField.getField());
    }
    
    /**
     * <p>Set Method   :   allowQueryFieldMap Map<String,Set<Object>></p>
     * @param allowConditionFieldMap
     */
    public void addAllowConditionField(ModelField modelField, Object... vals)
    {
        if (modelField.getClass() == BaseField.class)
        {
            throw new QueryValidException(BaseErrorCode.QUERY_VALIDATOR_BUILD_NOT_SUPPORT, " Query validator does not support BaseField type.");
        }
        Set<String> allowValSet = this.allowConditionFieldMap.get(modelField.getEntity() + "." + modelField.getField());
        if (allowValSet == null)
        {
            allowValSet = new HashSet<String>();
            this.allowConditionFieldMap.put(modelField.getEntity() + "." + modelField.getField(), allowValSet);
        }
        
        if (vals != null)
        {
            for (Object val : vals)
            {
                allowValSet.add(String.valueOf(val));
            }
        }
    }
    
    public void validQuery(BaseQuery query)
    {
        validReturnSize(query.getSize());
        
        if (!allowSelectFieldSet.isEmpty())
        {
            for (String field : query.getSelectFieldList())
            {
                validSelectField(field);
            }
        }
        
        if (!allowConditionFieldMap.isEmpty())
        {
            if (query.where() == null)
            {
                throw new QueryValidException(BaseErrorCode.QUERY_WHERE_CONDITION_IS_UNDEFINED, "The query corresponding to the where condition is not set.");
            }
            
            for (ConditionSegment conditionSegment : query.where().getConditionSegmentList())
            {
                if (conditionSegment instanceof CommonConditionSegment)
                {
                    validConditionField((CommonConditionSegment)conditionSegment);
                }
                else if (conditionSegment instanceof InConditionSegment)
                {
                    validConditionField((InConditionSegment)conditionSegment);
                }
            }
        }
    }
    
    public void validQuery(JointQuery query)
    {
        validReturnSize(query.getSize());
        
        if (!allowSelectFieldSet.isEmpty())
        {
            for (SelectField selectField : query.getSelectColumnList())
            {
                for (String field : selectField.getFieldList())
                {
                    validSelectField(selectField.getEntity() + "." + field);
                }
            }
        }
        
        if (!allowConditionFieldMap.isEmpty())
        {
            if (query.getWhere() == null)
            {
                throw new QueryValidException(BaseErrorCode.QUERY_WHERE_CONDITION_IS_UNDEFINED, "The joint-query corresponding to the where condition is not set.");
            }
            
            for (ConditionSegment conditionSegment : query.getWhere().getConditionSegmentList())
            {
                if (conditionSegment instanceof CommonConditionSegment)
                {
                    validConditionField((CommonConditionSegment)conditionSegment);
                }
                else if (conditionSegment instanceof InConditionSegment)
                {
                    validConditionField((InConditionSegment)conditionSegment);
                }
            }
        }
    }

    /**
     * 设置允许返回最大大小
     * <p>Set Method   :   allowReturnSize Integer</p>
     * @param allowReturnSize
     */
    public void setAllowReturnSize(Integer allowReturnSize)
    {
        this.allowReturnSize = allowReturnSize;
    }
    
    /**
     * 校验输入参数
     * <p>Title         : validConditionField lilinhai 2018年6月5日 下午3:41:22</p>
     * @param commonConditionSegment 
     * void
     */
    private void validReturnSize(Integer size)
    {
        if (allowReturnSize != null)
        {
            if (size == null)
            {
                throw new QueryValidException(BaseErrorCode.QUERY_RETURN_SIZE_NOT_SET, " The number of return-size is not set.");
            }
            if (allowReturnSize.intValue() < size.intValue())
            {
                throw new QueryValidException(BaseErrorCode.QUERY_RETURN_SIZE_EXCEED, " The return size can't exceed the maximum limit : " + allowReturnSize);
            }
        }
    }
    
    /**
     * 校验输入参数
     * <p>Title         : validConditionField lilinhai 2018年6月5日 下午3:41:22</p>
     * @param commonConditionSegment 
     * void
     */
    private void validSelectField(String field)
    {
        if (!allowSelectFieldSet.contains(field))
        {
            throw new QueryValidException(BaseErrorCode.QUERY_FIELD_NOT_ALLOWED, "This field is not allowed to return :" + field);
        }
    }

    /**
     * 校验输入参数
     * <p>Title         : validConditionField lilinhai 2018年6月5日 下午3:41:22</p>
     * @param commonConditionSegment 
     * void
     */
    private void validConditionField(CommonConditionSegment commonConditionSegment)
    {
        String fieldInfo = commonConditionSegment.getEntity() + "." + commonConditionSegment.getField();
        validConditionField(fieldInfo, String.valueOf(commonConditionSegment.getValue()));
    }
    
    /**
     * 校验输入参数
     * <p>Title         : validConditionField lilinhai 2018年6月5日 下午3:41:22</p>
     * @param commonConditionSegment 
     * void
     */
    private void validConditionField(InConditionSegment inConditionSegment)
    {
        String fieldInfo = inConditionSegment.getEntity() + "." + inConditionSegment.getField();
        for (Object val : inConditionSegment.getValueList())
        {
            validConditionField(fieldInfo, String.valueOf(val));
        }
    }
    
    /**
     * 校验输入参数
     * <p>Title         : validConditionField lilinhai 2018年6月5日 下午3:41:22</p>
     * @param commonConditionSegment 
     * void
     */
    private void validConditionField(String fieldInfo, String value)
    {
        Set<String> allowValSet = this.allowConditionFieldMap.get(fieldInfo);
        if (allowValSet == null)
        {
            throw new QueryValidException(BaseErrorCode.QUERY_CONDITION_FIELD_NOT_ALLOWED, "This field is not allowed to participate in the search :" + fieldInfo);
        }
        
        if (!allowValSet.isEmpty() && !allowValSet.contains(value))
        {
            throw new QueryValidException(BaseErrorCode.QUERY_CONDITION_FIELD_VALUE_NOT_ALLOWED, "This field-value is not allowed to participate in the search :" 
                    + fieldInfo + ", value: " + value);
        }
    }
}
