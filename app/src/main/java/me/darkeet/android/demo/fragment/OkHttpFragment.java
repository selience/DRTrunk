package me.darkeet.android.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.okhttp.OkHttpClient;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.darkeet.android.base.DRBaseStackFragment;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.constant.IntentConstants;
import me.darkeet.android.log.DebugLog;
import me.darkeet.android.retrofit.convert.JsonConverterFactory;
import me.darkeet.android.utils.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Name: OkHttpFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/9 16:51
 * Desc:
 */
public class OkHttpFragment extends DRBaseStackFragment {
    private static final String TAG = "okhttp";

    private static final String GET_TEXT_TASK = "GET_TEXT_TASK";
    private static final String GET_FILE_TASK = "GET_FILE_TASK";

    private OkHttpClient httpClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpClient = new OkHttpClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setTitle(getArguments().getString(IntentConstants.FRAGMENT_TITLE));
        View view = inflater.inflate(R.layout.demo_fragment_volley, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 请求文本数据
     */
    @OnClick(R.id.btn_text)
    public void requestString() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com/")
                .addConverterFactory(JsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<String> call = service.loadString("android");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toaster.show(mActivity, Thread.currentThread().getName() + "-" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * 下文文件到本地磁盘
     */
    @OnClick(R.id.btn_download)
    public void requestFile() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://112.65.222.35/")
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<Void> call = service.loadFile();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                DebugLog.d("onResponse");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public interface APIService {

        @GET("s")
        Call<String> loadString(@Query("wd") String wd);

        @GET("dd.myapp.com/16891/6189936FAB997424DADAFAA289E0F7A1.apk")
        Call<Void> loadFile();
    }
}
