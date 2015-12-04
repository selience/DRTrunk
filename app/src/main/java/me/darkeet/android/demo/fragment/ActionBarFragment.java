package me.darkeet.android.demo.fragment;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.darkeet.android.base.DRBaseStackFragment;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.activity.ResultActivity;
import me.darkeet.android.demo.constant.IntentConstants;
import me.darkeet.android.utils.IntentUtils;
import me.darkeet.android.utils.Toaster;

/**
 * Name: ActionBarFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/13 13:21
 * Desc: 在ActionBar添加各种组件：1. 资源文件方式；2. 采用代码动态创建;
 */
public class ActionBarFragment extends DRBaseStackFragment {
    private static final int MENU_SEARCH = 0;
    private static final int MENU_SHARE = 1;
    private static final int MENU_SETTING = 2;
    private static final int MENU_ABOUT = 3;

    private Menu mMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setTitle(getArguments().getString(IntentConstants.FRAGMENT_TITLE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_fragment_actionbar, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.mMenu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toaster.show(mActivity, item.getTitle());
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_menu_item)
    public void addMenuItem() {
        MenuItem menuItem = mMenu.add(0, MENU_ABOUT, MENU_ABOUT, "关于");
        menuItem.setIcon(android.R.drawable.ic_menu_help);
        MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
    }

    /**
     * actionView使用
     */
    @OnClick(R.id.btn_menu_actionview)
    public void addSearchView() {
        SearchView searchView = new SearchView(mActivity);
        searchView.setQueryHint("搜索建议");
        searchView.setIconified(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(false); // 默认不展开

        // http://developer.android.com/guide/topics/search/search-dialog.html
        ComponentName componentName = new ComponentName(mActivity, ResultActivity.class);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        MenuItem menuItem = mMenu.add(0, MENU_SEARCH, MENU_SEARCH, "搜索");
        menuItem.setIcon(android.R.drawable.ic_menu_search);
        MenuItemCompat.setActionView(menuItem, searchView);
        MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM
                | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
    }

    /**
     * actionProvider使用
     */
    @OnClick(R.id.btn_menu_provider)
    public void addActionProvider() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(intent.EXTRA_TEXT, "Text I want to share");

        ShareActionProvider shareProvider = new ShareActionProvider(mActivity);
        shareProvider.setShareIntent(intent);

        MenuItem menuItem = mMenu.add(0, MENU_SHARE, MENU_SHARE, "分享");
        menuItem.setIcon(android.R.drawable.ic_menu_share);
        MenuItemCompat.setActionProvider(menuItem, shareProvider);
        MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
    }


    /**
     * 自定义actionProvider
     */
    @OnClick(R.id.btn_menu_setting)
    public void addSettingProvider() {
        MenuItem menuItem = mMenu.add(0, MENU_SETTING, MENU_SETTING, "设置");
        menuItem.setIcon(android.R.drawable.ic_menu_manage);
        MenuItemCompat.setActionProvider(menuItem, new SettingsActionProvider(mActivity));
        MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_NEVER);
    }


    public static class SettingsActionProvider extends ActionProvider {
        private static final Intent sSettingsIntent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");

        public SettingsActionProvider(Context context) {
            super(context);
        }

        @Override
        public View onCreateActionView() {
            TextView txtView = new TextView(getContext());
            txtView.setText("Setting");
            return txtView;
        }

        @Override
        public boolean onPerformDefaultAction() {
            final Context context = getContext();
            if (IntentUtils.isIntentAvailable(context, sSettingsIntent)) {
                context.startActivity(sSettingsIntent);
            } else {
                context.startActivity(IntentUtils.newManageApplicationIntent());
            }
            return true;
        }
    }
}
