package com.huojitang.leaf.dao;

import android.content.SharedPreferences;

import com.huojitang.leaf.global.LeafApplication;
import com.huojitang.leaf.model.BillItem;
import com.huojitang.leaf.model.MonthlyBill;
import com.huojitang.leaf.model.Tag;
import com.huojitang.leaf.model.Wish;
import com.huojitang.leaf.util.ColorConverter;

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
        assertEquals("2019-01-15",sp.getString("FirstWishStartTime",""));
    }

    @Test
    public void initializeExampleData() {

        TagDao tagDao=TagDao.getInstance();
        Tag tag;
        tag =new Tag();
        tag.setName("吃");
        tag.setColor(1);
        tag.setIcon(1);
        tag.setBudget(50000);
        tag.setIndex(0);
        tagDao.add(tag);

        tag =new Tag();
        tag.setName("喝");
        tag.setColor(2);
        tag.setIcon(3);
        tag.setBudget(100000);
        tag.setComment("123456789");
        tag.setIndex(1);
        tagDao.add(tag);

        tag =new Tag();
        tag.setName("pIao");
        tag.setColor(3);
        tag.setIcon(0);
        tag.setBudget(50000);
        tagDao.add(tag);
        tag.setIndex(2);
        tagDao.add(tag);

        WishDao wishDao=WishDao.getInstance();
        Wish wish;
        wish=new Wish();
        wish.setName("wish1");
        wish.setComment("454w252");
        wish.setStartTime("2019-01-15");
        wish.setFinishedTime("20");
        wish.setState(Wish.WISH_TODO);
        wishDao.add(wish);
        wish=new Wish();
        wish.setName("wish2");
        wish.setComment("454w252");
        wish.setStartTime("2019-03-05");
        wish.setFinishedTime("20");
        wish.setState(Wish.WISH_ACHIEVED);
        wish.setFinishedTime("2019-05-05");
        wishDao.add(wish);
        wish=new Wish();
        wish.setName("wish3");
        wish.setComment("454w252");
        wish.setStartTime("2019-03-05");
        wish.setFinishedTime("20");
        wish.setState(Wish.WISH_CANCELLED);
        wish.setFinishedTime("2019-04-15");
        wishDao.add(wish);

        SharedPreferences.Editor editor=LeafApplication.getPreferencesEditor();
        editor.putString("FirstWishStartTime","2018-10");
        editor.putString("LastWishEndTime","2019-05");
        editor.commit();

        MonthlyBillDao monthlyBillDao=MonthlyBillDao.getInstance();
        MonthlyBill monthlyBill;

        monthlyBill=new MonthlyBill();
        monthlyBill.setBudget(120000);
        monthlyBill.setComment("comment of month");
        monthlyBill.setDate("2019-05");
        monthlyBillDao.add(monthlyBill);
        monthlyBill=new MonthlyBill();
        monthlyBill.setBudget(150000);
        monthlyBill.setDate("2019-04");
        monthlyBillDao.add(monthlyBill);

        BillItemDao billItemDao=BillItemDao.getInstance();

        BillItem billItem;
        billItem=new BillItem();
        billItem.setDay(4);
        billItem.setMonthlyBill(monthlyBillDao.getByDate("2019-04"));
        billItem.setName("itemName0");
        billItem.setValue(1800);
        billItem.setTag(LitePal.where("name = '吃'").find(Tag.class).get(0));
        billItemDao.add(billItem);

        billItem=new BillItem();
        billItem.setDay(8);
        billItem.setMonthlyBill(monthlyBillDao.getByDate("2019-04"));
        billItem.setName("itemName1");
        billItem.setValue(5000);
        billItem.setTag(LitePal.where("name = '吃'").find(Tag.class).get(0));
        billItemDao.add(billItem);

        billItem=new BillItem();
        billItem.setDay(8);
        billItem.setMonthlyBill(monthlyBillDao.getByDate("2019-05"));
        billItem.setName("itemName222");
        billItem.setValue(5000);
        billItem.setTag(LitePal.where("name = '吃'").find(Tag.class).get(0));
        billItemDao.add(billItem);
        billItem=new BillItem();
        billItem.setDay(8);
        billItem.setMonthlyBill(monthlyBillDao.getByDate("2019-05"));
        billItem.setName("金坷垃");
        billItem.setValue(180000);
        billItem.setTag(LitePal.where("name = '喝'").find(Tag.class).get(0));
        billItemDao.add(billItem);
    }
}
