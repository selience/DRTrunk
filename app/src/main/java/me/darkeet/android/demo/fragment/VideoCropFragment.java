package me.darkeet.android.demo.fragment;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.io.IOException;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.constant.IntentConstants;
import me.darkeet.android.demo.widget.TextureVideoView;
import me.darkeet.android.base.DRBaseStackFragment;

public class VideoCropFragment extends DRBaseStackFragment {
    private static final String FILE_URL = "mov_bbb.mp4";

    @Bind(R.id.id_textureView)
    TextureVideoView mTextureVideoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setTitle(getArguments().getString(IntentConstants.FRAGMENT_TITLE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_fragment_video, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mTextureVideoView.setDataSource(getOpenFile());
    }

    private AssetFileDescriptor getOpenFile() {
        try {
            return getResources().getAssets().openFd(FILE_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.video_crop, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_crop_center:
                mTextureVideoView.stop();
                mTextureVideoView.setScaleType(TextureVideoView.ScaleType.CENTER_CROP);
                mTextureVideoView.setDataSource(getOpenFile());
                mTextureVideoView.play();
                break;
            case R.id.menu_crop_top:
                mTextureVideoView.stop();
                mTextureVideoView.setScaleType(TextureVideoView.ScaleType.TOP);
                mTextureVideoView.setDataSource(getOpenFile());
                mTextureVideoView.play();
                break;
            case R.id.menu_crop_bottom:
                mTextureVideoView.stop();
                mTextureVideoView.setScaleType(TextureVideoView.ScaleType.BOTTOM);
                mTextureVideoView.setDataSource(getOpenFile());
                mTextureVideoView.play();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btnPlay, R.id.btnPause, R.id.btnStop})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                mTextureVideoView.play();
                break;
            case R.id.btnPause:
                mTextureVideoView.pause();
                break;
            case R.id.btnStop:
                mTextureVideoView.stop();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTextureVideoView.release();
    }
}
