/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : MySQLPageQueryGenerator.java</p>
 * <p>Package     : codegenerator.sqlmapgenerator</p>
 * @Creator lilinhai 2018年2月7日 下午3:17:36
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.sqlmapgenerator;

import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.dom.xml.Attribute;
import pers.linhai.nature.j2ee.generator.core.api.dom.xml.Document;
import pers.linhai.nature.j2ee.generator.core.api.dom.xml.TextElement;
import pers.linhai.nature.j2ee.generator.core.api.dom.xml.XmlElement;
import pers.linhai.nature.j2ee.generator.exception.GeneratorException;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : MySQLPageQueryGenerator</p>
 * @author lilinhai 2018年2月7日 下午3:17:36
 * @version 1.0
 */
public class MySQLPageQueryGenerator extends DatabasePageQueryGenerator
{

    /**
     * <p>Title        : MySQLPageQueryGenerator lilinhai 2018年2月7日 下午3:23:38</p>
     * @param targetPackae 
     */ 
    public MySQLPageQueryGenerator(String targetPackae)
    {
        super(targetPackae);
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月7日 下午3:17:36</p>
     * <p>Title: generator</p>
     * <p>Description: TODO</p>
     * @param document
     * @param introspectedTable 
     * @see pers.linhai.nature.j2ee.generator.sqlmapgenerator.DatabasePageQueryGenerator#generator(pers.linhai.nature.j2ee.generator.core.api.dom.xml.Document, pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable)
     */
    @Override
    public void generator(Document document, IntrospectedTable introspectedTable)
    {
        XmlElement rootElement = document.getRootElement();

        StringBuilder sb = new StringBuilder();

        // 添加getList
        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", "query"));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        //select.addAttribute(new Attribute("parameterType", targetPackae + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"));

        select.addElement(new TextElement(" select "));
        select.addElement(new TextElement(""));
        select.addElement(new TextElement("<!-- 添加需要返回的字段信息 -->"));
        XmlElement chooseEl = new XmlElement("choose");
        XmlElement whenEl = new XmlElement("when");
        whenEl.addAttribute(new Attribute("test", "returnFieldList != null"));
        
        XmlElement returnFieldForeachEl = new XmlElement("foreach");
        returnFieldForeachEl.addAttribute(new Attribute("collection", "returnFieldList"));
        returnFieldForeachEl.addAttribute(new Attribute("index", "index"));
        returnFieldForeachEl.addAttribute(new Attribute("item", "returnField"));
        returnFieldForeachEl.addAttribute(new Attribute("open", " "));
        returnFieldForeachEl.addAttribute(new Attribute("separator", ", "));
        returnFieldForeachEl.addAttribute(new Attribute("close", " "));
        returnFieldForeachEl.addElement(new TextElement("${returnField}"));
        //whenEl.addElement(returnFieldForeachEl);
        XmlElement otherwiseEl = new XmlElement("otherwise");
        XmlElement columnInclude = new XmlElement("include");
        columnInclude.addAttribute(new Attribute("refid", "Base_Column_List"));
        otherwiseEl.addElement(columnInclude);
        chooseEl.addElement(whenEl);
        chooseEl.addElement(otherwiseEl);
        select.addElement(returnFieldForeachEl);
        
        select.addElement(new TextElement(" from " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " where 1 = 1"));

        select.addElement(new TextElement(""));
        select.addElement(new TextElement("<!-- 添加动态自定义查询条件 -->"));
        XmlElement conditionChooseEl = new XmlElement("choose");
        XmlElement conditionWhenEl = new XmlElement("when");
        conditionWhenEl.addAttribute(new Attribute("test", "where.expressionSegmentList != null"));
        XmlElement expressionSegmentForeachEl = new XmlElement("foreach");
        expressionSegmentForeachEl.addAttribute(new Attribute("collection", "where.expressionSegmentList"));
        expressionSegmentForeachEl.addAttribute(new Attribute("index", "index"));
        expressionSegmentForeachEl.addAttribute(new Attribute("item", "expressionSegment"));
        expressionSegmentForeachEl.addAttribute(new Attribute("open", " and ( "));
        expressionSegmentForeachEl.addAttribute(new Attribute("separator", " "));
        expressionSegmentForeachEl.addAttribute(new Attribute("close", " ) "));
        conditionWhenEl.addElement(expressionSegmentForeachEl);
        XmlElement expressionSegmentItemChooseEl = new XmlElement("choose");
        XmlElement expressionSegmentItemWhenEl = new XmlElement("when");
        expressionSegmentItemWhenEl.addAttribute(new Attribute("test", "expressionSegment.getClass().getSimpleName() == 'Condition'.toString()"));
        expressionSegmentItemWhenEl.addElement(new TextElement("${expressionSegment.fieldName} ${expressionSegment.operator} #{expressionSegment.value, jdbcType=${expressionSegment.jdbcType}}"));
        expressionSegmentItemChooseEl.addElement(expressionSegmentItemWhenEl);
        XmlElement expressionSegmentItemOtherwiseEl = new XmlElement("otherwise");
        expressionSegmentItemOtherwiseEl.addElement(new TextElement("${expressionSegment}"));
        expressionSegmentItemChooseEl.addElement(expressionSegmentItemOtherwiseEl);
        expressionSegmentForeachEl.addElement(expressionSegmentItemChooseEl);
        
