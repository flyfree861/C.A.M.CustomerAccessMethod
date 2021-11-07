package com.example.camcustomeraccessmethod;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;

public class PopupActivity extends AppCompatActivity
{
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState)
    {
      String  facilityName;
      String  kindVpn;
      String  tokenApp;
      String  userName;
      String  accountId;
      String  registeredEmail;
      String  password;
      String  generalField1;
      String  generalField2;
      String  note;
      String  itMail;
      String  expireDate;
      MaterialTextView txtfacilityName,txtkindVpn,txttokenApp,txtuserName,txtaccountId,txtregisteredEmail,txtpassword,txtgeneralField1,txtgeneralField2,txtnote,txtitMail,txtexpireDate;

        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.popup_windows_show);


        txtfacilityName     = findViewById(R.id.txtPopupFacilityName);
        txtkindVpn          = findViewById(R.id.txtPopupKindVpn);
        txttokenApp         = findViewById(R.id.txtPopupTokenApp);
        txtuserName         = findViewById(R.id.txtPopupUserName);
        txtaccountId        = findViewById(R.id.txtPopupAccountId);
        txtregisteredEmail  = findViewById(R.id.txtPopupRegisteredMail);
        txtpassword         = findViewById(R.id.txtPopupPassword);
        txtgeneralField1    = findViewById(R.id.txtPopupGeneralField1);
        txtgeneralField2    = findViewById(R.id.txtPopupGeneralField2);
        txtnote             = findViewById(R.id.txtPopupNote);
        txtitMail           = findViewById(R.id.txtPopupItMail);
        txtexpireDate       = findViewById(R.id.txtPopupExpireDate);
        Bundle extras = getIntent().getExtras();

        if(extras != null)
        {
          facilityName   = extras.getString("facilityName")   ;
          kindVpn        = extras.getString("kindVpn")        ;
          tokenApp       = extras.getString("tokenApp")       ;
          userName       = extras.getString("userName")       ;
          accountId      = extras.getString("accountId")      ;
          registeredEmail= extras.getString("registeredEmail");
          password       = extras.getString("password")       ;
          generalField1  = extras.getString("generalField1")  ;
          generalField2  = extras.getString("generalField2")  ;
          note           = extras.getString("note")           ;
          itMail         = extras.getString("itMail")         ;
          expireDate     = extras.getString("expireDate")     ;
        }

        else
        {
          facilityName   = "Empty";
          kindVpn        = "Empty";
          tokenApp       = "Empty";
          userName       = "Empty";
          accountId      = "Empty";
          registeredEmail= "Empty";
          password       = "Empty";
          generalField1  = "Empty";
          generalField2  = "Empty";
          note           = "Empty";
          itMail         = "Empty";
          expireDate     = "Empty";
        }

       if(facilityName!= "Empty")
       {txtfacilityName.setText(facilityName);}
       else{txtfacilityName.setVisibility(View.GONE);}

       if(kindVpn!="Empty")
       {txtkindVpn.setText(kindVpn);}
       else{txtkindVpn.setVisibility(View.GONE);}

        if( tokenApp       != "Empty")
        {txttokenApp.setText(tokenApp);}
        else{txttokenApp.setVisibility(View.GONE);}

       if( userName       != "Empty")
        {txtuserName.setText(userName);}
        else{txtuserName.setVisibility(View.GONE);}

       if( accountId      != "Empty")
        {txtaccountId.setText(accountId);}
        else{txtaccountId.setVisibility(View.GONE);}

       if( registeredEmail!= "Empty")
        {txtregisteredEmail.setText(registeredEmail);}
        else{txtregisteredEmail.setVisibility(View.GONE);}

       if( password       != "Empty")
        {txtpassword.setText(password);}
        else{txtpassword.setVisibility(View.GONE);}

       if( generalField1  != "Empty")
        {txtgeneralField1.setText(generalField1);}
        else{txtgeneralField1.setVisibility(View.GONE);}

        if( generalField2  != "Empty")
        {txtgeneralField2.setText(generalField1);}
        else{txtgeneralField2.setVisibility(View.GONE);}

       if( note           != "Empty")
        {txtnote.setText(note);}
        else{txtnote.setVisibility(View.GONE);}

       if( itMail         != "Empty")
        {txtitMail.setText(itMail);}
        else{txtitMail.setVisibility(View.GONE);}

       if( expireDate     != "Empty")
        {txtexpireDate.setText(expireDate);}
        else{txtexpireDate.setVisibility(View.GONE);}

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(PopupActivity.this,ShowConnection.class);
        startActivity(intent);
    }
}
