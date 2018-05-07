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

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : ExcelParser</p>
 * @author lilinhai 2018年5月2日 上午12:16:40
 * @version 1.0
 */
public class ExcelParser
{
    
    public static void main(String[] args) throws Exception
    {
        List<Constructor< ? extends Workbook>> workbookConstructorList = new ArrayList<Constructor< ? extends Workbook>>(2);
        workbookConstructorList.add(XSSFWorkbook.class.getConstructor(InputStream.class));
        workbookConstructorList.add(HSSFWorkbook.class.getConstructor(InputStream.class));
        Workbook hssWB = null;
        for (Constructor< ? extends Workbook> constructor : workbookConstructorList)
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
