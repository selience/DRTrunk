package me.darkeet.android.demo.fragment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.darkeet.android.base.DRBaseStackFragment;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.constant.IntentConstants;

/**
 * Name: CustomTextFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/27 17:04
 * Desc:
 */
public class CustomTextFragment extends DRBaseStackFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setTitle(getArguments().getString(IntentConstants.FRAGMENT_TITLE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_customtext, container, false);
    }
}
