package com.huojitang.leaf.dao;

import android.util.Log;

import com.huojitang.leaf.model.Wish;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import com.huojitang.leaf.model.Tag;

public class WishDaoTest {

    @Test
    public void test() {
        WishDao wd = WishDao.getInstance();
//        Wish w = new Wish();
//        w.setName("金坷垃");
//        w.setValue(1800);
//        w.setStartTime("2019-5-26");
        List<Wish> wishList = wd.listByStartTime("2019-4");

        assertEquals(2,wishList.size());
    }
}