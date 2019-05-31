package com.huojitang.leaf;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.huojitang.leaf.global.LeafApplication;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        SharedPreferences.Editor editor= LeafApplication.getPreferencesEditor();
        editor.putString("FirstWishStartTime","2017-10");
        editor.putString("LastWishEndTime","2020-05");
        editor.commit();

//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("com.huojitang.leaf", appContext.getPackageName());
    }
}
