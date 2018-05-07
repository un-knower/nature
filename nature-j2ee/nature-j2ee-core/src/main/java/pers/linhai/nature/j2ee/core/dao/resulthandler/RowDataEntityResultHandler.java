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

import pers.linhai.nature.j2ee.core.dao.processor.IEntityProcessor;

/**
 * 公共结果记录处理器
 * <p>ClassName      : MyResultHandler</p>
 * @author lilinhai 2018年2月12日 下午12:55:04
 * @version 1.0
 */
public class RowDataEntityResultHandler<Entity> implements ResultHandler<Entity>
{
    
    private IEntityProcessor<Entity> entityProcessor;
    
    /**
     * <p>Title        : RowDataResultHandler lilinhai 2018年2月13日 下午12:20:21</p>
     * @param fieldMap
     * @param entityConstructor
     * @param entityProcessor 
     */
    public RowDataEntityResultHandler(IEntityProcessor<Entity> entityProcessor)
    {
        super();
        this.entityProcessor = entityProcessor;
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月12日 下午12:56:07</p>
     * <p>Title: handleResult</p>
     * @param resultContext 
     * @see org.apache.ibatis.session.ResultHandler#handleResult(org.apache.ibatis.session.ResultContext)
     */
    public void handleResult(ResultContext< ? extends Entity> resultContext)
    {
        entityProcessor.process(resultContext.getResultObject());
    }
    
}
