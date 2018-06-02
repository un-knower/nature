/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : MyResultHandler.java</p>
 * <p>Package : com.meme.crm.dao.core</p>
 * @Creator lilinhai 2018年2月12日 下午12:55:04
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.dao.resulthandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import pers.linhai.nature.j2ee.core.dao.EntityReflector;
import pers.linhai.nature.j2ee.core.dao.exception.ReflectException;
import pers.linhai.nature.j2ee.core.dao.processor.IRowDataJointQueryProcessor;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.EntityBean;
import pers.linhai.nature.j2ee.core.model.JointEntityBean;
import pers.linhai.nature.j2ee.core.model.JointQueryResultBean;

/**
 * 公共结果记录处理器
 * <p>ClassName      : RowDataHashMapJointQueryResultHandler</p>
 * @author lilinhai 2018年6月12日 下午12:55:04
 * @version 1.0
 */
public class RowDataHashMapJointQueryResultHandler implements ResultHandler<JointQueryResultBean>
{
    
    /**
     * 实体反射器map映射
     */
    private Map<Class<? extends BaseEntity<?>>, EntityReflector<? extends BaseEntity<?>>> entityReflectorMap = new HashMap<Class<? extends BaseEntity<?>>, EntityReflector<? extends BaseEntity<?>>>();
    
    private IRowDataJointQueryProcessor rowDataJointQueryProcessor;
    
    /**
     * process方法反射函数
     */
    private Method processMethod;
    
    /**
     * process方法的参数类型
     */
    private Class<?>[] params;
    
    /**
     * <p>Title        : RowDataResultHandler lilinhai 2018年2月13日 下午12:20:21</p>
     * @param fieldMap
     * @param entityConstructor2
     * @param entityProcessor 
     */
    public RowDataHashMapJointQueryResultHandler(Map<Class<? extends BaseEntity<?>>, EntityReflector<? extends BaseEntity<?>>> entityReflectorMap, IRowDataJointQueryProcessor rowDataJointQueryProcessor)
    {
        try
        {
            this.entityReflectorMap = entityReflectorMap;
            Method[] declaredMethods = rowDataJointQueryProcessor.getClass().getDeclaredMethods();
            for (Method method : declaredMethods)
            {
                if (method.getName().equals("process"))
                {
                    processMethod = method;
                    processMethod.setAccessible(true);
                    break;
                }
            }
            
            if (processMethod == null)
            {
                throw new ReflectException("Can't find the process method in the class: " + rowDataJointQueryProcessor.getClass().getName());
            }
            
            Class<?>[] params = processMethod.getParameterTypes();
            for (Class< ? > class1 : params)
            {
                if (class1 != JointEntityBean.class && class1.getSuperclass() != BaseEntity.class)
                {
                    throw new ReflectException("The param-type of the process method in the class must be EntityBean or BaseEntity, but it is: " + class1.getName());
                }
            }
            
            this.params = params;
            this.rowDataJointQueryProcessor = rowDataJointQueryProcessor;
        }
        catch (Throwable e)
        {
            if (e instanceof ReflectException)
            {
                throw (ReflectException) e;
            }
            throw new ReflectException("Can't find the process method in the class: " + rowDataJointQueryProcessor.getClass().getName());
        }
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月12日 下午12:56:07</p>
     * <p>Title: handleResult</p>
     * @param resultContext 
     * @see org.apache.ibatis.session.ResultHandler#handleResult(org.apache.ibatis.session.ResultContext)
     */
    public void handleResult(ResultContext< ? extends JointQueryResultBean> resultContext)
    {
        try
        {
            JointQueryResultBean jointQueryResultBean = resultContext.getResultObject();
            JointEntityBean jointEntityBean = jointQueryResultBean.getJointEntityBean();
            
            // 设置为已经初始化
            jointEntityBean.setInited();
            
            Object[] objs = new Object[params.length];
            
            EntityBean entityBean = null;
            int i = 0;
            for (Class< ? > class1 : params)
            {
                if (class1 == JointEntityBean.class)
                {
                    objs[i] = jointEntityBean;
                }
                else
                {
                    entityBean = jointEntityBean.get(class1.getSimpleName());
                    objs[i] = entityReflectorMap.get(class1).getInstance(entityBean == null ? new EntityBean(true) : entityBean);
                }
                i++;
            }
            
            processMethod.invoke(rowDataJointQueryProcessor, objs);
        }
        catch (Throwable e1)
        {
            throw new ReflectException("handleResult-reflect error: ", e1);
        }
    }
}
