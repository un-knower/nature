/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : RestApi.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.generator.restapi.mappings</p>
 * @Creator lilinhai 2018年4月1日 下午4:42:37
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.restapi.mappings;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : RestApi</p>
 * @author lilinhai 2018年4月1日 下午4:42:37
 * @version 1.0
 */
public abstract class RestApi
{

    /**
     * rest接口名字
     */
    protected String apiName;
    
    /**
     * 请求的URL
     */
    protected String url;
    
    /**
     * 请求放法
     */
    protected String method;
    
    /**
     * <p>Get Method   :   url String</p>
     * @return url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * <p>Set Method   :   url String</p>
     * @param url
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * <p>Get Method   :   method String</p>
     * @return method
     */
    public String getMethod()
    {
        return method;
    }

    /**
     * <p>Set Method   :   method String</p>
     * @param method
     */
    public void setMethod(String method)
    {
        this.method = method;
    }

    /**
     * <p>Get Method   :   apiName String</p>
     * @return apiName
     */
    public String getApiName()
    {
        return apiName;
    }

    /**
     * <p>Set Method   :   apiName String</p>
     * @param apiName
     */
    public void setApiName(String apiName)
    {
        this.apiName = apiName;
    }
}
