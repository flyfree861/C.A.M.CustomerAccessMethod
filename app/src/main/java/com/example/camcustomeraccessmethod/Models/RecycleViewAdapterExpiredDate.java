package com.example.camcustomeraccessmethod.Models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camcustomeraccessmethod.DBManager.DataBaseHelper;
import com.example.camcustomeraccessmethod.R;
import com.example.camcustomeraccessmethod.RegistrationActivity;
import com.example.camcustomeraccessmethod.ShowConnection;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleViewAdapterExpiredDate extends RecyclerView.Adapter<RecycleViewAdapterExpiredDate.MyConnectionViewHolder>
{
    Context context;
    List<ConnectionModel> connectionModel;
    CircleImageView icon;
    LayoutInflater inflater;
    View v;

    public RecycleViewAdapterExpiredDate(Context appContext, List connectionModel)
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
        v = inflater.inflate(R.layout.card_expired_date, parent, false);
        MyConnectionViewHolder myConnectionViewHolder = new MyConnectionViewHolder(v);
        return myConnectionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyConnectionViewHolder viewHolder, @SuppressLint("RecyclerView") int position)
    {
        viewHolder.circleImageView.setImageResource(R.drawable.ic_factory);
        viewHolder.txtCustomer.setText(connectionModel.get(position).getFacilityName());
        viewHolder.txtExpired.setText(connectionModel.get(position).getExpireDate());
        viewHolder.btnModify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DataBaseHelper db = new DataBaseHelper(view.getContext());
                boolean result = db.deleteConnection(connectionModel.get(position).getFacilityName(), connectionModel.get(position).getKindOfVpn());
                if (result)
                {
                    connectionModel.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(view.getContext(), "The connection: " + connectionModel.get(position).getFacilityName() + "has been delete successfully", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(view.getContext(), "Something went wrong or your data has been already deleted \n field not deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewHolder.btnModify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), RegistrationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("facilityName",    (connectionModel.get(position).getFacilityName()       == "") ? "Empty" : connectionModel.get(position).getFacilityName());
                intent.putExtra("kindVpn",         (connectionModel.get(position).getKindOfVpn()          == "") ? "Empty" : connectionModel.get(position).getKindOfVpn());
                intent.putExtra("tokenApp",        (connectionModel.get(position).getTokenAppAssociated() == "") ? "Empty" : connectionModel.get(position).getTokenAppAssociated());
                intent.putExtra("userName",        (connectionModel.get(position).getUserName()           == "") ? "Empty" : connectionModel.get(position).getUserName());
                intent.putExtra("accountId",       (connectionModel.get(position).getAccountId()          == "") ? "Empty" : connectionModel.get(position).getAccountId());
                intent.putExtra("registeredEmail", (connectionModel.get(position).getRegisteredEmail()    == "") ? "Empty" : connectionModel.get(position).getRegisteredEmail());
                intent.putExtra("password",        (connectionModel.get(position).getPassword()           == "") ? "Empty" : connectionModel.get(position).getPassword());
                intent.putExtra("generalField1",   (connectionModel.get(position).getGeneralField1()      == "") ? "Empty" : connectionModel.get(position).getGeneralField1());
                intent.putExtra("generalField2",   (connectionModel.get(position).getGetGeneralField2()   == "") ? "Empty" : connectionModel.get(position).getGetGeneralField2());
                intent.putExtra("note",            (connectionModel.get(position).getNote()               == "") ? "Empty" : connectionModel.get(position).getNote());
                intent.putExtra("itMail",          (connectionModel.get(position).getItEmail()            == "") ? "Empty" : connectionModel.get(position).getItEmail());
                intent.putExtra("expireDate",      (connectionModel.get(position).getExpireDate()         == "") ? "Empty" : connectionModel.get(position).getExpireDate());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount()
    {
        if (connectionModel.size() > 0)
        {
            return connectionModel.size();
        } else
        {
            return 0;
        }
    }

    public class MyConnectionViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView circleImageView;
        TextView txtCustomer, txtExpired;
        ImageButton btnModify;

        public MyConnectionViewHolder(@NonNull View itemView)
        {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.CircleShowConnImg);
            txtCustomer = itemView.findViewById(R.id.txtCardExpiredCustomer);
            txtExpired = itemView.findViewById(R.id.txtCardExpiredExpireDate);
            btnModify = itemView.findViewById(R.id.btnExpireCardModify);
        }
    }


}
