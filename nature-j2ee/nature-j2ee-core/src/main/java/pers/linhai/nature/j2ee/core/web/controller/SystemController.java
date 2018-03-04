/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : SystemController.java</p>
 * <p>Package     : com.leloven.wanka.gw.controller</p>
 * @Creator lilinhai 2018年1月18日 下午3:40:49
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pers.linhai.nature.j2ee.core.web.controller.SystemInfo.MemoryInfo;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : SystemController</p>
 * @author lilinhai 2018年1月18日 下午3:40:49
 * @version 1.0
 */
@RestController
@RequestMapping("/system")
public class SystemController
{

    @RequestMapping(value = "/getSystemInfo", method = RequestMethod.GET)
    public SystemInfo getSystemInfo()
    {
        Runtime run = Runtime.getRuntime();
        
        SystemInfo systemInfo = new SystemInfo();
        MemoryInfo memoryInfo = new MemoryInfo();
        memoryInfo.setTotalMemory(run.totalMemory());
        memoryInfo.setFreeMemory(run.freeMemory());
        memoryInfo.setUsedMemory(memoryInfo.getTotalMemory() - memoryInfo.getFreeMemory());
        systemInfo.setMemoryInfo(memoryInfo);
        return systemInfo;
    }
}
