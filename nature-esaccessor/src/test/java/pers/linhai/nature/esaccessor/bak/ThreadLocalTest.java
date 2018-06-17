/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.ThreadLocalTest.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月27日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.bak;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class ThreadLocalTest
{
    
    private static ThreadLocal<SimpleDateFormat> sdfThreadLocal = new ThreadLocal<SimpleDateFormat>(){

        /**
         * 
         *
         * @return
         */
        @Override
        protected SimpleDateFormat initialValue()
        {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        
    };

    
    public static void main(String[] args)
    {
        for (int i = 0; i < 1000; i++)
        {
            new TimeThead(sdfThreadLocal).start();
        }
    }
    
    
    public static class TimeThead extends Thread
    {

        ThreadLocal<SimpleDateFormat> sdfThreadLocal;
        
        private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        
        /** 
         * <默认构造函数>
         *
         * @param sdfThreadLocal
         */
        public TimeThead(ThreadLocal<SimpleDateFormat> sdfThreadLocal)
        {
            this.sdfThreadLocal = sdfThreadLocal;
        }

        /**
         * 
         *
         */
        public void run()
        {
            try
            {
                System.out.println(Thread.currentThread().getName() + "   " + sdf.format(new Date()));
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
        }
        
    }
}
