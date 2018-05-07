/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : Operator.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.condition</p>
 * @Creator lilinhai 2018年4月17日 下午11:24:31
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model.condition;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : Operator</p>
 * @author lilinhai 2018年4月17日 下午11:24:31
 * @version 1.0
 */
public enum Operator
{
    /**
     * <!-- lt : 小于 -->
     */
    LT(),
    
    /**
     * <!-- gt : 大于 -->
     */
    GT(),
    
    /**
     * <!-- eq : 等于 -->
     */
    EQ(),
    
    /**
     * <!-- ne : 不等于 -->
     */
    NE(),
    
    /**
     * <!-- le : 小于等于 -->
     */
    LE(),
    
    /**
     * <!-- ge : 大于等于 -->
     */
    GE(),
    
    /**
     * <!-- rg : 范围 -->
     */
    RG();
}
