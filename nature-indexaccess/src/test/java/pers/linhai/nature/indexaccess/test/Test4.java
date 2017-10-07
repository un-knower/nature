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
package pers.linhai.nature.indexaccess.test;

import java.io.IOException;

import org.elasticsearch.index.query.MatchAllQueryBuilder;

import pers.linhai.nature.indexaccess.core.AccessorFactory;
import pers.linhai.nature.indexaccess.interfaces.BulkOperation;
import pers.linhai.nature.indexaccess.interfaces.HitCollection;
import pers.linhai.nature.indexaccess.interfaces.HitCollection.Consumer;
import pers.linhai.nature.indexaccess.interfaces.TypeAccessor;
import pers.linhai.nature.indexaccess.test.model.UserIndex;
import pers.linhai.nature.indexaccess.test.model.UserInfo;
import pers.linhai.nature.indexaccess.test.model.WorkInfo;

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
        AccessorFactory.load(UserIndex.class);
        final TypeAccessor<UserInfo> userInfoAccessor = AccessorFactory.typeAccessor(UserInfo.class);
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
                        userInfoHitCollection.each(new Consumer<UserInfo>()
                        {
                            public void consume(UserInfo t)
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
