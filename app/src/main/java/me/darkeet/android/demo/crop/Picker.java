package me.darkeet.android.demo.crop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.File;

/**
 * Name: Picker
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/5 15:33
 * Desc:
 */
public class Picker {

    /*
     * 启动系统拍照功能
	 */
    public void takePhoto(Activity activity, File file) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        activity.startActivityForResult(intent, 1000);
    }
}
