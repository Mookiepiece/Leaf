package com.huojitang.leaf.util;



public class PriceTransUtil{

    /**
     * 传入100倍的int值返回带点字符串
     * 60000  ->   600.00
     */
    static public String Int2Decimal(int price100){
        return price100/100+"."+price100%100;
    };
}
