package me.darkeet.android.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import me.darkeet.android.adapter.BaseListAdapter;

public class MenuListAdapter extends BaseListAdapter<String, MenuListAdapter.ItemViewHolder> {

    public MenuListAdapter(Context context) {
        super(context);
    }

    @Override
    public View newView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(View convertView, int viewType) {
        ItemViewHolder viewHolder = new ItemViewHolder();
        viewHolder.textView = (TextView) convertView.findViewById(android.R.id.text1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(View convertView, ItemViewHolder viewHolder, int position, int viewType) {
        viewHolder.textView.setText(getItem(position));
    }


    static class ItemViewHolder {
        TextView textView;
    }
}
