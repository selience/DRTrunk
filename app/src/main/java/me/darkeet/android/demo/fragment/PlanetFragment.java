package me.darkeet.android.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import me.darkeet.android.demo.R;

import me.darkeet.android.base.DRBaseFragment;


public class PlanetFragment extends DRBaseFragment {

	public static final String ARG_PLANET_EXTRAS = "planet";
	
	public static PlanetFragment newInstance(String title) {
		PlanetFragment fragment = new PlanetFragment();
		Bundle bundle = new Bundle();
		bundle.putString(ARG_PLANET_EXTRAS, title);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	    View mainView=inflater.inflate(R.layout.swipe_list_item, container, false);
	    TextView textView = (TextView)mainView.findViewById(android.R.id.text1);
	    textView.setText(getArguments().getString(ARG_PLANET_EXTRAS));
	    return mainView;
	}
}
