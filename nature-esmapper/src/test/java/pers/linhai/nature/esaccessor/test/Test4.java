/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.Test4.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月24日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.test;

import java.io.IOException;

import org.elasticsearch.index.query.MatchAllQueryBuilder;

import pers.linhai.nature.esaccessor.test.model.UserIndex;
import pers.linhai.nature.esaccessor.test.model.UserInfo;
import pers.linhai.nature.esaccessor.test.model.WorkInfo;
import pers.linhai.nature.esmapper.core.MapperFactory;
import pers.linhai.nature.esmapper.interfaces.BulkOperation;
import pers.linhai.nature.esmapper.interfaces.HitCollection;
import pers.linhai.nature.esmapper.interfaces.TypeMapper;
import pers.linhai.nature.esmapper.interfaces.HitCollection.Consumer;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class Test4
{
    
    public static void main(String[] args) throws IOException
    {
        MapperFactory.load(UserIndex.class);
        final TypeMapper<UserInfo> userInfoAccessor = MapperFactory.typeAccessor(UserInfo.class);
        new Thread()
        {

            /**
             * 
             *
             */
            public void run()
            {
                while (true)
                {
                    BulkOperation<UserInfo> bo = userInfoAccessor.bulkOperations();
                    for (int i = 0; i < 1100; i++)
                    {
                        UserInfo ui = new UserInfo();
                        ui.setName("lilinhai_" + i);
                        ui.setAge(30);
                        
                        WorkInfo wi = new WorkInfo();
                        wi.setJob("soft develop");
                        wi.setAddress("郫县");
                        wi.setMoneny(1000 + i);
                        ui.setWorkInfo(wi);
                        bo.add(ui);
                    }
                    bo.close();
                    try
                    {
                        sleep(5 * 1000);
                        
                        HitCollection<UserInfo> userInfoHitCollection = userInfoAccessor.query(new MatchAllQueryBuilder(), 0, 10);
                        userInfoHitCollection.foreach(new Consumer<UserInfo>()
                        {
                            public void process(UserInfo t)
                            {
                                System.out.println(t);
                            }
                        });
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            
        }.start();
    }
}
