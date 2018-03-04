/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DatabaseType.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年3月3日 上午10:38:01
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.util.Locale;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : DatabaseType</p>
 * @author lilinhai 2018年3月3日 上午10:38:01
 * @version 1.0
 */
public abstract class DatabaseType
{

    /**
     * 是否是MySQL
     */
    private static boolean isMySQL;
    
    /**
     * 是否是Oracle
     */
    private static boolean isOracle;
    
    /**
     * 是否是sql server
     */
    private static boolean isSqlServer;
    
    /**
     * 
     * <p>Title : initialize lilinhai 2018年3月3日 上午10:43:32</p>
     * @param databaseProductName 
     * void
     */
    public static void initialize(String databaseProductName)
    {
        databaseProductName = databaseProductName.toLowerCase(Locale.ENGLISH);
        if (databaseProductName.contains("mysql"))
        {
            isMySQL = true;
        }
        else if (databaseProductName.contains("oracle"))
        {
            isOracle = true;
        }
        else if (databaseProductName.contains("sqlserver"))
        {
            isSqlServer = true;
        }
        else
        {
            isMySQL = true;
        }
    }

    /**
     * <p>Get Method   :   isMySQL boolean</p>
     * @return isMySQL
     */
    public static boolean isMySQL()
    {
        return isMySQL;
    }

    /**
     * <p>Get Method   :   isOracle boolean</p>
     * @return isOracle
     */
    public static boolean isOracle()
    {
        return isOracle;
    }

    /**
     * <p>Get Method   :   isSqlServer boolean</p>
     * @return isSqlServer
     */
    public static boolean isSqlServer()
    {
        return isSqlServer;
    }
}