        XmlElement conditionOtherwiseForeachEl = new XmlElement("foreach");
        conditionOtherwiseForeachEl.addAttribute(new Attribute("collection", "where.conditionList"));
        conditionOtherwiseForeachEl.addAttribute(new Attribute("index", "index"));
        conditionOtherwiseForeachEl.addAttribute(new Attribute("item", "condition"));
        conditionOtherwiseForeachEl.addAttribute(new Attribute("open", " and "));
        conditionOtherwiseForeachEl.addAttribute(new Attribute("separator", " and "));
        conditionOtherwiseForeachEl.addAttribute(new Attribute("close", " "));
        conditionOtherwiseForeachEl.addElement(new TextElement("${condition.fieldName} ${condition.operator} #{condition.value, jdbcType=${condition.jdbcType}}"));
        
        XmlElement conditionOtherwiseEl = new XmlElement("otherwise");
        XmlElement conditionOtherwiseIfEl = new XmlElement("if");
        conditionOtherwiseIfEl.addAttribute(new Attribute("test", "where.conditionList != null"));
        conditionOtherwiseIfEl.addElement(conditionOtherwiseForeachEl);
        conditionOtherwiseEl.addElement(conditionOtherwiseIfEl);
        conditionChooseEl.addElement(conditionWhenEl);
        conditionChooseEl.addElement(conditionOtherwiseEl);
        select.addElement(conditionChooseEl);
        
        XmlElement orderIfEl = new XmlElement("if");
        orderIfEl.addAttribute(new Attribute("test", "sortFieldList != null"));
        XmlElement orderForeachEl = new XmlElement("foreach");
        orderForeachEl.addAttribute(new Attribute("collection", "sortFieldList"));
        orderForeachEl.addAttribute(new Attribute("index", "index"));
        orderForeachEl.addAttribute(new Attribute("item", "orderField"));
        orderForeachEl.addAttribute(new Attribute("open", " order by "));
        orderForeachEl.addAttribute(new Attribute("separator", ", "));
        orderForeachEl.addAttribute(new Attribute("close", " "));
        orderForeachEl.addElement(new TextElement("#{orderField.fieldName} ${orderField.direction}"));
        orderIfEl.addElement(orderForeachEl);
        select.addElement(new TextElement(""));
        select.addElement(new TextElement("<!-- 添加排序字段信息 -->"));
        select.addElement(orderIfEl);
        
        
        select.addElement(new TextElement(""));
        select.addElement(new TextElement("<!-- 分页查询参数组件 -->"));
        
        // 绑定分页查询参数
        XmlElement pageEl = new XmlElement("if"); //$NON-NLS-1$
        sb.setLength(0);
        sb.append("offset").append(" != null and ").append("size != null"); //$NON-NLS-1$
        pageEl.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
        
        sb.setLength(0);
        sb.append(" limit #{offset}, #{size}");
        pageEl.addElement(new TextElement(sb.toString()));
        select.addElement(pageEl);
        rootElement.addElement(new TextElement(""));
        rootElement.addElement(new TextElement("<!-- 组合查询函数，支持分页功能 -->"));
        rootElement.addElement(select);
        
        // 添加范型继承关系BaseService
        if (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().isEmpty())
        {
            throw new GeneratorException(introspectedTable.getBaseRecordType() + " 该表没有设置主键，请设置！");
        }
        
        String keyFieldName = introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName();
        
        // 添加getList
        XmlElement selectCount = new XmlElement("select");
        selectCount.addAttribute(new Attribute("id", "queryCount"));
        selectCount.addAttribute(new Attribute("resultType", Long.class.getName()));
        //selectCount.addAttribute(new Attribute("parameterType", targetPackae + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"));
        selectCount.addElement(new TextElement(" select count(" + keyFieldName + ") from " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " where 1 = 1"));
        selectCount.addElement(new TextElement(""));
        selectCount.addElement(new TextElement("<!-- 添加动态自定义查询条件 -->"));
        selectCount.addElement(conditionChooseEl);
        rootElement.addElement(selectCount);
    }
}
