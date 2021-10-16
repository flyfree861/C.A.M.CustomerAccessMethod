package com.example.camcustomeraccessmethod.Models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.camcustomeraccessmethod.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyConnectionViewHolder>
{
    Context context;
    List <ConnectionModel> connectionModel;
    CircleImageView icon;
    LayoutInflater inflater;

    public RecycleViewAdapter(Context appContext, List connectionModel, CircleImageView icon)
    {
        this.context = appContext;
        this.connectionModel = connectionModel;
        this.icon = icon;
        inflater = LayoutInflater.from(appContext);
    }

    @NonNull
    @Override
    public MyConnectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = inflater.inflate(R.layout.connection_card_list,parent,false);
        MyConnectionViewHolder myConnectionViewHolder = new MyConnectionViewHolder(v);
        return myConnectionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyConnectionViewHolder viewHolder, int position)
    {
        viewHolder.circleImageView.setImageResource(R.drawable.ic_factory);
        viewHolder.txtCustomer.setText(connectionModel.get(position).getFacilityName());
        viewHolder.txtKindOfVpn.setText(connectionModel.get(position).getKindOfVpn());
    }

    @Override
    public int getItemCount()
    {
        return connectionModel.size();
    }


    public class MyConnectionViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView circleImageView;
        TextView txtCustomer, txtKindOfVpn;

        public MyConnectionViewHolder(@NonNull View itemView)
        {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.CircleShowConnImg);
            txtCustomer     = itemView.findViewById(R.id.txtShowConnCustomer);
            txtKindOfVpn    = itemView.findViewById(R.id.txtShowConnKindVpn);
        }
    }
}
