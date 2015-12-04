
package me.darkeet.android.demo.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.constant.IntentConstants;
import me.darkeet.android.facecropper.FaceCropper;
import me.darkeet.android.base.DRBaseStackFragment;

/**
 * Name: FaceDetectorFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/13 13:21
 * Desc: 脸部探测
 */
public class FaceDetectorFragment extends DRBaseStackFragment implements OnSeekBarChangeListener {

    private ImageView mFaceView;
    private FaceCropper mFaceCropper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setTitle(getArguments().getString(IntentConstants.FRAGMENT_TITLE));
        return inflater.inflate(R.layout.demo_fragment_facedetector, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        mFaceView = (ImageView) view.findViewById(R.id.imageView);
        seekBar.setOnSeekBarChangeListener(this);

        mFaceCropper = new FaceCropper(1f);
        mFaceCropper.setFaceMinSize(0);
        mFaceCropper.setDebug(true);

        updateView();
    }

    private void updateView() {
        Bitmap bitmap = mFaceCropper.getCroppedImage(mActivity, R.drawable.lluis);
        mFaceView.setImageBitmap(bitmap);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mFaceCropper.setEyeDistanceFactorMargin((float) progress / 10);
        updateView();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
