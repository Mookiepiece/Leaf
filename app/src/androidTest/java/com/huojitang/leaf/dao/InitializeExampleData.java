package com.huojitang.leaf.dao;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.huojitang.global.LeafApplication;
import com.huojitang.leaf.WishHistoryActivity;
import com.huojitang.leaf.model.BillItem;
import com.huojitang.leaf.model.MonthlyBill;
import com.huojitang.leaf.model.Tag;
import com.huojitang.leaf.model.Wish;
import com.huojitang.util.ColorConverter;

import org.junit.Test;
import org.litepal.LitePal;
import static org.junit.Assert.*;

/**
 * 非常简陋的加入测试数据的方法
 * @author Mookiepiece
 */
public class InitializeExampleData {

    @Test
    public void spTest(){
        SharedPreferences sp=LeafApplication.getPreferences();
        assertEquals("2019-1-15",sp.getString("FirstWishStartTime",""));
    }

    @Test
    public void initializeExampleData() {

        TagDao tagDao=TagDao.getInstance();
        Tag tag;
        tag =new Tag();
        tag.setName("吃");
        tag.setColor(ColorConverter.String2Color("8f0"));
        tag.setBudget(50000);
        tag.setIndex(0);
        tagDao.add(tag);

        tag =new Tag();
        tag.setName("喝");
        tag.setColor(ColorConverter.String2Color("346"));
        tag.setBudget(100000);
        tag.setComment("123456789");
        tag.setIndex(1);
        tagDao.add(tag);

        tag =new Tag();
        tag.setName("pIao");
        tag.setColor(ColorConverter.String2Color("8f0"));
        tag.setBudget(50000);
        tagDao.add(tag);
        tag.setIndex(2);
        tagDao.add(tag);

        tag =new Tag();
        tag.setName("未分类");
        tag.setColor(ColorConverter.String2Color("aaa"));
        tag.setReserved(true);
        tagDao.add(tag);

        WishDao wishDao=WishDao.getInstance();
        Wish wish;
        wish=new Wish();
        wish.setName("wish1");
        wish.setComment("454w252");
        wish.setStartTime("2019-1-15");
        wish.setFinishedTime("20");
        wish.setState(Wish.WISH_TODO);
        wishDao.add(wish);
        wish=new Wish();
        wish.setName("wish2");
        wish.setComment("454w252");
        wish.setStartTime("2019-3-5");
        wish.setFinishedTime("20");
        wish.setState(Wish.WISH_ACHIEVED);
        wish.setFinishedTime("2019-5-5");
        wishDao.add(wish);
        wish=new Wish();
        wish.setName("wish3");
        wish.setComment("454w252");
        wish.setStartTime("2019-3-5");
        wish.setFinishedTime("20");
        wish.setState(Wish.WISH_CANCELLED);
        wish.setFinishedTime("2019-4-15");
        wishDao.add(wish);

        SharedPreferences.Editor editor=LeafApplication.getPreferencesEditor();
        editor.putString("FirstWishStartTime","2019-1-15");
        editor.putString("LastWishStartTime","2019-5-5");
        editor.commit();

        MonthlyBillDao monthlyBillDao=MonthlyBillDao.getInstance();
        MonthlyBill monthlyBill;

        monthlyBill=new MonthlyBill();
        monthlyBill.setBudget(120000);
        monthlyBill.setComment("comment of month");
        monthlyBill.setDate("2019-5");
        monthlyBillDao.add(monthlyBill);
        monthlyBill=new MonthlyBill();
        monthlyBill.setBudget(150000);
        monthlyBill.setDate("2019-4");
        monthlyBillDao.add(monthlyBill);

        BillItemDao billItemDao=BillItemDao.getInstance();

        BillItem billItem;
        billItem=new BillItem();
        billItem.setDay(4);
        billItem.setMonthlyBill(monthlyBillDao.getByDate("2019-4"));
        billItem.setName("itemName0");
        billItem.setValue(1800);
        billItem.setTag(LitePal.where("name = '吃'").find(Tag.class).get(0));
        billItemDao.add(billItem);

        billItem=new BillItem();
        billItem.setDay(8);
        billItem.setMonthlyBill(monthlyBillDao.getByDate("2019-4"));
        billItem.setName("itemName1");
        billItem.setValue(5000);
        billItem.setTag(LitePal.where("name = '吃'").find(Tag.class).get(0));
        billItemDao.add(billItem);

        billItem=new BillItem();
        billItem.setDay(8);
        billItem.setMonthlyBill(monthlyBillDao.getByDate("2019-5"));
        billItem.setName("itemName222");
        billItem.setValue(5000);
        billItem.setTag(LitePal.where("name = '吃'").find(Tag.class).get(0));
        billItemDao.add(billItem);
        billItem=new BillItem();
        billItem.setDay(8);
        billItem.setMonthlyBill(monthlyBillDao.getByDate("2019-5"));
        billItem.setName("金坷垃");
        billItem.setValue(180000);
        billItem.setTag(LitePal.where("name = '喝'").find(Tag.class).get(0));
        billItemDao.add(billItem);
    }
}