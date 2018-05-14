/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : ExcelParser.java</p> <p>Package
 * : pers.linhai.nature.j2ee.core.common</p>
 * @Creator lilinhai 2018年5月2日 上午12:16:40
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : ExcelParser</p>
 * @author lilinhai 2018年5月2日 上午12:16:40
 * @version 1.0
 */
public class ExcelParser
{
    
    private static final List<Constructor< ? extends Workbook>> WORK_BOOK_CONSTRUCTOR_LIST = new ArrayList<Constructor< ? extends Workbook>>(2);
    static
    {
        try
        {
            WORK_BOOK_CONSTRUCTOR_LIST.add(XSSFWorkbook.class.getConstructor(InputStream.class));
            WORK_BOOK_CONSTRUCTOR_LIST.add(HSSFWorkbook.class.getConstructor(InputStream.class));
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 日志记录器
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private Workbook workbook;
    
    public ExcelParser(InputStream source)
    {
        for (Constructor< ? extends Workbook> constructor : WORK_BOOK_CONSTRUCTOR_LIST)
        {
            try
            {
                workbook = constructor.newInstance(new FileInputStream("C:\\Users\\lilinhai\\Desktop\\test\\test.xls"));
                break;
            }
            catch (Throwable e)
            {
                logger.error("ExcelParser(InputStream source) occor an error.", e);
            }
        }
    }
    
    
    
    public static void main(String[] args) throws Exception
    {
        Workbook hssWB = null;
        for (Constructor<? extends Workbook> constructor : WORK_BOOK_CONSTRUCTOR_LIST)
        {
            try
            {
                hssWB = constructor.newInstance(new FileInputStream("C:\\Users\\lilinhai\\Desktop\\test\\test.xls"));
                break;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        hssWB.close();
       
        
        
        
        
        
        
        
        
    }
}
