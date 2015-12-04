package me.darkeet.android.demo.fragment.permission;

import java.lang.String;
import permissions.dispatcher.PermissionUtils;

public final class PermissionAnnotationFragmentPermissionsDispatcher {
  private static final int REQUEST_INSERTDUMMYCONTACT = 0;

  private static final String[] PERMISSION_INSERTDUMMYCONTACT = new String[]{"android.permission.WRITE_CONTACTS"};

  private PermissionAnnotationFragmentPermissionsDispatcher() {
  }

  public static void insertDummyContactWithCheck(PermissionAnnotationFragment target) {
    if (PermissionUtils.hasSelfPermissions(target.getActivity(), PERMISSION_INSERTDUMMYCONTACT)) {
      target.insertDummyContact();
    } else {
      if (PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_INSERTDUMMYCONTACT)) {
        target.showRationaleForWriteContacts();
      }
      target.requestPermissions(PERMISSION_INSERTDUMMYCONTACT, REQUEST_INSERTDUMMYCONTACT);
    }
  }

  public static void onRequestPermissionsResult(PermissionAnnotationFragment target, int requestCode, int[] grantResults) {
    switch (requestCode) {
      case REQUEST_INSERTDUMMYCONTACT:
      if (PermissionUtils.verifyPermissions(grantResults)) {
        target.insertDummyContact();
      } else {
        target.showDeniedForWriteContacts();
      }
      break;
      default:
      break;
    }
  }
}
