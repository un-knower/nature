/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : RestErrorCode.java</p>
 * <p>Package     : com.meme.crm.web.constant</p>
 * @Creator lilinhai 2018年2月8日 上午11:55:38
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.constant;

/**
 * <p>ClassName      : RestErrorCode</p>
 * @author lilinhai 2018年2月8日 上午11:55:38
 * @version 1.0
 */
public interface BaseErrorCode
{
    
    /*****[10000 - 20000)删除相关错误码**********************************************/
    
    /**
     * rest接口不可用
     */
    int REST_API_NOT_AVAILABLE = 5000;
    
    /**
     * 删除失败
     */
    int DELETE_FAIL = 10001;
    
    /**
     * 删除出现异常
     */
    int DELETE_EXCEPTION = 10002;
    
    /*****[10000 - 20000)删除相关错误码**********************************************/
    
    /*****[20000 - 30000)添加相关错误码**********************************************/
    
    /**
     * 添加失败
     */
    int INSERT_FAIL = 20001;
    
    /**
     * 添加出现异常
     */
    int INSERT_EXCEPTION = 20002;
    
    /**
     * 选择性添加失败
     */
    int INSERT_SELECTIVE_FAIL = 20011;
    
    /**
     * 选择性添加出现异常
     */
    int INSERT_SELECTIVE_EXCEPTION = 20012;
    
    /**
     * 添加失败
     */
    int UPDATE_FAIL = 25001;
    
    /**
     * 添加出现异常
     */
    int UPDATE_EXCEPTION = 25002;
    
    /*****[20000 - 30000)添加相关错误码**********************************************/
    
    /*****[30000 - 40000)查询相关错误码**********************************************/
    
    /**
     * 单个查询失败
     */
    int GET_FAIL = 30001;
    
    /**
     * 单个查询出现异常
     */
    int GET_EXCEPTION = 30002;
    
    /**
     * 组合查询失败
     */
    int QUERY_FAIL = 30011;
    
    /**
     * 组合查询出现异常
     */
    int QUERY_EXCEPTION = 30012;
    
    /**
     * 分页查询失败
     */
    int PAGE_QUERY_FAIL = 30021;
    
    /**
     * 分页查询出现异常
     */
    int PAGE_QUERY_EXCEPTION = 30022;
    
    /**
     * 分页查询size太大，超过1000
     */
    int PAGE_QUERY_SIZE_TOO_LARGE_EXCEPTION = 30023;
    
    /*****[30000 - 40000)添加相关错误码**********************************************/
    
    /*****[40000 - 50000)添加相关错误码**********************************************/
    
    /**
     * 校验器构建不支持
     */
    int QUERY_VALIDATOR_BUILD_NOT_SUPPORT = 40001;
    
    /**
     * 查询字段不被允许
     */
    int QUERY_FIELD_NOT_ALLOWED = 40005;
    
    /**
     * 查询条件字段不被允许
     */
    int QUERY_CONDITION_FIELD_NOT_ALLOWED = 40010;
    
    /**
     * 查询条件字段的参数值不被允许
     */
    int QUERY_CONDITION_FIELD_VALUE_NOT_ALLOWED = 40011;
    
    /**
     * 查询where条件未定义
     */
    int QUERY_WHERE_CONDITION_IS_UNDEFINED = 40012;
    
    /**
     * 查询返回记录数未设置
     */
    int QUERY_RETURN_SIZE_NOT_SET = 40020;
    
    /**
     * 查询返回记录数超过最大限度
     */
    int QUERY_RETURN_SIZE_EXCEED = 40021;
    
    
    /*****[40000 - 50000)添加相关错误码**********************************************/
    
    
    /*****[70000 - 80000)关联查询错误码范围定义**************************************************************************/
    
    /**
     * processorId解密盐值未定义
     */
    int JOINT_QUERY_PROCESSORID_AES_DECRYPT_SALT_UNDEFINED = 70005;
    
    /**
     * processorId未定义
     */
    int JOINT_QUERY_PROCESSORID_UNDEFINED = 70010;
    
    /**
     * processorId非法
     */
    int JOINT_QUERY_PROCESSORID_ILLEGAL = 70011;
    
    /**
     * processorId不支持
     */
    int JOINT_QUERY_PROCESSORID_NOT_SUPPORTED = 70012;
    
    /**
     * 关联查询结果处理失败
     */
    int JOINT_QUERY_PROCESS_ERROR = 70020;
    
    /*****[70000 - 80000)关联查询错误码范围定义**************************************************************************/
}
