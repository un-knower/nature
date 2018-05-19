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
import java.util.Locale;

import pers.linhai.nature.utils.NamingUtils;

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
    private List<String> returnFieldList;
    
    /**
     * <p>Title        : BaseQuery lilinhai 2018年2月11日 下午11:40:39</p>
     * @param tableName 
     */
    public BaseQuery(String tableName)
    {
        super(tableName);
    }
    
    /**
     * 返回所有字段列表
     * <p>Title         : getAllFieldList lilinhai 2018年2月12日 上午11:23:13</p>
     * @return 
     * List<String>
     */
    public abstract List<String> getAllFieldList();
    
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
        
        if (size != null && offset == null)
        {
            // 设置查询偏移量
            setOffset(this.page * size);
        }
    }
    
    /**
     * <p>Get Method   :   offset Integer</p>
     * @return offset
     */
    public Integer getOffset()
    {
        return offset;
    }
    
    /**
     * <p>Set Method   :   offset Integer</p>
     * @param offset
     */
    public void setOffset(Integer offset)
    {
        this.offset = offset;
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
        
        if (page != null && offset == null)
        {
            // 设置查询偏移量
            setOffset(page * this.size);
        }
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
        this.sortFieldList = sortFieldList;
    }
    
    /**
     * <p>Get Method   :   returnFieldList List<String></p>
     * @return returnFieldList
     */
    public List<String> getReturnFieldList()
    {
        return returnFieldList == null ? getAllFieldList() : returnFieldList;
    }
    
    /**
     * <p>Set Method   :   returnFieldList List<String></p>
     * @param returnFieldList
     */
    public void setReturnFieldList(List<String> returnFieldList)
    {
        if (returnFieldList != null)
        {
            List<String> returnFieldListTemp = new ArrayList<String>();
            for (String fieldName : returnFieldList)
            {
                validField(fieldName);
                returnFieldListTemp.add(getTableField(fieldName));
            }
            this.returnFieldList = returnFieldListTemp;
        }
    }
    
    /**
     * 排序字段对象
     * <p>ClassName      : SortField</p>
     * @author lilinhai 2018年2月8日 下午2:45:03
     * @version 1.0
     */
    public static class SortField
    {
        
        /**
         * 排序字段名
         */
        private String fieldName;
        
        /**
         * 排序方向
         */
        private String direction = Direction.ASC.name().toLowerCase(Locale.ENGLISH);
        
        /**
         * <p>Get Method   :   fieldName String</p>
         * @return fieldName
         */
        public String getFieldName()
        {
            return fieldName;
        }
        
        /**
         * <p>Set Method   :   fieldName String</p>
         * @param fieldName
         */
        public void setFieldName(String fieldName)
        {
            if (fieldName == null)
            {
                return;
            }
            this.fieldName = NamingUtils.storeFieldName(fieldName);
        }
        
        /**
         * <p>Get Method   :   direction Direction</p>
         * @return direction
         */
        public String getDirection()
        {
            return direction;
        }
        
        /**
         * <p>Set Method   :   direction Direction</p>
         * @param direction
         */
        public void setDirection(String direction)
        {
            this.direction = Direction.fromString(direction).name().toLowerCase(Locale.ENGLISH);
        }
        
        /** 
         * <p>Overriding Method: lilinhai 2018年2月8日 下午3:08:05</p>
         * <p>Title: toString</p>
         * @return 
         * @see java.lang.Object#toString()
         */
        public String toString()
        {
            return "SortField [fieldName=" + fieldName + ", direction=" + direction + "]";
        }
        
        /**
         * Enumeration for sort directions.
         * @author Oliver Gierke
         */
        public static enum Direction
        {
            
            ASC, DESC;
            
            /**
             * Returns whether the direction is ascending.
             * 
             * @return
             * @since 1.13
             */
            public boolean isAscending()
            {
                return this.equals(ASC);
            }
            
            /**
             * Returns whether the direction is descending.
             * 
             * @return
             * @since 1.13
             */
            public boolean isDescending()
            {
                return this.equals(DESC);
            }
            
            /**
             * Returns the {@link Direction} enum for the given {@link String} value.
             * 
             * @param value
             * @throws IllegalArgumentException in case the given value cannot be parsed into an enum value.
             * @return
             */
            public static Direction fromString(String value)
            {
                
                try
                {
                    return Direction.valueOf(value.toUpperCase(Locale.US));
                }
                catch (Exception e)
                {
                    throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
                }
            }
            
            /**
             * Returns the {@link Direction} enum for the given {@link String} or null if it cannot be parsed into an enum
             * value.
             * 
             * @param value
             * @return
             */
            public static Direction fromStringOrNull(String value)
            {
                
                try
                {
                    return fromString(value);
                }
                catch (IllegalArgumentException e)
                {
                    return null;
                }
            }
        }
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月15日 上午9:08:57</p>
     * <p>Title: toString</p>
     * <p>Description: TODO</p>
     * @return 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "BaseQuery [page=" + page + ", offset=" + offset + ", size=" + size + ", sortFieldList=" + sortFieldList + ", returnFieldList=" + returnFieldList + "]";
    }
}
