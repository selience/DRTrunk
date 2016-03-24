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
import me.darkeet.android.util.PermissionUtils;
import me.darkeet.android.util.Toaster;

/**
 * Name: PermissionFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/4 16:06
 * Desc: 使用该系统方法动态请求权限
 */
public class PermissionNormalFragment extends DRBaseStackFragment implements View.OnClickListener {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_fragment_permission, container, false);
        Button button = (Button) view.findViewById(R.id.id_button_permission);
        button.setText("添加联系人<运行时动态请求权限>");
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        insertDummyContactWrapper();
    }

    /**
     * 请求授权
     */
    private void insertDummyContactWrapper() {
        if (PermissionUtils.hasSelfPermissions(mActivity, Manifest.permission.WRITE_CONTACTS)) {
            insertDummyContact();
        } else {
            if (!PermissionUtils.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.WRITE_CONTACTS)) {
                showRationaleForWriteContacts();
            }
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE_ASK_PERMISSIONS);
        }
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

        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                // 用户同意授权
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    insertDummyContact();
                } else {
                    // 用户拒绝授权
                    showDeniedForWriteContacts();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 向通讯录写入联系人数据
     */
    private void insertDummyContact() {
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


    void showRationaleForWriteContacts() {
        Toaster.show(mActivity, "You need to allow access to Contacts");
    }

    void showDeniedForWriteContacts() {
        Toaster.show(mActivity, "WRITE_CONTACTS Denied");
    }

    void showAgreeForWriteContacts(){
        Toaster.show(mActivity, "WRITE_CONTACTS Success");
    }
}
