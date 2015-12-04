package me.darkeet.android.demo.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import com.android.volley.Listener;
import com.android.volley.core.RequestManager;
import com.android.volley.image.NetworkImageView;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.model.ViewModel;
import android.support.v7.extensions.PaletteManager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ViewModel> mDataList;
    private PaletteManager paletteManager;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        this.mDataList = new ArrayList<>();
        this.paletteManager = new PaletteManager();
    }

    public void addItems(List<ViewModel> items) {
        this.mDataList.addAll(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ViewModel item = mDataList.get(position);
        holder.itemView.setTag(item);
        holder.text.setText(item.getText());

        holder.image.setImageListener(new Listener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                holder.updatePalette(paletteManager, bitmap);
            }
        });
        holder.image.setImageUrl(item.getImage(), RequestManager.loader().useDefaultLoader().obtain());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove((ViewModel) view.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void add(ViewModel item, int position) {
        mDataList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(ViewModel item) {
        int position = mDataList.indexOf(item);
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    private static int setColorAlpha(int color, int alpha) {
        return (alpha << 24) | (color & 0x00ffffff);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public NetworkImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            image = (NetworkImageView) itemView.findViewById(R.id.image);
        }

        public void updatePalette(PaletteManager paletteManager, Bitmap bitmap) {
            String key = ((ViewModel) itemView.getTag()).getImage();
            paletteManager.getPalette(key, bitmap, new PaletteManager.Callback() {
                @Override
                public void onPaletteReady(Palette palette) {
                    int bgColor = palette.getVibrantSwatch().getRgb();
                    text.setBackgroundColor(setColorAlpha(bgColor, 192));
                    text.setTextColor(bgColor);
                }
            });
        }
    }
}