/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : InCondition.java</p>
 * <p>Package     : com.meme.crm.model.core.condition</p>
 * @Creator lilinhai 2018年2月15日 下午3:41:16
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model.condition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pers.linhai.nature.j2ee.core.exception.ConditionFormatException;
import pers.linhai.nature.j2ee.core.model.Where.Condition;
import pers.linhai.nature.j2ee.core.model.datatypeparser.DataTypeParser;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : InCondition</p>
 * @author lilinhai 2018年2月15日 下午3:41:16
 * @version 1.0
 */
public class InConditionSegment extends ConditionSegment
{
    
    private List<Object> valueList = new ArrayList<Object>();
    
    /**
     * <p>Title        : InCondition lilinhai 2018年2月15日 下午4:24:19</p>
     * @param fieldName 
     */
    public InConditionSegment(Condition condition)
    {
        super(condition, 2);
        Object value = condition.getValue();
        if (!(value instanceof Collection))
        {
            throw new ConditionFormatException("The type of value must be a array in 'in-operator', condition: " + condition);
        }
        else
        {
            Collection< ? > valueTempCollection = (Collection< ? >) value;
            if (valueTempCollection == null || valueTempCollection.isEmpty())
            {
                throw new ConditionFormatException("The value can't be enmpy, while the condition type is In or Not-in, condition: " + condition);
            }
            for (Object val : valueTempCollection)
            {
                this.valueList.add(DataTypeParser.parse(getJdbcType(), val));
            }
        }
    }
    
    /**
     * <p>Get Method   :   valueList List<Serializable></p>
     * @return valueList
     */
    public List<Object> getValueList()
    {
        return valueList;
    }
}
