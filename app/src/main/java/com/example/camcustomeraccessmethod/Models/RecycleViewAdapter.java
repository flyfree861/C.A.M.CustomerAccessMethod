package com.example.camcustomeraccessmethod.Models;

import android.content.Context;
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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>
{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mConnName = new ArrayList<>();
    private ArrayList<String> mKindVpn  = new ArrayList<>();
    private Context mContext;

    public RecycleViewAdapter(Context context, ArrayList<String> mConnName, ArrayList<String> mKindVpn )
    {
        this.mConnName = mConnName;
        this.mKindVpn = mKindVpn;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.connection_card_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
                     Glide.with(mContext)
                             .asBitmap()
                             .load(mConnName.get(position))
                             .into(holder.imgIcon);
                     holder.txtConnection.setText(mConnName.get(position));

                     holder.parentLayout.setOnClickListener(new View.OnClickListener()
                     {
                         @Override
                         public void onClick(View view)
                         {
                             Toast.makeText(mContext, "Boh", Toast.LENGTH_SHORT).show();
                         }
                     });
    }

    @Override
    public int getItemCount()
    {
        return mConnName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView imgIcon;
        TextView txtConnection, txtKindVpn;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView)
        {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.CircleShowConnImg);
            txtConnection = itemView.findViewById(R.id.txtShowConnCustomer);
            txtKindVpn = itemView.findViewById(R.id.txtShowConnKindVpn);
            parentLayout = itemView.findViewById(R.id.layoutConnectionCardList);
        }
    }
}
