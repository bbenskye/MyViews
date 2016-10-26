package com.example.wenchao.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created on 2016/9/22.
 *
 * @author wenchao
 * @since 1.0
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter {
    private List<String> mData;
    private Context context;
    private LayoutInflater inflater;
    private OnMyItemClickedListener clickedListener;

    public MyRecyclerAdapter(Context context, List<String> data){
        this.context = context;
        this.mData = data;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycle_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ((MyViewHolder)holder).tv.setText(mData.get(position));
        if (clickedListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickedListener.onClick(position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickedListener.onLongClick(position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;

        public MyViewHolder(View view){
            super(view);
            this.tv = (TextView) view.findViewById(R.id.tv_item_recycler);
        }
    }

    public void setClickedListener(OnMyItemClickedListener listener){
        this.clickedListener = listener;
    }
    public interface OnMyItemClickedListener{
        void onClick(int position);
        void onLongClick(int position);
    }
}
