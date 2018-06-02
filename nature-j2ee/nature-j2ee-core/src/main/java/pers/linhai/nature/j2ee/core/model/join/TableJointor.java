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
     * 序号，第一个的时候，需要完整表达
     */
    private int index;
    
    /**
     * 连接方式
     */
    private String joinType;
    
    /**
     * 左表信息
     */
    private JoinField left;
    
    /**
     * 右表信息
     */
    private JoinField right;

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
    public JoinField getLeft()
    {
        return left;
    }

    /**
     * <p>Set Method   :   left JoinTable</p>
     * @param left
     */
    public void setLeft(JoinField left)
    {
        this.left = left;
    }

    /**
     * <p>Get Method   :   right JoinTable</p>
     * @return right
     */
    public JoinField getRight()
    {
        return right;
    }

    /**
     * <p>Set Method   :   right JoinTable</p>
     * @param right
     */
    public void setRight(JoinField right)
    {
        this.right = right;
    }
    
    /**
     * <p>Get Method   :   index int</p>
     * @return index
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * <p>Set Method   :   index int</p>
     * @param index
     */
    public void setIndex(int index)
    {
        this.index = index;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年6月2日 下午9:37:02</p>
     * <p>Title: toString</p>
     * @return 
     * @see java.lang.Object#toString()
     */ 
    @Override
    public String toString()
    {
        return "TableJointor [index=" + index + ", joinType=" + joinType + ", left=" + left + ", right=" + right + "]";
    }
}
