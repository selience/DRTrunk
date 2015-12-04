package me.darkeet.android.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.extensions.PaletteManager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.Listener;
import com.android.volley.core.RequestManager;
import com.android.volley.image.NetworkImageView;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.model.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class StaggeredViewAdapter extends RecyclerView.Adapter<StaggeredViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Integer> mHeights;
    private List<ViewModel> mDataList;
    private PaletteManager paletteManager;

    public StaggeredViewAdapter(Context mContext) {
        this.mContext = mContext;
        paletteManager = new PaletteManager();

        mDataList = new ArrayList<>();
        mHeights = new ArrayList<>();
    }

    public void addItems(List<ViewModel> items) {
        this.mDataList.addAll(items);
        for (int i = 0; i < mDataList.size(); i++) {
            mHeights.add((int) (Math.random() * 300) + 300);
        }
    }

    public void add(ViewModel item, int position) {
        mDataList.add(position, item);
        mHeights.add((int) (Math.random() * 300) + 300);
        notifyItemInserted(position);
    }

    public void remove(ViewModel item) {
        int position = mDataList.indexOf(item);
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder mViewHolder = new ViewHolder(mView);
        return mViewHolder;
    }

    /**
     * 绑定ViewHoler，给item中的控件设置数据
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ViewGroup.LayoutParams mLayoutParams = holder.itemView.getLayoutParams();
        mLayoutParams.height = mHeights.get(position);
        holder.itemView.setLayoutParams(mLayoutParams);

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
            public void onClick(View v) {
                remove((ViewModel) v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
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
                    int bgColor = palette.getDarkVibrantSwatch().getRgb();
                    text.setBackgroundColor(setColorAlpha(bgColor, 192));
                    Palette.Swatch swatch = palette.getLightMutedSwatch();
                    if (swatch != null) {
                        text.setTextColor(swatch.getBodyTextColor());
                    }
                }
            });
        }
    }
}
