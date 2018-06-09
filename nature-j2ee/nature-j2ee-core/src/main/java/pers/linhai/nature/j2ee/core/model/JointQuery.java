/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : JoinQuery.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年5月26日 下午5:16:54
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import pers.linhai.nature.j2ee.core.model.enumer.JoinType;
import pers.linhai.nature.j2ee.core.model.exception.QueryBuildException;
import pers.linhai.nature.j2ee.core.model.join.SelectField;
import pers.linhai.nature.j2ee.core.model.join.TableJointor;
import pers.linhai.nature.utils.StringUtils;

/**
 * 关联查询
 * <p>ClassName      : JoinQuery</p>
 * @author lilinhai 2018年5月26日 下午5:16:54
 * @version 1.0
 */
public class JointQuery
{
    
    /**
     * 结果处理器唯一标示
     */
    private String processorId;
    
    /**
     * 第几页
     */
    private Integer page;
    
    /**
     * 第X页的开始下标
     */
    @JsonIgnore
    private Integer offset;
    
    /**
     * 每页显示的大小
     */
    private Integer size;
    
    /**
     * 数据库表连接器集合
     */
    private List<TableJointor> tableJointorList;
    
    /**
     * 需要查询的字段列表
     */
    private List<SelectField> selectColumnList;
    
    /**
     * 查询条件集合
     */
    private Where where;
    
    /**
     * 排序字段集合
     */
    private List<SortField> orderFieldList;
    
    /**
     * <p>Set Method   :   sortFieldList List<SortField></p>
     * @param sortFieldList
     */
    public void setOrderBy(List<SortField> orderBy)
    {
        if (orderBy != null && !orderBy.isEmpty())
        {
            for (SortField sortField : orderBy)
            {
                if (StringUtils.isEmpty(sortField.getEntity()))
                {
                    throw new QueryBuildException(" The entity name can not be empty in SortField[" + sortField.getField() + "]");
                }
                ModelHelper modelHelper = ModelHelperCache.getInstance().get(sortField.getEntity());
                if (modelHelper == null)
                {
                    throw new QueryBuildException(" Illegal entity name : " + sortField.getEntity());
                }
                sortField.setTable(modelHelper.table());
                sortField.setColumn(modelHelper.getColumn(sortField.getField()));
            }
            this.orderFieldList = orderBy;
        }
    }
    
    /**
     * <p>Get Method   :   selectFieldList List<SelectField></p>
     * @return selectFieldList
     */
    @JsonIgnore
    public List<SelectField> getSelectColumnList()
    {
        if (selectColumnList == null || selectColumnList.isEmpty())
        {
            throw new QueryBuildException("The select-field of the join-query cannot be empty ");
        }
        return selectColumnList;
    }

    /**
     * <p>Set Method   :   selectFieldList List<SelectField></p>
     * @param selectColumnList
     */
    public void setSelect(List<SelectField> select)
    {
        this.selectColumnList = select;
        if (selectColumnList == null || selectColumnList.isEmpty())
        {
            throw new QueryBuildException("The select-field of the join-query cannot be empty ");
        }
        
        for (SelectField selectField : select)
        {
            if (StringUtils.isEmpty(selectField.getEntity()))
            {
                throw new QueryBuildException(" The entity name can not be empty in SelectField[" + selectField.getFieldList() + "]");
            }
            ModelHelper modelHelper = ModelHelperCache.getInstance().get(selectField.getEntity());
            if (modelHelper == null)
            {
                throw new QueryBuildException(" Illegal entity name : " + selectField.getEntity());
            }
            
            if (selectField.getFieldList() == null || selectField.getFieldList().isEmpty())
            {
                throw new QueryBuildException("The select-field of the join-query cannot be empty ");
            }
            
            selectField.setTable(modelHelper.table());
            
            List<String> columnList = new ArrayList<String>();
            for (String field : selectField.getFieldList())
            {
                columnList.add(modelHelper.getColumn(field));
            }
            selectField.setColumnList(columnList);
        }
    }
    
    /**
     * <p>Get Method   :   tableJointorList List<TableJointor></p>
     * @return tableJointorList
     */
    @JsonIgnore
    public List<TableJointor> getTableJointorList()
    {
        if (tableJointorList == null || tableJointorList.isEmpty())
        {
            throw new QueryBuildException("The from-field information of the join-table-query can not be empty.");
        }
        return tableJointorList;
    }

    /**
     * <p>Set Method   :   tableJointorList List<TableJointor></p>
     * @param tableJointorList
     */
    public void setFrom(List<TableJointor> from)
    {
        this.tableJointorList = from;
        if (tableJointorList == null || tableJointorList.isEmpty())
        {
            throw new QueryBuildException("The from-field information of the join-table-query can not be empty.");
        }
        
        int index = 0;
        for (TableJointor tableJointor : from)
        {
            // 校验joinType
            JoinType joinType = JoinType.transfer(tableJointor.getJoinType());
            if (joinType == null)
            {
                throw new QueryBuildException("The join-type of the join-query is illegal: " + tableJointor.getJoinType());
            }
            
            //  序号，第一个的时候，需要完整表达
            tableJointor.setIndex(++index);
            
            // 设置为数据库对应的值
            tableJointor.setJoinType(joinType.getDatabaseValue());
            
            // 校验衔接表信息
            if (tableJointor.getLeft() == null || tableJointor.getRight() == null)
            {
                throw new QueryBuildException("The left-or-right-table information of the join-query can not be empty.");
            }
            
            ModelHelper modelHelper = ModelHelperCache.getInstance().get(tableJointor.getLeft().getEntity());
            if (modelHelper == null)
            {
                throw new QueryBuildException(" Illegal left-entity name : " + tableJointor.getLeft().getEntity());
            }
            
            tableJointor.getLeft().setTable(modelHelper.table());
            tableJointor.getLeft().setColumn(modelHelper.getColumn(tableJointor.getLeft().getField()));
            
            modelHelper = ModelHelperCache.getInstance().get(tableJointor.getRight().getEntity());
            if (modelHelper == null)
            {
                throw new QueryBuildException(" Illegal right-entity name : " + tableJointor.getRight().getEntity());
            }
            
            tableJointor.getRight().setTable(modelHelper.table());
            tableJointor.getRight().setColumn(modelHelper.getColumn(tableJointor.getRight().getField()));
        }
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
     * @return orderByFieldList
     */
    @JsonIgnore
    public List<SortField> getOrderFieldList()
    {
        return orderFieldList;
    }
    
    /**
     * <p>Get Method   :   where Where</p>
     * @return where
     */
    public Where getWhere()
    {
        return where;
    }

    /**
     * <p>Set Method   :   where Where</p>
     * @param where
     */
    public void setWhere(Where where)
    {
        where.initialize(null);
        this.where = where;
    }
    
    /**
     * <p>Get Method   :   processorId String</p>
     * @return processorId
     */
    public String getProcessorId()
    {
        return processorId;
    }

    /**
     * <p>Set Method   :   processorId String</p>
     * @param processorId
     */
    public void setProcessorId(String processorId)
    {
        this.processorId = processorId;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年6月4日 下午3:27:31</p>
     * <p>Title: toString</p>
     * @return 
     * @see java.lang.Object#toString()
     */ 
    public String toString()
    {
        return "JointQuery [processorId=" + processorId + ", page=" + page + ", offset=" + offset + ", size=" + size + ", tableJointorList=" + tableJointorList + ", selectColumnList="
                + selectColumnList + ", where=" + where + ", orderFieldList=" + orderFieldList + "]";
    }
}
