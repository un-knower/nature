/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : NumberType.java</p>
 * <p>Package     : com.meme.crm.model.core.datatype</p>
 * @Creator lilinhai 2018年2月15日 下午6:58:54
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model.datatype;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import pers.linhai.nature.j2ee.core.exception.DataTypeException;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : NumberType</p>
 * @author lilinhai 2018年2月15日 下午6:58:54
 * @version 1.0
 */
public class DateType extends DataType
{
    
    private static final Map<Pattern, SimpleDateFormat> MAP = new HashMap<Pattern, SimpleDateFormat>();
    static
    {
        MAP.put(Pattern.compile("^[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}$"), new SimpleDateFormat("yyyyMMddHHmmss"));
        MAP.put(Pattern.compile("^[0-9]{4}[0-9]{2}[0-9]{2}$"), new SimpleDateFormat("yyyyMMdd"));
        MAP.put(Pattern.compile("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        MAP.put(Pattern.compile("^[0-9]{1,2}-[0-9]{1,2}-[0-9]{4} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$"), new SimpleDateFormat("MM-dd-yyyy HH:mm:ss"));
        MAP.put(Pattern.compile("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$"), new SimpleDateFormat("yyyy-MM-dd"));
        MAP.put(Pattern.compile("^[0-9]{1,2}-[0-9]{1,2}-[0-9]{4}$"), new SimpleDateFormat("MM-dd-yyyy"));
        MAP.put(Pattern.compile("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}$"), new SimpleDateFormat("yyyy-MM-dd HH:mm"));
        MAP.put(Pattern.compile("^[0-9]{1,2}-[0-9]{1,2}-[0-9]{4} [0-9]{1,2}:[0-9]{1,2}$"), new SimpleDateFormat("MM-dd-yyyy HH:mm"));
        MAP.put(Pattern.compile("^[0-9]{4}$"), new SimpleDateFormat("yyyy"));
        MAP.put(Pattern.compile("^[0-9]{2}-[0-9]{1,2}-[0-9]{1,2}$"), new SimpleDateFormat("yy-MM-dd"));
        MAP.put(Pattern.compile("^[0-9]{4}\\.[0-9]{1,2}\\.[0-9]{1,2}$"), new SimpleDateFormat("yyyy.MM.dd"));
        MAP.put(Pattern.compile("^[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{4}$"), new SimpleDateFormat("MM.dd.yyyy"));
        MAP.put(Pattern.compile("^[0-9]{4}/[0-9]{1,2}/[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$"), new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));
        MAP.put(Pattern.compile("^[0-9]{1,2}/[0-9]{1,2}/[0-9]{4} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$"), new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"));
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月15日 下午6:59:08</p>
     * <p>Title: parse</p>
     * @param value
     * @return 
     * @see com.meme.crm.model.core.datatype.DataType#parse(java.lang.String)
     */
    public Date parse(Object value)
    {
        try
        {
            if (value instanceof Date)
            {
                return (Date) value;
            }
            else if (value instanceof Number)
            {
                return new Date(((Number) value).longValue());
            }
            else if (value instanceof String)
            {
                for (Entry<Pattern, SimpleDateFormat> e : MAP.entrySet())
                {
                    if (e.getKey().matcher(value.toString()).matches())
                    {
                        SimpleDateFormat sdf = e.getValue();
                        synchronized (sdf)
                        {
                            return sdf.parse(value.toString());
                        }
                    }
                }
                return new Date(Long.parseLong(value.toString()));
            }
            throw new DataTypeException("Date parse error: " + value);
        }
        catch (Throwable e)
        {
            if (e instanceof DataTypeException)
            {
                throw (DataTypeException) e;
            }
            throw new DataTypeException("The date pared fail:" + value, e);
        }
    }
}
