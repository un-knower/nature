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

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

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
    @ApiModelProperty(value = "分页查询参数，第几页。若不传，值为空，表示不按分页查找，此时默认最大返回1000条记录。")
    private Integer page;
    
    /**
     * 第X页的开始下标
     */
    @JsonIgnore
    private Integer offset;
    
    /**
     * 每页显示的大小
     */
    @ApiModelProperty(value = "分页查询参数，每页显示条数。若不传，值为空，表示不按分页查找，此时默认最大返回1000条记录。")
    private Integer size;
    
    /**
     * 排序字段集合
     */
    private List<SortField> orderFieldList;
    
    /**
     * 待返回的字段列表
     */
    private List<String> selectColumnList;
    
    /**
     * java字段列表
     */
    private List<String> selectFieldList;
    
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
     * @return orderByFieldList
     */
    @JsonIgnore
    public List<SortField> getOrderFieldList()
    {
        return orderFieldList;
    }
    
    /**
     * <p>Set Method   :   sortFieldList List<SortField></p>
     * @param sortFieldList
     */
    @ApiModelProperty(value="【即将废弃】排序声明属性，注：该属性后期将废弃，请使用新属性orderBy", name="orderBy")
    @Deprecated
    public void setSortFieldList(List<SortField> sortFieldList)
    {
        setOrderBy(sortFieldList);
    }
    
    /**
     * <p>Set Method   :   sortFieldList List<SortField></p>
     * @param sortFieldList
     */
    @ApiModelProperty(value = "排序字段列表，可以传入多个排序字段同时排序。", name = "orderBy")
    public void setOrderBy(List<SortField> orderBy)
    {
        if (orderBy != null && !orderBy.isEmpty())
        {
            for (SortField sortField : orderBy)
            {
                sortField.setColumn(getColumn(sortField.getField()));
                sortField.setTable(getTable());
            }
            this.orderFieldList = orderBy;
        }
    }
    
    /**
     * <p>Get Method   :   returnFieldList List<String></p>
     * @return returnFieldList
     */
    @JsonIgnore
    public List<String> getSelectColumnList()
    {
        return (selectColumnList == null || selectColumnList.isEmpty()) ? columnList() : selectColumnList;
    }
    
    /**
     * <p>Get Method   :   selectFieldList List<String></p>
     * @return selectFieldList
     */
    @JsonIgnore
    public List<String> getSelectFieldList()
    {
        return (selectFieldList == null || selectFieldList.isEmpty()) ? fieldList() : selectFieldList;
    }

    /**
     * <p>Set Method   :   returnFieldList List<String></p>
     * please use setSelect
     * @param select
     */
    @ApiModelProperty(value="【即将废弃】返回字段声明属性，注：该属性后期将废弃，请使用新属性select", name="select")
    @Deprecated
    public void setReturnFieldList(List<String> returnFieldList)
    {
        setSelect(returnFieldList);
    }
    
    /**
     * <p>Set Method   :   returnFieldList List<String></p>
     * @param select
     */
    @ApiModelProperty(value = "需要查询并返回的字段列表，若不传，默认返回所有字段。", name = "select")
    public void setSelect(List<String> select)
    {
        if (select != null && !select.isEmpty())
        {
            List<String> returnFieldListTemp = new ArrayList<String>();
            for (String fieldName : select)
            {
                returnFieldListTemp.add(getColumn(fieldName));
            }
            this.selectFieldList = select;
            this.selectColumnList = returnFieldListTemp;
        }
    }
    
    public String toString()
    {
        return "BaseQuery [page=" + page + ", offset=" + offset + ", size=" + size + ", selectColumnList=" + selectColumnList + "]";
    }
}
