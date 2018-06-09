/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BrowseUtisl.java</p>
 * <p>Package     : pers.linhai.nature.utils</p>
 * @Creator lilinhai 2018年6月8日 下午11:00:02
 * @Version  V1.0  
 */

package pers.linhai.nature.utils;

import java.awt.Desktop;
import java.net.URI;

/**
 * 网址浏览工具
 * <p>ClassName      : BrowseUtisl</p>
 * @author lilinhai 2018年6月8日 下午11:00:02
 * @version 1.0
 */
public abstract class BrowseUtils
{
    
    /**
     * 让操作系统 打开指定url网址
     * <p>Title         : browse lilinhai 2018年6月8日 下午11:00:52</p>
     * @param url 
     * void
     */
    public static void browse(String url)
    {
        //判断当前系统是否支持Java AWT Desktop扩展
        if (Desktop.isDesktopSupported())
        {
            try
            {
                //创建一个URI实例,注意不是URL
                java.net.URI uri = URI.create(url);
                
                //获取当前系统桌面扩展
                Desktop dp = Desktop.getDesktop();
                
                //判断系统桌面是否支持要执行的功能
                if (dp.isSupported(Desktop.Action.BROWSE))
                {
                    //获取系统默认浏览器打开链接
                    dp.browse(uri);
                }
            }
            catch (Throwable e)
            {
                e.printStackTrace();
                ProcessUtils.run("rundll32 url.dll,FileProtocolHandler " + url);
            }
        }
        else
        {
            ProcessUtils.run("rundll32 url.dll,FileProtocolHandler " + url);
        }
    }
}
