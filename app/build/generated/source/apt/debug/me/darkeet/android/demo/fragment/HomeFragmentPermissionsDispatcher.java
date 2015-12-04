package me.darkeet.android.demo.fragment;

import java.lang.String;
import permissions.dispatcher.PermissionUtils;

public final class HomeFragmentPermissionsDispatcher {
  private static final int REQUEST_STARTPICKIMAGE = 0;

  private static final String[] PERMISSION_STARTPICKIMAGE = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};

  private HomeFragmentPermissionsDispatcher() {
  }

  public static void startPickImageWithCheck(HomeFragment target) {
    if (PermissionUtils.hasSelfPermissions(target.getActivity(), PERMISSION_STARTPICKIMAGE)) {
      target.startPickImage();
    } else {
      if (PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_STARTPICKIMAGE)) {
        target.showRationaleForWriteContacts();
      }
      target.requestPermissions(PERMISSION_STARTPICKIMAGE, REQUEST_STARTPICKIMAGE);
    }
  }

  public static void onRequestPermissionsResult(HomeFragment target, int requestCode, int[] grantResults) {
    switch (requestCode) {
      case REQUEST_STARTPICKIMAGE:
      if (PermissionUtils.verifyPermissions(grantResults)) {
        target.startPickImage();
      } else {
        target.showDeniedForWriteContacts();
      }
      break;
      default:
      break;
    }
  }
}
