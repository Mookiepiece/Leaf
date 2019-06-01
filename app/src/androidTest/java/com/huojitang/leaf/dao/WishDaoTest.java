package com.huojitang.leaf.dao;

import android.util.Log;

import com.huojitang.leaf.model.Wish;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import com.huojitang.leaf.model.Tag;

public class WishDaoTest {
    @Before
    public void init() {
        Wish aWish = new Wish();
        aWish.setName("平板电脑");
        aWish.setValue(200000);
        aWish.setState(Wish.WISH_ACHIEVED);
        aWish.setStartTime(LocalDate.of(2019, 3, 1));
        aWish.setFinishedTime(LocalDate.of(2019, 6, 30));

        WishDao.getInstance().add(aWish);

        Wish anotherWish = new Wish();
        anotherWish.setName("Kindle");
        anotherWish.setValue(99800);
        anotherWish.setState(Wish.WISH_ACHIEVED);
        anotherWish.setStartTime(LocalDate.of(2018, 10, 1));
        anotherWish.setFinishedTime(LocalDate.of(2018, 11, 11));

        WishDao.getInstance().add(anotherWish);

        Wish unfinishedWish = new Wish();
        unfinishedWish.setName("MP3 播放器");
        unfinishedWish.setValue(29900);
        unfinishedWish.setStartTime(LocalDate.now());

        WishDao.getInstance().add(unfinishedWish);

        Wish cancelledWish = new Wish();
        cancelledWish.setName("MacBook Pro");
        cancelledWish.setStartTime(LocalDate.of(2019, 1, 1));
        cancelledWish.setFinishedTime(LocalDate.of(2019, 3, 31));
        cancelledWish.setState(Wish.WISH_CANCELLED);
        cancelledWish.setValue(4999900);

        WishDao.getInstance().add(cancelledWish);
    }

    @Test
    public void testGetEarliestStartedTime() {
        String result = WishDao.getInstance().getEarliestStartTime();
        assertEquals(LocalDate.of(2018, 10, 1).toString(), result);
    }

    @Test
    public void testGetLatestFinishedTime() {
        String result = WishDao.getInstance().getLatestFinishedTime();
        assertEquals(LocalDate.of(2019, 6, 30).toString(), result);
    }
}