package com.example.camcustomeraccessmethod.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.camcustomeraccessmethod.Models.ConnectionModel;
import com.example.camcustomeraccessmethod.NewConnection;

public class DataBaseHelper extends SQLiteOpenHelper
{

    public static final String COLUMN_FACILITY_NAME         = "Facility_Name";
    public static final String COLUMN_KIND_OF_VPN           = "Kind_of_Vpn";
    public static final String COLUMN_TOKEN_APP             = "Token_App";
    public static final String COLUMN_ID                    = "Id";
    public static final String COLUMN_USER_NAME             = "User_Name";
    public static final String COLUMN_ACCOUNT_ID            = "Account_Id";
    public static final String COLUMN_REGISTERED_EMAIL      = "Registered_Email";
    public static final String COLUMN_PASSWORD              = "Password";
    public static final String COLUMN_GENERAL_FIELD_1       = "General_Field_1";
    public static final String COLUMN_GENERAL_FIELD_2       = "General_Field_2";
    public static final String COLUMN_NOTE                  = "Note";
    public static final String COLUMN_EMAIL_IT              = "Email_It";
    public static final String COLUMN_EXPIRE_DATE           = "Expire_Date";
    public static final String COLUMN_ADV_EXPIRE_DATE       = "Adv_Expire_Date";
    public static final String TABLE_CONNECTION             = "Connection";

    public DataBaseHelper(@Nullable Context context)
    {
        super(context, "CAMCustomerAccessMethod.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
      String CreateTableConnection = "CREATE TABLE IF NOT EXISTS " + TABLE_CONNECTION + "("
                                   + COLUMN_ID                     + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                   + COLUMN_FACILITY_NAME          + " TEXT, "
                                   + COLUMN_KIND_OF_VPN            + " TEXT, "
                                   + COLUMN_TOKEN_APP              + " TEXT, "
                                   + COLUMN_USER_NAME              + " TEXT, "
                                   + COLUMN_ACCOUNT_ID             + " TEXT, "
                                   + COLUMN_REGISTERED_EMAIL       + " TEXT, "
                                   + COLUMN_PASSWORD               + " TEXT, "
                                   + COLUMN_GENERAL_FIELD_1        + " TEXT, "
                                   + COLUMN_GENERAL_FIELD_2        + " TEXT, "
                                   + COLUMN_NOTE                   + " TEXT, "
                                   + COLUMN_EMAIL_IT               + " TEXT, "
                                   + COLUMN_EXPIRE_DATE            + " TEXT, "
                                   + COLUMN_ADV_EXPIRE_DATE        + " TEXT )";
      sqLiteDatabase.execSQL(CreateTableConnection);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }

    public DbAnswerManager addNewConnection(ConnectionModel connectionModel)
    {
        DbAnswerManager dbAnswerManager = new DbAnswerManager();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FACILITY_NAME, connectionModel.getFacilityName());
        cv.put(COLUMN_KIND_OF_VPN,   connectionModel.getKindOfVpn());
        cv.put(COLUMN_TOKEN_APP,     connectionModel.getTokenAppAssociated());
        cv.put(COLUMN_USER_NAME,     connectionModel.getUserName());
        cv.put(COLUMN_ACCOUNT_ID,    connectionModel.getAccountId());
        cv.put(COLUMN_REGISTERED_EMAIL, connectionModel.getRegisteredEmail());
        cv.put(COLUMN_PASSWORD,         connectionModel.getPassword());
        cv.put(COLUMN_GENERAL_FIELD_1,  connectionModel.getGeneralField1());
        cv.put(COLUMN_GENERAL_FIELD_2,  connectionModel.getGetGeneralField2());
        cv.put(COLUMN_NOTE,             connectionModel.getNote());
        cv.put(COLUMN_EMAIL_IT,         connectionModel.getItEmail());
        cv.put(COLUMN_EXPIRE_DATE,      connectionModel.getExpireDate().toString());
        cv.put(COLUMN_ADV_EXPIRE_DATE,  connectionModel.getExpireDateAdvise());
        long insertResult=0;
        if(!CheckIfConnectionExist(TABLE_CONNECTION,"Facility_Name","Kind_of_Vpn",connectionModel.getFacilityName(),
                                    connectionModel.getKindOfVpn()))
        {
            SQLiteDatabase db = this.getWritableDatabase();
            insertResult = db.insert(TABLE_CONNECTION, null, cv);
            db.close();
        }
        else
            {
                dbAnswerManager.setAnswer("Field already exist");
                dbAnswerManager.setResult(false);
              return dbAnswerManager;
            }


        if(insertResult == -1)
        {
            dbAnswerManager.setAnswer("Query failed");
            dbAnswerManager.setResult(false);
            return dbAnswerManager;
        }

        else
            {
                dbAnswerManager.setAnswer("New connection successfully saved");
                dbAnswerManager.setResult(true);
                return dbAnswerManager;
            }
    }

    public boolean CheckIfConnectionExist(String tableName,
                                          String column,
                                          String column2,
                                          String facilityName,
                                          String kindOfVpn )
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        String sql ="SELECT * FROM '"+tableName+"' WHERE ('"+column+"'='"
                +facilityName+"' AND '"
                +column2+"'='"
                +kindOfVpn+"');";


        cursor= db.rawQuery(sql,null);

        if(cursor.getCount()>0)
        {
            cursor.close();
            db.close();
            return true;

        }
        else
        {
            cursor.close();
            db.close();
            return false;
        }

    }
}
