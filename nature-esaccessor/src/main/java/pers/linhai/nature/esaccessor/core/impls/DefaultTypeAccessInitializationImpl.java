/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.core.impls.DefaultTypeAccessInitializationImpl.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  lilinhai
 * 修改时间:  2017年10月6日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.core.impls;

import pers.linhai.nature.esaccessor.core.processor.IndicesAdminClientProcessor;
import pers.linhai.nature.esaccessor.core.processor.TransportClientProcessor;
import pers.linhai.nature.esaccessor.interfaces.TypeAccessorInitialization;
import pers.linhai.nature.esaccessor.model.type.MappingConfiguration;
import pers.linhai.nature.esaccessor.model.type.Type;

/**
 * @author  lilinhai
 * @version  V100R001C00
 */
public class DefaultTypeAccessInitializationImpl implements TypeAccessorInitialization
{
    
    
    
    /** 
     * <p>Overriding Method: lilinhai 2018年6月18日 上午12:42:54</p>
     * <p>Title: initializeConfiguration</p>
     * <p>Description: TODO</p> 
     * @see pers.linhai.nature.esaccessor.interfaces.TypeAccessorInitialization#initializeConfiguration()
     */ 
    public void initializeConfiguration()
    {
        
    }

    /**
     *
     * @param writingIndexName
     * @param indices
     * @param mappingConfigurationlist
     */
    @Override
    public void initialize(IndicesAdminClientProcessor indicesAdminClientProcessor)
    {
        // 初始化该索引库下面的TransportClientProcessor,一个mapping对应一个TransportClientProcessor对象
        for (MappingConfiguration<? extends Type> tec : indicesAdminClientProcessor.getIndex().getMappingConfigurationlist())
        {
            TransportClientProcessor transportClientProcessor = TransportClientProcessor
                    .newInstance(indicesAdminClientProcessor.getWritingIndexName(), indicesAdminClientProcessor.getIndices(), tec.typeName());
            
            //添加缓存中
            indicesAdminClientProcessor.getTransportClientProcessorMap().put(transportClientProcessor.type(), transportClientProcessor);
            
            // 创建type实体对应的es mapping信息
            createTypeAccessor(tec, transportClientProcessor);
        }
    }
    
    /**
     * 创建type实体对应的es mapping信息
     *
     * @param type
     * @param typeAccessorMap void
     */
    private <T extends Type> void createTypeAccessor(MappingConfiguration<T> tec, TransportClientProcessor typeProcessor)
    {
        new TypeAccessorImpl<T>(tec, typeProcessor);
    }
    
}
