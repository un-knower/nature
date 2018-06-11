/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : ModelField.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年5月20日 下午6:00:23
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import pers.linhai.nature.j2ee.core.model.enumer.JdbcType;

/**
 * <p>ClassName      : ModelField</p>
 * @author lilinhai 2018年5月20日 下午6:00:23
 * @version 1.0
 */
public interface ModelField
{
    
    /**
     * 公共ID
     */
    ModelField ID = new ModelField()
    {
        public String getTable()
        {
            return null;
        }
        
        public String getJdbcType()
        {
            return JdbcType.BIGINT.getType();
        }
        
        public String getField()
        {
            return "id";
        }
        
        public String getEntity()
        {
            return null;
        }
        
        public String getColumn()
        {
            return "id";
        }
    };
    
    /**
     * 公共创建时间
     */
    ModelField CREATE_TIME = new ModelField()
    {
        public String getTable()
        {
            return null;
        }
        
        public String getJdbcType()
        {
            return JdbcType.TIMESTAMP.getType();
        }
        
        public String getField()
        {
            return "createTime";
        }
        
        public String getEntity()
        {
            return null;
        }
        
        public String getColumn()
        {
            return "create_time";
        }
    };
    
    /**
     * 公共修改时间
     */
    ModelField UPDATE_TIME = new ModelField()
    {
        public String getTable()
        {
            return null;
        }
        
        public String getJdbcType()
        {
            return JdbcType.TIMESTAMP.getType();
        }
        
        public String getField()
        {
            return "updateTime";
        }
        
        public String getEntity()
        {
            return null;
        }
        
        public String getColumn()
        {
            return "update_time";
        }
    };
    
    
    String getEntity();
    
    String getTable();
    
    String getField();

    String getColumn();

    String getJdbcType();
}
