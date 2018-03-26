/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : RestErrorCode.java</p>
 * <p>Package     : com.meme.crm.web.constant</p>
 * @Creator lilinhai 2018年2月8日 上午11:55:38
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.web.constant;

/**
 * <p>ClassName      : RestErrorCode</p>
 * @author lilinhai 2018年2月8日 上午11:55:38
 * @version 1.0
 */
public interface RestErrorCode
{

    /*****[10000 - 20000)删除相关错误码**********************************************/
    
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
    
    /*****[30000 - 40000)添加相关错误码**********************************************/
}
