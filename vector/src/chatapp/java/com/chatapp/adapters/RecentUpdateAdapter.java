package com.chatapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.chatapp.share.RecentModel;
import com.chatapp.status_module.StatusActivity;

import java.util.ArrayList;

import im.vector.R;
import im.vector.view.VectorCircularImageView;

public class RecentUpdateAdapter extends RecyclerView.Adapter<RecentUpdateAdapter.RecentHolder> {

    private ArrayList<RecentModel> list;
    private Context context;
    private IShareAdapterClickListner listner;

    public IShareAdapterClickListner getListner() {
        return listner;
    }

    public void setListner(IShareAdapterClickListner listner) {
        this.listner = listner;
    }

    public RecentUpdateAdapter(ArrayList<RecentModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_holder, parent, false);
        return new RecentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentHolder holder, int position) {
        RecentModel model = list.get(position);
        if (position == 0) {
            holder.recentUpdate.setVisibility(View.GONE);
            holder.container.setVisibility(View.VISIBLE);
        } else if (position == 1) {
            holder.recentUpdate.setVisibility(View.VISIBLE);
            holder.container.setVisibility(View.GONE);
        } else {
            holder.recentUpdate.setVisibility(View.GONE);
            holder.container.setVisibility(View.VISIBLE);
        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    listner.onStatusClick();
                } else if (position == 1) {
                    //do nothing
                } else {
                    Intent i = new Intent(context, StatusActivity.class);
                    context.startActivity(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RecentHolder extends RecyclerView.ViewHolder {

        VectorCircularImageView userIcon;
        TextView userName, userTime;
        ConstraintLayout container;
        TextView recentUpdate;

        public RecentHolder(@NonNull View itemView) {
            super(itemView);
            userIcon = itemView.findViewById(R.id.user_status_icon);
            userName = itemView.findViewById(R.id.my_status);
            userTime = itemView.findViewById(R.id.time);
            container = itemView.findViewById(R.id.container);
            recentUpdate = itemView.findViewById(R.id.recentUpdate);

        }
    }

    public interface IShareAdapterClickListner {
        void onStatusClick();
    }
}
