/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : ModelField.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年5月20日 下午6:00:23
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

/**
 * <p>ClassName      : ModelField</p>
 * @author lilinhai 2018年5月20日 下午6:00:23
 * @version 1.0
 */
public interface ModelField
{
    String getJavaField();

    String getTableField();

    String getJdbcType();
}
