/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : MyResultHandler.java</p>
 * <p>Package : com.meme.crm.dao.core</p>
 * @Creator lilinhai 2018年2月12日 下午12:55:04
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.dao;


import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import pers.linhai.nature.j2ee.core.exception.ReflectException;
import pers.linhai.nature.j2ee.core.model.BaseEntity;


/**
 * 公共结果记录处理器
 * <p>ClassName      : MyResultHandler</p>
 * @author lilinhai 2018年2月12日 下午12:55:04
 * @version 1.0
 */
public class RowDataHashMapResultHandler<Entity extends BaseEntity<?>> implements ResultHandler<Map<String, Serializable>>
{

    /**
     * 实体的所有字段列表
     */
    private Map<String, Field> fieldMap;

    private Constructor<Entity> entityConstructor;
    
    private IEntityProcessor<Entity> entityProcessor;
    
    /**
     * <p>Title        : RowDataResultHandler lilinhai 2018年2月13日 下午12:20:21</p>
     * @param fieldMap
     * @param entityConstructor
     * @param entityProcessor 
     */ 
    public RowDataHashMapResultHandler(Map<String, Field> fieldMap, Constructor<Entity> entityConstructor, IEntityProcessor<Entity> entityProcessor)
    {
        super();
        this.fieldMap = fieldMap;
        this.entityConstructor = entityConstructor;
        this.entityProcessor = entityProcessor;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月12日 下午12:56:07</p>
     * <p>Title: handleResult</p>
     * @param resultContext 
     * @see org.apache.ibatis.session.ResultHandler#handleResult(org.apache.ibatis.session.ResultContext)
     */
    public void handleResult(ResultContext<? extends Map<String, Serializable>> resultContext)
    {
        try
        {
            Map<String, Serializable> rowData = resultContext.getResultObject();
            Entity entity = entityConstructor.newInstance();
            for (Entry<String, Serializable> e : rowData.entrySet())
            {
                fieldMap.get(e.getKey()).set(entity, e.getValue());
            }
            entityProcessor.process(entity);
        }
        catch (Throwable e1)
        {
            throw new ReflectException("handleResult-reflect error: ", e1);
        }
    }
}
