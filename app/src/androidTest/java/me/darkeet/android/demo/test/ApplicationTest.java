package me.darkeet.android.demo.test;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.android.volley.ExecutorDelivery;
import com.android.volley.Listener;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.cache.NoCache;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.stack.HttpStack;
import com.android.volley.stack.HurlStack;
import com.android.volley.toolbox.BasicNetwork;

/**
 * Name: ApplicationTest
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/29 13:45
 * Desc:
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest(Class<Application> applicationClass) {
        super(applicationClass);
    }

}
