/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BaseBean.java</p>
 * <p>Package     : com.meme.crm.model.core</p>
 * @Creator lilinhai 2018年2月7日 下午4:29:28
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询对象基类
 * <p>ClassName      : BaseQuery</p>
 * @author lilinhai 2018年2月9日 下午11:45:11
 * @version 1.0
 */
public abstract class BaseQuery extends BaseModel
{
    
    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年4月22日 下午9:39:18</p>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 第几页
     */
    private Integer page;
    
    /**
     * 第X页的开始下标
     */
    private Integer offset;
    
    /**
     * 每页显示的大小
     */
    private Integer size;
    
    /**
     * 排序字段集合
     */
    private List<SortField> sortFieldList;
    
    /**
     * 待返回的字段列表
     */
    private List<String> select;
    
    /**
     * <p>Title        : BaseQuery lilinhai 2018年2月11日 下午11:40:39</p>
     * @param tableName 
     */
    public BaseQuery(String tableName)
    {
        super(tableName);
    }
    
    /**
     * <p>Get Method   :   page Integer</p>
     * @return page
     */
    public Integer getPage()
    {
        return page;
    }
    
    /**
     * <p>Set Method   :   page Integer</p>
     * @param page
     */
    public void setPage(Integer page)
    {
        this.page = page;
    }
    
    /**
     * <p>Get Method   :   offset Integer</p>
     * @return offset
     */
    public Integer getOffset()
    {
        return this.offset = this.page * this.size;
    }
    
    /**
     * <p>Get Method   :   size Integer</p>
     * @return size
     */
    public Integer getSize()
    {
        return size;
    }
    
    /**
     * <p>Set Method   :   size Integer</p>
     * @param size
     */
    public void setSize(Integer size)
    {
        this.size = size;
    }
    
    /**
     * <p>Get Method   :   sortFieldList List<SortField></p>
     * @return sortFieldList
     */
    public List<SortField> getSortFieldList()
    {
        return sortFieldList;
    }
    
    /**
     * <p>Set Method   :   sortFieldList List<SortField></p>
     * @param sortFieldList
     */
    public void setSortFieldList(List<SortField> sortFieldList)
    {
        if (sortFieldList != null && !sortFieldList.isEmpty())
        {
            for (SortField sortField : sortFieldList)
            {
                sortField.setFieldName(getTableField(sortField.getFieldName()));
            }
            this.sortFieldList = sortFieldList;
        }
    }
    
    /**
     * <p>Get Method   :   returnFieldList List<String></p>
     * @return returnFieldList
     */
    public List<String> getSelect()
    {
        return (select == null || select.isEmpty()) ? allFieldList() : select;
    }
    
    /**
     * <p>Set Method   :   returnFieldList List<String></p>
     * @param select
     */
    public void setSelect(List<String> select)
    {
        if (select != null && !select.isEmpty())
        {
            List<String> returnFieldListTemp = new ArrayList<String>();
            for (String fieldName : select)
            {
                returnFieldListTemp.add(getTableField(fieldName));
            }
            this.select = returnFieldListTemp;
        }
    }
    
    public String toString()
    {
        return "BaseQuery [page=" + page + ", offset=" + offset + ", size=" + size + ", sortFieldList=" + sortFieldList + ", select=" + select + "]";
    }
}
