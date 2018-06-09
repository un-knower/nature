/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : ProcessUtils.java</p>
 * <p>Package     : pers.linhai.nature.utils</p>
 * @Creator lilinhai 2018年6月8日 下午10:22:58
 * @Version  V1.0  
 */ 

package pers.linhai.nature.utils;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import pers.linhai.nature.constant.Charsets;
import pers.linhai.nature.exception.ProcessException;

/**
 * java调用应用程序工具类
 * <p>ClassName      : ProcessUtils</p>
 * @author lilinhai 2018年6月8日 下午10:22:58
 * @version 1.0
 */
public class ProcessUtils
{
    /**
     * 运行一个cmd命令
     * <p>Title         : run lilinhai 2018年6月8日 下午10:48:41</p>
     * @param cmd 
     * void
     */
    public static ProcessResult run(String cmd)
    {
        try
        {
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream resultStream = process.getInputStream();
            String result = null;
            if (resultStream != null)
            {
                result = IOUtils.inputStreamToString(resultStream);
            }
            String errorMessage = null;
            InputStream errorStream = process.getErrorStream();
            if (errorStream != null)
            {
                errorMessage = IOUtils.inputStreamToString(process.getErrorStream());
            }
            
            return new ProcessResult(result, errorMessage);
        }
        catch (Throwable e)
        {
            throw new ProcessException("ProcessUtils.run occor an exception", e);
        }
    }
    
    /**
     * 运行一个cmd命令，并传入参数
     * <p>Title         : run lilinhai 2018年6月8日 下午10:49:56</p>
     * @param cmd
     * @param outPutMessage 
     * void
     */
    public static ProcessResult run(String cmd, String outPutMessage)
    {
        try
        {
            Process process = Runtime.getRuntime().exec(cmd);
            
            OutputStream out = process.getOutputStream();
            if (out != null && outPutMessage != null)
            {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, Charsets.UTF_8));
                bw.write(outPutMessage);
                bw.close();
            }
            
            InputStream resultStream = process.getInputStream();
            String result = null;
            if (resultStream != null)
            {
                result = IOUtils.inputStreamToString(resultStream);
            }
            String errorMessage = null;
            InputStream errorStream = process.getErrorStream();
            if (errorStream != null)
            {
                errorMessage = IOUtils.inputStreamToString(process.getErrorStream());
            }
            
            return new ProcessResult(result, errorMessage);
        }
        catch (Throwable e)
        {
            throw new ProcessException("ProcessUtils.run occor an exception", e);
        }
    }
    
    /**
     * cmd命令执行结果
     * <p>ClassName      : ProcessResult</p>
     * @author lilinhai 2018年6月8日 下午10:47:57
     * @version 1.0
     */
    public static class ProcessResult
    {
        /**
         * 正常执行结果
         */
        private String result;
        
        /**
         * 执行错误的信息
         */
        private String error;
        
        /**
         * <p>Title        : ProcessResult lilinhai 2018年6月8日 下午10:50:10</p>
         * @param result
         * @param error 
         */ 
        public ProcessResult(String result, String error)
        {
            super();
            this.result = result;
            this.error = error;
        }

        /**
         * <p>Get Method   :   result String</p>
         * @return result
         */
        public String getResult()
        {
            return result;
        }

        /**
         * <p>Set Method   :   result String</p>
         * @param result
         */
        public void setResult(String result)
        {
            this.result = result;
        }

        /**
         * <p>Get Method   :   error String</p>
         * @return error
         */
        public String getError()
        {
            return error;
        }

        /**
         * <p>Set Method   :   error String</p>
         * @param error
         */
        public void setError(String error)
        {
            this.error = error;
        }

        /** 
         * <p>Overriding Method: lilinhai 2018年6月8日 下午10:48:17</p>
         * <p>Title: toString</p>
         * @return 
         * @see java.lang.Object#toString()
         */ 
        public String toString()
        {
            return "ProcessResult [result=" + result + ", error=" + error + "]";
        }
    }
}
