package android.support.v7.extensions;

import java.util.Map;
import java.util.LinkedHashMap;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;

public abstract class BaseRecyclerViewAdapter<T extends Adapter<ViewHolder>> extends Adapter<ViewHolder> {
	
	private int mItemPosition=0;
	
	private final Map<String, T> mAdapters = new LinkedHashMap<String, T>();
	
	public BaseRecyclerViewAdapter() {
	}

	@Override
	public int getItemCount() {
		int total = 0;
		for (final T adapter : mAdapters.values()) {
			total += adapter.getItemCount();
		}
		return total;
	}

	@Override
	public int getItemViewType(int position) {
		mItemPosition = position;
		for (final T adapter : mAdapters.values()) {
			final int size = adapter.getItemCount();

			// check if position inside this section
			if (position < size) return adapter.getItemViewType(position);

			// otherwise jump into next section
			position -= size;
		}
		return 0;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		int position = mItemPosition;
		for (final T adapter : mAdapters.values()) {
			final int size = adapter.getItemCount();
			
			// check if position inside this section
			if (viewType==adapter.getItemViewType(position))
			if (position < size) return adapter.onCreateViewHolder(parent, viewType);
			
			// otherwise jump into next section
			position -= size;
		}
		return null;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		for (final T adapter : mAdapters.values()) {
			final int size = adapter.getItemCount();
			
			// check if position inside this section
			if (position < size) adapter.onBindViewHolder(holder, position);
			
			// otherwise jump into next section
			position -= size;
		}
	}
	
}
