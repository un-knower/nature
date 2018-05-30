/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : StringConditionSegment.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.condition</p>
 * @Creator lilinhai 2018年3月6日 下午10:53:29
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model.condition;

/**
 * <p>ClassName      : StringConditionSegment</p>
 * @author lilinhai 2018年3月6日 下午10:53:29
 * @version 1.0
 */
public class StringSegment extends ConditionSegment
{
    
    /**
     * String条件片段
     */
    private String segment;
    
    /**
     * <p>Title        : StringSegment lilinhai 2018年3月6日 下午10:56:59</p>
     * @param conditionTemp
     * @param segment 
     */
    public StringSegment(String segment)
    {
        super(4);
        this.segment = segment;
    }
    
    /**
     * <p>Get Method   :   segment String</p>
     * @return segment
     */
    public String getSegment()
    {
        return segment;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年5月30日 下午9:25:21</p>
     * <p>Title: initialize</p>
     * <p>Description: TODO</p> 
     * @see pers.linhai.nature.j2ee.core.model.condition.ConditionSegment#initialize()
     */ 
    public void initialize()
    {
        
    }
}
