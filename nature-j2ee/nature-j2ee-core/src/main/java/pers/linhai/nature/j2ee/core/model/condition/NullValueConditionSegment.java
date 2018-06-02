/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : NullValueCondition.java</p>
 * <p>Package     : com.meme.crm.model.core.condition</p>
 * @Creator lilinhai 2018年2月15日 下午3:42:07
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model.condition;

import pers.linhai.nature.j2ee.core.model.Condition;

/**
 * 如果表中的某个列是可选的，那么我们可以在不向该列添加值的情况下插入新记录或更新已有的记录。这意味着该字段将以
 * <p>ClassName      : NullValueCondition</p>
 * @author lilinhai 2018年2月15日 下午3:42:07
 * @version 1.0
 */
public class NullValueConditionSegment extends ConditionSegment
{
    
    /**
     * <p>Title        : NullValueCondition lilinhai 2018年2月15日 下午4:24:26</p>
     * @param fieldName 
     */
    public NullValueConditionSegment(Condition condition)
    {
        super(condition, 3);
    }
}
