package com.example.camcustomeraccessmethod.Models;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.camcustomeraccessmethod.DBManager.DataBaseHelper;
import com.example.camcustomeraccessmethod.PopupActivity;
import com.example.camcustomeraccessmethod.R;
import com.example.camcustomeraccessmethod.ShowConnection;

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
    View v;

    public RecycleViewAdapter(Context appContext, List connectionModel)
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
        v = inflater.inflate(R.layout.connection_card_list,parent,false);
        MyConnectionViewHolder myConnectionViewHolder = new MyConnectionViewHolder(v);
        return myConnectionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyConnectionViewHolder viewHolder, @SuppressLint("RecyclerView") int position)
    {
        viewHolder.circleImageView.setImageResource(R.drawable.ic_factory);
        viewHolder.txtCustomer.setText(connectionModel.get(position).getFacilityName());
        viewHolder.txtKindOfVpn.setText(connectionModel.get(position).getKindOfVpn());
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DataBaseHelper db = new DataBaseHelper(view.getContext());
                boolean result = db.deleteConnection(connectionModel.get(position).getFacilityName(),connectionModel.get(position).getKindOfVpn());
                if(result)
                {
                    connectionModel.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(view.getContext(), "The connection: "+connectionModel.get(position).getFacilityName()+ "has been delete successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(view.getContext(), "Something went wrong or your data has been already deleted \n field not deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewHolder.btnShow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), PopupActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("facilityName",   (connectionModel.get(position).getFacilityName()       =="") ? "Empty": connectionModel.get(position).getFacilityName());
                intent.putExtra("kindVpn",        (connectionModel.get(position).getKindOfVpn()          =="") ? "Empty": connectionModel.get(position).getKindOfVpn());
                intent.putExtra("tokenApp",       (connectionModel.get(position).getTokenAppAssociated() =="") ? "Empty": connectionModel.get(position).getTokenAppAssociated());
                intent.putExtra("userName",       (connectionModel.get(position).getUserName()           =="") ? "Empty": connectionModel.get(position).getUserName());
                intent.putExtra("accountId",      (connectionModel.get(position).getAccountId()          =="") ? "Empty": connectionModel.get(position).getAccountId());
                intent.putExtra("registeredEmail",(connectionModel.get(position).getRegisteredEmail()    =="") ? "Empty": connectionModel.get(position).getRegisteredEmail());
                intent.putExtra("password",       (connectionModel.get(position).getPassword()           =="") ? "Empty": connectionModel.get(position).getPassword());
                intent.putExtra("generalField1",  (connectionModel.get(position).getGeneralField1()      =="") ? "Empty": connectionModel.get(position).getGeneralField1());
                intent.putExtra("generalField2",  (connectionModel.get(position).getGetGeneralField2()   =="") ? "Empty": connectionModel.get(position).getGetGeneralField2());
                intent.putExtra("note",           (connectionModel.get(position).getNote()               =="") ? "Empty": connectionModel.get(position).getNote());
                intent.putExtra("itMail",         (connectionModel.get(position).getItEmail()            =="") ? "Empty": connectionModel.get(position).getItEmail());
                intent.putExtra("expireDate",     (connectionModel.get(position).getExpireDate()         =="") ? "Empty": connectionModel.get(position).getExpireDate());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount()
    {
        if(connectionModel.size() > 0)
        {
            return connectionModel.size();
        }
        else{return 0;}
    }

    public class MyConnectionViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView circleImageView;
        TextView txtCustomer, txtKindOfVpn;
        ImageButton btnDelete,btnShow;

        public MyConnectionViewHolder(@NonNull View itemView)
        {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.CircleShowConnImg);
            txtCustomer     = itemView.findViewById(R.id.txtShowConnCustomer);
            txtKindOfVpn    = itemView.findViewById(R.id.txtShowConnKindVpn);
            btnDelete       = itemView.findViewById(R.id.btnCardListDelete);
            btnShow         = itemView.findViewById(R.id.btnCardListShow);
        }
    }



}
