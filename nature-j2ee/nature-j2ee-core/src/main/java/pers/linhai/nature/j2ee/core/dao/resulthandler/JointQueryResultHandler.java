/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : MyResultHandler.java</p>
 * <p>Package : com.meme.crm.dao.core</p>
 * @Creator lilinhai 2018年2月12日 下午12:55:04
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.dao.resulthandler;

import java.lang.reflect.Method;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import pers.linhai.nature.j2ee.core.dao.exception.ReflectException;
import pers.linhai.nature.j2ee.core.dao.processor.JointQueryRowDataProcessor;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.EntityBean;
import pers.linhai.nature.j2ee.core.model.ModelReflectorCache;
import pers.linhai.nature.j2ee.core.model.join.JointEntityBean;
import pers.linhai.nature.j2ee.core.model.join.JointQueryResultBean;

/**
 * 公共结果记录处理器
 * <p>ClassName      : RowDataHashMapJointQueryResultHandler</p>
 * @author lilinhai 2018年6月12日 下午12:55:04
 * @version 1.0
 */
public class JointQueryResultHandler implements ResultHandler<JointQueryResultBean>
{
    private JointQueryRowDataProcessor rowDataJointQueryProcessor;
    
    /**
     * process方法反射函数
     */
    private Method processMethod;
    
    /**
     * process方法的参数类型
     */
    private Class<?>[] paramClasses;
    
    /**
     * <p>Title        : RowDataResultHandler lilinhai 2018年2月13日 下午12:20:21</p>
     * @param fieldMap
     * @param entityConstructor2
     * @param entityProcessor 
     */
    public JointQueryResultHandler(JointQueryRowDataProcessor rowDataJointQueryProcessor)
    {
        try
        {
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
            
            this.paramClasses = params;
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
            
            Object[] objs = new Object[paramClasses.length];
            
            EntityBean entityBean = null;
            int i = 0;
            for (Class< ? > paramClass : paramClasses)
            {
                if (paramClass == JointEntityBean.class)
                {
                    objs[i] = jointEntityBean;
                }
                else
                {
                    entityBean = jointEntityBean.get(paramClass);
                    if (entityBean == null)
                    {
                        objs[i] = null;
                    }
                    else
                    {
                        objs[i] = ModelReflectorCache.getInstance().get(paramClass).getInstance(entityBean);
                    }
                }
                i++;
            }
            
            // 加到集合中，返回到前端用
            rowDataJointQueryProcessor.addJointEntityBean(jointEntityBean);
            processMethod.invoke(rowDataJointQueryProcessor, objs);
        }
        catch (Throwable e1)
        {
            throw new ReflectException("handleResult-reflect error: ", e1);
        }
    }
}
