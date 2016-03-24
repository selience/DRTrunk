package me.darkeet.android.demo.test;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.ArrayList;
import java.util.List;

import me.darkeet.android.cache.ModelCache;
import me.darkeet.android.demo.model.News;
import me.darkeet.android.demo.model.TestObject;

/**
 * Name: ApplicationTest
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/29 13:45
 * Desc:
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
    }

    /**
     * CacheModel
     */
    public void testSaveStoresInCache() {
        ModelCache modelCache = new ModelCache(1, 1, 1);
        String id = "123";

        List<News> dataList = new ArrayList<>();
        dataList.add(new News(1, "abc"));
        dataList.add(new News(2, "edf"));
        dataList.add(new News(2, "aaa"));
        dataList.add(new News(2, "bbb"));
        dataList.add(new News(2, "ccc"));

        TestObject preObject = new TestObject();
        preObject.setTestString("this is a test");
        preObject.setDataList(dataList);

        modelCache.put(id, preObject);

        TestObject postObject = (TestObject) modelCache.get(id);
        assertEquals("this is a test", postObject.getTestString());
    }
}
