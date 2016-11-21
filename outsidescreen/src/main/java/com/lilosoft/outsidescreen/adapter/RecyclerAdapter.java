package com.lilosoft.outsidescreen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lilosoft.outsidescreen.R;
import com.lilosoft.outsidescreen.bean.DeptDuty;

import java.util.ArrayList;

/**
 * Created by chablis on 2016/11/8.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private ArrayList<DeptDuty> data;
    private Context mContext;
    private OnItemClickLitener mOnItemClickLitener;


    //单击和长按监听
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    public OnItemClickLitener getmOnItemClickLitener() {
        return mOnItemClickLitener;
    }

    public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public ViewHolder(View v) {
            super(v);
            tv = (TextView) v.findViewById(R.id.title);
        }
    }

    public RecyclerAdapter(ArrayList<DeptDuty> data) {
        this.data = data;
    }

    public RecyclerAdapter(Context mContext,ArrayList<DeptDuty> data) {
        this.data = data;
        this.mContext=mContext;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        mContext=parent.getContext();
        ViewHolder vh=new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matter,parent,false));
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, int position) {
//        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.mipmap.temp);
//        Palette palette = Palette.generate(bm);
//        Palette.Swatch vibrant = palette.getVibrantSwatch();//有活力
//        Palette.Swatch b = palette.getDarkVibrantSwatch();//有活力 暗色
//        Palette.Swatch c = palette.getLightVibrantSwatch();//有活力 亮色
//        Palette.Swatch d = palette.getMutedSwatch();//柔和
//        Palette.Swatch e = palette.getDarkMutedSwatch();//柔和 暗色
//        Palette.Swatch f = palette.getLightMutedSwatch();//柔和 亮色
//        if (vibrant != null) {
//                    holder.tv.setBackgroundColor(
//                            b.getRgb());
//                    holder.tv.setTextColor(
//                            vibrant.getTitleTextColor());
//                    holder.cv.setCardBackgroundColor(b.getRgb());
//                }

        holder.tv.setText(data.get(position).getProjectname());

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
