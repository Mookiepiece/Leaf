package com.huojitang.leaf.dao;

import com.huojitang.leaf.model.BillItem;
import com.huojitang.leaf.model.Tag;

import org.junit.Test;

import static org.junit.Assert.*;

public class BillItemDaoTest {

    @Test
    public void getById() {
        BillItem b=BillItemDao.getInstance().getById(21);
        Tag t=b.getTag();
        assertEquals(null,t);
    }
}