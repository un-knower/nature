/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : EntityValidator.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年5月20日 下午1:51:38
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.util.List;

/**
 * 模型辅助器
 * <p>ClassName      : ModelHelper</p>
 * @author lilinhai 2018年5月20日 下午1:51:38
 * @version 1.0
 */
public interface ModelHelper
{
    
    /**
     * 获取java字段名对应的数据库表字段名
     * <p>Title         : validField lilinhai 2018年2月8日 下午11:06:40</p>
     * @param javaField 
     * void
     */
    String getTableField(String javaField);
    
    /**
     * 获取改该字段对应的JDBC类型
     * <p>Title         : getJdbcType lilinhai 2018年2月10日 上午11:14:56</p>
     * @param jdbcType
     * @return 
     * String
     */
    String getJdbcType(String javaField);
    
    /**
     * 校验字段名
     * <p>Title         : validField lilinhai 2018年2月13日 下午2:47:59</p>
     * @param javaField 
     * void
     */
    void validField(String javaField);
    
    /**
     * 判断是否存在某个字段
     * <p>Title         : existsField lilinhai 2018年4月3日 下午4:26:08</p>
     * @param fieldName
     * @return 
     * boolean
     */
    boolean existsField(String fieldName);
    
    /**
     * 返回所有字段列表
     */
    List<String> allFieldList();
    
    /**
     * 获取表名
     * <p>Title         : getTableName lilinhai 2018年5月31日 下午3:32:57</p>
     * @return 
     * String
     */
    String tableName();
}
