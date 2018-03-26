/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : SystemInfo.java</p> <p>Package
 * : com.leloven.wanka.gw.model</p>
 * @Creator lilinhai 2018年1月18日 下午3:41:42
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.web.model;

/**
 * 系统占用的资源信息
 * <p>ClassName      : SystemInfo</p>
 * @author lilinhai 2018年1月18日 下午3:41:42
 * @version 1.0
 */
public class SystemInfo
{

    /**
     * 内存资源占用信息
     */
    private MemoryInfo memoryInfo;
    
    
    /**
     * <p>Get Method   :   memoryInfo MemoryInfo</p>
     * @return memoryInfo
     */
    public MemoryInfo getMemoryInfo()
    {
        return memoryInfo;
    }

    /**
     * <p>Set Method   :   memoryInfo MemoryInfo</p>
     * @param memoryInfo
     */
    public void setMemoryInfo(MemoryInfo memoryInfo)
    {
        this.memoryInfo = memoryInfo;
    }

    /**
     * 内存资源占用信息
     * <p>ClassName      : MemoryInfo</p>
     * @author lilinhai 2018年1月18日 下午3:45:25
     * @version 1.0
     */
    public static class MemoryInfo
    {

        /**
         * 总内存大小
         */
        private long totalMemory;
        
        /**
         * 剩余内存
         */
        private long freeMemory;
        
        /**
         * 已经使用的内存
         */
        private long usedMemory;

        /**
         * <p>Get Method   :   totalMemory long</p>
         * @return totalMemory
         */
        public long getTotalMemory()
        {
            return totalMemory;
        }

        /**
         * <p>Set Method   :   totalMemory long</p>
         * @param totalMemory
         */
        public void setTotalMemory(long totalMemory)
        {
            this.totalMemory = totalMemory;
        }

        /**
         * <p>Get Method   :   freeMemory long</p>
         * @return freeMemory
         */
        public long getFreeMemory()
        {
            return freeMemory;
        }

        /**
         * <p>Set Method   :   freeMemory long</p>
         * @param freeMemory
         */
        public void setFreeMemory(long freeMemory)
        {
            this.freeMemory = freeMemory;
        }

        /**
         * <p>Get Method   :   usedMemory long</p>
         * @return usedMemory
         */
        public long getUsedMemory()
        {
            return usedMemory;
        }

        /**
         * <p>Set Method   :   usedMemory long</p>
         * @param usedMemory
         */
        public void setUsedMemory(long usedMemory)
        {
            this.usedMemory = usedMemory;
        }
    }
}
