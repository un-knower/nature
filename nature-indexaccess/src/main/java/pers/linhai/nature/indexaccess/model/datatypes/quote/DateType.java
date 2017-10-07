/*
 * 文 件 名: pers.linhai.esdao.datatype.StringType.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月5日 下午2:24:59
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.indexaccess.model.datatypes.quote;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.List;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.indexaccess.annotation.datatypes.DateField;
import pers.linhai.nature.indexaccess.annotation.datatypes.TextField;
import pers.linhai.nature.indexaccess.exception.DateFieldValueInvalidException;
import pers.linhai.nature.indexaccess.model.datatypes.quote.DateType.Date;
import pers.linhai.nature.indexaccess.model.datatypes.quote.ObjectType.ObjectField;
import pers.linhai.nature.indexaccess.model.enumer.Index;
import pers.linhai.nature.indexaccess.utils.StringUtils;

/**
 * @Description: <一句话功能简述>
 * <pre>
 * Date datatype
 * 
 * JSON doesn’t have a date datatype, so dates in Elasticsearch can either be:
 *     strings containing formatted dates, e.g. "2015-01-01" or "2015/01/01 12:10:30".
 *     a long number representing milliseconds-since-the-epoch.
 *     an integer representing seconds-since-the-epoch. 
 * Internally, dates are converted to UTC (if the time-zone is specified) and stored as a long number representing milliseconds-since-the-epoch.
 * Date formats can be customised, but if no format is specified then it uses the default:
 * "strict_date_optional_time||epoch_millis"
 * This means that it will accept dates with optional timestamps, which conform to the formats supported by strict_date_optional_time or milliseconds-since-the-epoch.
 * </pre>
 * @author: shinelon
 * @date: 2017年3月5日 下午2:24:59
 *
 * @ClassName: 	[StringType]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public class DateType extends ObjectType<Date>
{
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
    
    /** 
     * <默认构造函数>
     *
     * @param clazz
     * @param annoList
     */
    public DateType(Class<Date> clazz, List<Annotation> annoList)
    {
        super(clazz, annoList);
        
        DateField dateField = getAnnotation(annoList, DateField.class);
        if(dateField != null && !StringUtils.isEmpty(dateField.format()))
        {
            dateFormat = new SimpleDateFormat(dateField.format());
        }
    }

    /**
     * 
     *
     * @param xContentBuilder
     * @param pojo
     * @throws IOException
     */
    @Override
    public <T> void setJsonFieldValue(XContentBuilder xContentBuilder, T pojo) throws IOException
    {
        Date date = (Date) invoke(pojo);
        if (date == null) return;
        
        if(date.getFormatTime() == null)
        {
            if (date.getMilliseconds() <= 0)
            {
                throw new DateFieldValueInvalidException("The value of Date Field is invalid, it can't be null or empty.");
            }
            else
            {
                date.setFormatTime(dateFormat.format(new java.util.Date(date.getMilliseconds())));
            }
        }
        
/*        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getMilliseconds());
        calendar.set(Calendar.HOUR_OF_DAY, (calendar.get(Calendar.HOUR_OF_DAY) / hoursSpan) * hoursSpan);
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 0);  
        calendar.set(Calendar.MILLISECOND, 0); 
        
        //设置日志发生时间的整点毫秒时间戳
        date.setWholePointTime(calendar.getTimeInMillis());*/
        setJsonFieldValue0(xContentBuilder, date);
    }

    /**
     * 日期对象
     * 
     * @author  shinelon
     * @version  V100R001C00
     */
    public static final class Date extends ObjectField
    {
        
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         * 毫秒
         */
        private long milliseconds;
        
        /**
         * 格式化的日期字符串
         */
        @TextField(index = Index.NO)
        private String formatTime;
        
        /** 
         * <默认构造函数>
         *
         * @param milliseconds
         */
        public Date(long milliseconds)
        {
            this.milliseconds = milliseconds;
        }
        
        /** 
         * <默认构造函数>
         *
         */
        public Date()
        {
        }
        
        /**
         * 返回 millisecond
         *
         * @return millisecond
         */
        public long getMilliseconds()
        {
            return milliseconds;
        }

        /**
         * 对millisecond进行赋值
         *
         * @param milliseconds
         */
        public void setMilliseconds(long milliseconds)
        {
            this.milliseconds = milliseconds;
        }

        /**
         * 返回 formatTime
         *
         * @return formatTime
         */
        public String getFormatTime()
        {
            return formatTime;
        }

        /**
         * 对time进行赋值
         *
         * @param formatTime
         */
        public void setFormatTime(String formatTime)
        {
            this.formatTime = formatTime;
        }

        /**
         * @return
         */
        public String toString()
        {
            return "Date [milliseconds=" + milliseconds + ", formatTime=" + formatTime + "]";
        }
    }
    
}
