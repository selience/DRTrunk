package me.darkeet.android.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.darkeet.android.base.DRBaseStackFragment;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.constant.IntentConstants;
import me.darkeet.android.demo.fragment.view.CustomTextFragment;

/**
 * Name: ViewFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/27 17:05
 * Desc:
 */
public class ViewFragment extends DRBaseStackFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_fragment_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_view_text)
    public void onClickWithText(View view) {
        Button button = (Button) view;
        String text = button.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString(IntentConstants.FRAGMENT_TITLE, text);
        replace(CustomTextFragment.class, "CustomTextFragment", bundle);
    }
}
