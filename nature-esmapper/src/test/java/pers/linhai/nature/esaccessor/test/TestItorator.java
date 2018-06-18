/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : TestItorator.java</p>
 * <p>Package     : pers.linhai.nature.indexaccess.test</p>
 * @Creator lilinhai 2018年6月17日 下午12:04:17
 * @Version  V1.0  
 */ 

package pers.linhai.nature.esaccessor.test;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : TestItorator</p>
 * @author lilinhai 2018年6月17日 下午12:04:17
 * @version 1.0
 */
public class TestItorator
{
    
    public static void main(String[] args)
    {
        List<String> sl = new ArrayList<String>();
        sl.add("aaaaaaaa");
        sl.add("dddddddddd");
        for (String string : sl)
        {
            System.out.println(string);
        }
        for (String string : sl)
        {
            System.out.println(string);
        }
    }
}
