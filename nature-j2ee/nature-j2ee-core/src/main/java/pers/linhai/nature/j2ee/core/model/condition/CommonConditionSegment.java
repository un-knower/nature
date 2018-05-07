/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : CommonCondition.java</p>
 * <p>Package : com.meme.crm.model.core.condition</p>
 * @Creator lilinhai 2018年2月15日 下午3:40:59
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.model.condition;

import pers.linhai.nature.j2ee.core.model.Where.Condition;
import pers.linhai.nature.j2ee.core.model.datatype.DataType;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : CommonCondition</p>
 * @author lilinhai 2018年2月15日 下午3:40:59
 * @version 1.0
 */
public class CommonConditionSegment extends ConditionSegment
{
    
    /**
     * 查询字段的值
     */
    private Object value;
    
    /**
     * <p>Title        : CommonCondition lilinhai 2018年2月15日 下午4:23:59</p>
     * @param fieldName 
     */
    public CommonConditionSegment(Condition condition)
    {
        super(condition, 1);
        setValue(condition.getValue());
    }
    
    /**
     * <p>Get Method   :   value Serializable</p>
     * @return value
     */
    public Object getValue()
    {
        return value;
    }
    
    /**
     * <p>Set Method   :   value Serializable</p>
     * @param value
     */
    public void setValue(Object value)
    {
        this.value = DataType.parse(getJdbcType(), value);
    }
}
