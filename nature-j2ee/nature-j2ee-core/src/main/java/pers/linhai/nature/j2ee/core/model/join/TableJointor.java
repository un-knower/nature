/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : TableJointor.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.join</p>
 * @Creator lilinhai 2018年6月2日 下午4:58:39
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.join;

/**
 * 数据库表连接器
 * <p>ClassName      : TableJointor</p>
 * @author lilinhai 2018年6月2日 下午4:58:39
 * @version 1.0
 */
public class TableJointor
{

    /**
     * 连接方式
     */
    private String joinType;
    
    /**
     * 左表信息
     */
    private JoinTable left;
    
    /**
     * 右表信息
     */
    private JoinTable right;

    /**
     * <p>Get Method   :   joinType String</p>
     * @return joinType
     */
    public String getJoinType()
    {
        return joinType;
    }

    /**
     * <p>Set Method   :   joinType String</p>
     * @param joinType
     */
    public void setJoinType(String joinType)
    {
        this.joinType = joinType;
    }

    /**
     * <p>Get Method   :   left JoinTable</p>
     * @return left
     */
    public JoinTable getLeft()
    {
        return left;
    }

    /**
     * <p>Set Method   :   left JoinTable</p>
     * @param left
     */
    public void setLeft(JoinTable left)
    {
        this.left = left;
    }

    /**
     * <p>Get Method   :   right JoinTable</p>
     * @return right
     */
    public JoinTable getRight()
    {
        return right;
    }

    /**
     * <p>Set Method   :   right JoinTable</p>
     * @param right
     */
    public void setRight(JoinTable right)
    {
        this.right = right;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年6月2日 下午5:04:28</p>
     * <p>Title: toString</p>
     * @return 
     * @see java.lang.Object#toString()
     */ 
    public String toString()
    {
        return "TableJointor [joinType=" + joinType + ", left=" + left + ", right=" + right + "]";
    }
}
