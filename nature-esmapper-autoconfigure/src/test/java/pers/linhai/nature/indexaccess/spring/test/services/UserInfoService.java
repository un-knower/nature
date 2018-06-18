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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.linhai.nature.esmapper.interfaces.TypeMapper;
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
    @Autowired
    private TypeMapper<UserInfo> userInfoAccessor;
    
    /**
     * 返回 userIndexAccessor
     *
     * @return userIndexAccessor
     */
    public TypeMapper<UserInfo> getUserInfoAccessor()
    {
        return userInfoAccessor;
    }
    
}
