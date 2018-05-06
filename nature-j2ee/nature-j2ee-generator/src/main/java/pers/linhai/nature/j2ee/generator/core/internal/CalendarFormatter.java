/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : CalendarFormatter.java</p>
 * <p>Package : pers.linhai.nature.j2ee.generator.core.internal</p>
 * @Creator lilinhai 2018年5月6日 下午9:45:21
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.generator.core.internal;


import java.util.Calendar;
import java.util.TimeZone;


/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : CalendarFormatter</p>
 * @author lilinhai 2018年5月6日 下午9:45:21
 * @version 1.0
 */
public class CalendarFormatter
{

    public static String doFormat(String format, Calendar cal)
        throws IllegalArgumentException
    {
        int fidx = 0;
        int flen = format.length();
        StringBuilder buf = new StringBuilder();

        while (fidx < flen)
        {
            char fch = format.charAt(fidx++ );

            if (fch != '%')
            { // not a meta character
                buf.append(fch);
                continue;
            }

            // seen meta character. we don't do error check against the format
            switch (format.charAt(fidx++ ))
            {
                case 'Y': // year
                    formatYear(cal, buf);
                    break;

                case 'M': // month
                    formatMonth(cal, buf);
                    break;

                case 'D': // days
                    formatDays(cal, buf);
                    break;

                case 'h': // hours
                    formatHours(cal, buf);
                    break;

                case 'm': // minutes
                    formatMinutes(cal, buf);
                    break;

                case 's': // parse seconds.
                    formatSeconds(cal, buf);
                    break;

                case 'z': // time zone
                    formatTimeZone(cal, buf);
                    break;

                default:
                    // illegal meta character. impossible.
                    throw new InternalError();
            }
        }

        return buf.toString();
    }

    private static void formatYear(Calendar cal, StringBuilder buf)
    {
        int year = cal.get(Calendar.YEAR);

        String s;
        if (year <= 0) // negative value
        {
            s = Integer.toString(1 - year);
        }
        else // positive value
        {
            s = Integer.toString(year);
        }

        while (s.length() < 4)
        {
            s = '0' + s;
        }
        if (year <= 0)
        {
            s = '-' + s;
        }

        buf.append(s);
    }

    private static void formatMonth(Calendar cal, StringBuilder buf)
    {
        formatTwoDigits(cal.get(Calendar.MONTH) + 1, buf);
    }

    private static void formatDays(Calendar cal, StringBuilder buf)
    {
        formatTwoDigits(cal.get(Calendar.DAY_OF_MONTH), buf);
    }

    private static void formatHours(Calendar cal, StringBuilder buf)
    {
        formatTwoDigits(cal.get(Calendar.HOUR_OF_DAY), buf);
    }

    private static void formatMinutes(Calendar cal, StringBuilder buf)
    {
        formatTwoDigits(cal.get(Calendar.MINUTE), buf);
    }

    private static void formatSeconds(Calendar cal, StringBuilder buf)
    {
        formatTwoDigits(cal.get(Calendar.SECOND), buf);
        if (cal.isSet(Calendar.MILLISECOND))
        { // milliseconds
            int n = cal.get(Calendar.MILLISECOND);
            if (n != 0)
            {
                String ms = Integer.toString(n);
                while (ms.length() < 3)
                {
                    ms = '0' + ms; // left 0 paddings.
                }
                buf.append('.');
                buf.append(ms);
            }
        }
    }

    /** formats time zone specifier. */
    private static void formatTimeZone(Calendar cal, StringBuilder buf)
    {
        TimeZone tz = cal.getTimeZone();

        if (tz == null)
        {
            return;
        }

        // otherwise print out normally.
        int offset = tz.getOffset(cal.getTime().getTime());

        if (offset == 0)
        {
            buf.append('Z');
            return;
        }

        if (offset >= 0)
        {
            buf.append('+');
        }
        else
        {
            buf.append('-');
            offset *= -1;
        }

        offset /= 60 * 1000; // offset is in milli-seconds

        formatTwoDigits(offset / 60, buf);
        buf.append(':');
        formatTwoDigits(offset % 60, buf);
    }

    /** formats Integer into two-character-wide string. */
    private static void formatTwoDigits(int n, StringBuilder buf)
    {
        // n is always non-negative.
        if (n < 10)
        {
            buf.append('0');
        }
        buf.append(n);
    }

}
