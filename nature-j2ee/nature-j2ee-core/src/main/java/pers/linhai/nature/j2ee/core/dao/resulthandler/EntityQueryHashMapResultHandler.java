/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : MyResultHandler.java</p>
 * <p>Package : com.meme.crm.dao.core</p>
 * @Creator lilinhai 2018年2月12日 下午12:55:04
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.dao.resulthandler;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import pers.linhai.nature.j2ee.core.dao.EntityReflector;
import pers.linhai.nature.j2ee.core.dao.exception.ReflectException;
import pers.linhai.nature.j2ee.core.dao.processor.IEntityQueryRowDataProcessor;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.EntityBean;

/**
 * 公共结果记录处理器
 * <p>ClassName      : MyResultHandler</p>
 * @author lilinhai 2018年2月12日 下午12:55:04
 * @version 1.0
 */
public class EntityQueryHashMapResultHandler<Entity extends BaseEntity< ? >> implements ResultHandler<EntityBean>
{
    
    /**
     * 实体反射器
     */
    private EntityReflector<Entity> entityReflecter;
    
    private IEntityQueryRowDataProcessor<Entity> rowDataProcessor;
    
    /**
     * <p>Title        : RowDataResultHandler lilinhai 2018年2月13日 下午12:20:21</p>
     * @param fieldMap
     * @param entityConstructor2
     * @param entityProcessor 
     */
    public EntityQueryHashMapResultHandler(EntityReflector<Entity> entityReflecter, IEntityQueryRowDataProcessor<Entity> rowDataProcessor)
    {
        super();
        this.entityReflecter = entityReflecter;
        this.rowDataProcessor = rowDataProcessor;
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月12日 下午12:56:07</p>
     * <p>Title: handleResult</p>
     * @param resultContext 
     * @see org.apache.ibatis.session.ResultHandler#handleResult(org.apache.ibatis.session.ResultContext)
     */
    public void handleResult(ResultContext< ? extends EntityBean> resultContext)
    {
        try
        {
            EntityBean entityBean = resultContext.getResultObject();
            Entity entity = entityReflecter.getInstance(entityBean);
            rowDataProcessor.process(entityBean, entity);
        }
        catch (Throwable e1)
        {
            throw new ReflectException("handleResult-reflect error: ", e1);
        }
    }
}
