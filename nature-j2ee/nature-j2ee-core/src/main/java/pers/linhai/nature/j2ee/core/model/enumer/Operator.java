/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : Operator.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.condition</p>
 * @Creator lilinhai 2018年4月17日 下午11:24:31
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model.enumer;

/**
 * 数据库所有操作符的枚举
 * <p>ClassName      : Operator</p>
 * @author lilinhai 2018年4月17日 下午11:24:31
 * @version 1.0
 */
public enum Operator
{
    /**
     * 小于
     */
    LESS_THAN("<"),
    
    /**
     * 大于
     */
    GREATER_THAN(">"),
    
    /**
     * 小于等于
     */
    LESS_THAN_OR_EQUAL("<="),
    
    /**
     * 大于等于
     */
    GREATER_THAN_OR_EQUAL(">="),
    
    /**
     * 等于
     */
    EQUAL("="),
    
    /**
     * 不等于
     */
    NOT_EQUAL("!="),
    
    /**
     * 模糊搜索like
     */
    LIKE("like"),
    
    /**
     * 包含in
     */
    IN("in"),
    
    /**
     * 不包含in
     */
    NOT_IN("not in"),
    
    /**
     * is null
     */
    IS_NULL("is null"),
    
    /**
     * is not null
     */
    IS_NOT_NULL("is not null");
    
    /**
     * <p>Title        : Operator lilinhai 2018年5月15日 下午6:07:12</p>
     * @param value 
     */ 
    private Operator(String value)
    {
        this.value = value;
    }

    /**
     * 运算符的值
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
}
