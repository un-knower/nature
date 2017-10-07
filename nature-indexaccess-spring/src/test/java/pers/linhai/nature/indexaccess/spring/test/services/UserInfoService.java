/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexdao.springintegration.service.UserIndexService.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月2日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.spring.test.services;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pers.linhai.nature.indexaccess.interfaces.TypeAccessor;
import pers.linhai.nature.indexaccess.spring.test.type.UserInfo;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
@Service
public class UserInfoService
{
    @Resource(name="user_info_accessor")
    private TypeAccessor<UserInfo> userIndexAccessor;
    
    /**
     * 返回 userIndexAccessor
     *
     * @return userIndexAccessor
     */
    public TypeAccessor<UserInfo> getUserIndexAccessor()
    {
        return userIndexAccessor;
    }
    
}
