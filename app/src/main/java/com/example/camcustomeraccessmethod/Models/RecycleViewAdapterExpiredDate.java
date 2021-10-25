package com.example.camcustomeraccessmethod.Models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camcustomeraccessmethod.DBManager.DataBaseHelper;
import com.example.camcustomeraccessmethod.NewConnection;
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
        if(viewHolder.txtCustomer.getText().equals("Empty") && viewHolder.txtExpired.getText().equals("Empty"))
        {
            viewHolder.btnModify.setEnabled(false);
        }
        viewHolder.btnModify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), NewConnection.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("facilityName",   (TextUtils.isEmpty(connectionModel.get(position).getFacilityName()      ) ? "": connectionModel.get(position).getFacilityName()));
                intent.putExtra("kindVpn",        (TextUtils.isEmpty(connectionModel.get(position).getKindOfVpn()         ) ? "": connectionModel.get(position).getKindOfVpn()));
                intent.putExtra("tokenApp",       (TextUtils.isEmpty(connectionModel.get(position).getTokenAppAssociated()) ? "": connectionModel.get(position).getTokenAppAssociated()));
                intent.putExtra("userName",       (TextUtils.isEmpty(connectionModel.get(position).getUserName()          ) ? "": connectionModel.get(position).getUserName()));
                intent.putExtra("accountId",      (TextUtils.isEmpty(connectionModel.get(position).getAccountId()         ) ? "": connectionModel.get(position).getAccountId()));
                intent.putExtra("registeredEmail",(TextUtils.isEmpty(connectionModel.get(position).getRegisteredEmail()   ) ? "": connectionModel.get(position).getRegisteredEmail()));
                intent.putExtra("password",       (TextUtils.isEmpty(connectionModel.get(position).getPassword()          ) ? "": connectionModel.get(position).getPassword()));
                intent.putExtra("generalField1",  (TextUtils.isEmpty(connectionModel.get(position).getGeneralField1()     ) ? "": connectionModel.get(position).getGeneralField1()));
                intent.putExtra("generalField2",  (TextUtils.isEmpty(connectionModel.get(position).getGetGeneralField2()  ) ? "": connectionModel.get(position).getGetGeneralField2()));
                intent.putExtra("note",           (TextUtils.isEmpty(connectionModel.get(position).getNote()              ) ? "": connectionModel.get(position).getNote()));
                intent.putExtra("itMail",         (TextUtils.isEmpty(connectionModel.get(position).getItEmail()           ) ? "" : connectionModel.get(position).getItEmail()));
                intent.putExtra("expireDate",     (TextUtils.isEmpty(connectionModel.get(position).getExpireDate()        ) ? "": connectionModel.get(position).getExpireDate()));
                intent.putExtra("advise",         (Boolean.valueOf(connectionModel.get(position).getExpireDateAdvise()    ) ? true : false));
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
