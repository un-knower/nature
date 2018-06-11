/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : SelectBuilder.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.builder</p>
 * @Creator lilinhai 2018年5月26日 下午9:04:40
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.builder;

import java.util.ArrayList;
import java.util.List;

import pers.linhai.nature.j2ee.core.model.ModelField;
import pers.linhai.nature.j2ee.core.model.exception.QueryBuildException;

/**
 * 查询字段构建器
 * <p>ClassName      : SelectBuilder</p>
 * @author lilinhai 2018年5月26日 下午9:04:40
 * @version 1.0
 */
public abstract class BaseSelectBuilder<EntitySelectFieldBuilder>
{
    
    /**
     * 是否设置了返回所有字段
     */
    private boolean isReturnAll;
    
    /**
     * 是否设置了返回某个字段
     */
    private boolean isSetReturn;
    
    /**
     * 待返回的字段列表
     */
    private List<String> returnFieldList = new ArrayList<String>();
    
    private EntitySelectFieldBuilder entitySelectBuilder;
    
    /**
     * <p>Title        : SelectBuilder lilinhai 2018年5月26日 下午10:37:28</p>
     * @param entitySelectBuilder 
     */ 
    @SuppressWarnings("unchecked")
    public BaseSelectBuilder()
    {
        this.entitySelectBuilder = (EntitySelectFieldBuilder)this;
    }

    /**
     * 查询指定字段
     * <p>Title         : select lilinhai 2018年5月26日 下午10:26:22</p>
     * @param modelField 
     * void
     */
    protected void select(ModelField modelField)
    {
        if (isReturnAll)
        {
            //异常处理：returnAll方法设置后，不能在设置return某个具体字段
            throw new QueryBuildException("After setting the returnAll method, you cannot set a specific field in return.");
        }
        returnFieldList.add(modelField.getField());
        isSetReturn = true;
    }
    
    /**
     * 查询所有字段
     * <p>Title         : all lilinhai 2018年5月27日 上午12:23:39</p>
     * @return 
     * EntitySelectBuilder
     */
    public EntitySelectFieldBuilder all()
    {
        isReturnAll = true;
        isSetReturn = true;
        return entitySelectBuilder;
    }
    
    /**
     * 检索：ID
     */
    public EntitySelectFieldBuilder id()
    {
        select(ModelField.ID);
        return entitySelectBuilder;
    }
    
    /**
     * 检索：CREATE_TIME
     */
    public EntitySelectFieldBuilder createTime()
    {
        select(ModelField.CREATE_TIME);
        return entitySelectBuilder;
    }
    
    /**
     * 检索：UPDATE_TIME
     */
    public EntitySelectFieldBuilder updateTime()
    {
        select(ModelField.UPDATE_TIME);
        return entitySelectBuilder;
    }

    /**
     * <p>Get Method   :   returnFieldList List<String></p>
     * @return returnFieldList
     */
    public List<String> build()
    {
        return returnFieldList;
    }
    
    boolean isSetSelectField()
    {
        return isSetReturn || isReturnAll;
    }
}
