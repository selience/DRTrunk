package me.darkeet.android.demo.fragment.permission;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Random;
import me.darkeet.android.base.DRBaseStackFragment;
import me.darkeet.android.demo.R;
import me.darkeet.android.log.DebugLog;
import me.darkeet.android.util.Toaster;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Name: PermissionFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/4 16:06
 * Desc: 第三方库注解动态请求权限 https://github.com/hotchemi/PermissionsDispatcher
 */
@RuntimePermissions
public class PermissionAnnotationFragment extends DRBaseStackFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_fragment_permission, container, false);
        Button button = (Button) view.findViewById(R.id.id_button_permission);
        button.setText("添加联系人<使用第三方支持库>");
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        PermissionAnnotationFragmentPermissionsDispatcher.insertDummyContactWithCheck(this);
    }

    /**
     * 授权回调方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionAnnotationFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /**
     * 向通讯录写入联系人数据
     */
    @NeedsPermission(Manifest.permission.WRITE_CONTACTS)
    void insertDummyContact() {
        // Two operations are needed to insert a new contact.
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>(2);
        // First, set up a new raw contact.
        ContentProviderOperation.Builder op =
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
        operations.add(op.build());

        // Next, set the name for the contact.
        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        "admin" + (new Random().nextInt(1000)));
        operations.add(op.build());

        // Apply the operations.
        ContentResolver resolver = getContentResolver();
        try {
            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
            showAgreeForWriteContacts();
        } catch (RemoteException e) {
            DebugLog.d(TAG, "Could not add a new contact: " + e.getMessage());
        } catch (OperationApplicationException e) {
            DebugLog.d(TAG, "Could not add a new contact: " + e.getMessage());
        }
    }


    // Option
    @OnShowRationale(Manifest.permission.WRITE_CONTACTS)
    void showRationaleForWriteContacts(PermissionRequest request) {
        Toaster.show(mActivity, "You need to allow access to Contacts");
    }

    // Option
    @OnPermissionDenied(Manifest.permission.WRITE_CONTACTS)
    void showDeniedForWriteContacts() {
        Toaster.show(mActivity, "WRITE_CONTACTS Denied");
    }

    void showAgreeForWriteContacts(){
        Toaster.show(mActivity, "WRITE_CONTACTS Success");
    }
}
