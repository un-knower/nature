/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.analysis.tokenfilters.ASCIIFoldingTokenFilter.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.model.analysis.tokenfilters;

/**
 * 
 * <pre>
        A filter that stems words using a Snowball-generated stemmer. The language parameter controls the stemmer with the following available values: Armenian, Basque, Catalan, Danish, Dutch, English, Finnish, French, German, German2, Hungarian, Italian, Kp, Lithuanian, Lovins, Norwegian, Porter, Portuguese, Romanian, Russian, Spanish, Swedish, Turkish.
        
        For example:
        
        PUT /my_index
        {
            "settings": {
                "analysis" : {
                    "analyzer" : {
                        "my_analyzer" : {
                            "tokenizer" : "standard",
                            "filter" : ["standard", "lowercase", "my_snow"]
                        }
                    },
                    "filter" : {
                        "my_snow" : {
                            "type" : "snowball",
                            "language" : "Lovins"
                        }
                    }
                }
            }
        }
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class SnowballTokenFilter extends LowercaseTokenFilter
{
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public SnowballTokenFilter()
    {
        super("snowball");
    }
}
