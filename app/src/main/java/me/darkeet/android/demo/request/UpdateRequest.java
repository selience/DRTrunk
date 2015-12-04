package me.darkeet.android.demo.request;

import com.android.volley.Request;
import com.android.volley.core.RequestInterface;
import com.android.volley.request.MultiPartRequest;
import com.android.volley.request.StringMultipartRequest;
import com.android.volley.request.StringRequest;

/**
 * Created by yi on 2015/8/12.
 */
public class UpdateRequest extends RequestInterface {
    private static final String IMGUR_CLIENT_ID = "a0ee9b59aa1f65e";

    @Override
    public Request create() {
       /* MultiPartRequest request = new StringMultipartRequest(
                Request.Method.POST,
                "https://api.imgur.com/3/image",
                useInterfaceListener());
        request.addHeader("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
        request.addParams("title", System.currentTimeMillis()+"");
        request.addFile("image", "/storage/emulated/0/Android/data/com.sdx.mobile.anxin/cache/files/1439360414744.jpg");
        return request;*/
        return new StringRequest("http://www.baidu.com", useInterfaceListener());
    }

}
