package com.example.camcustomeraccessmethod.Models;

import java.util.Date;

public class ConnectionModel
{
    private int id;
    private String factoryName;
    private String kindOfVpn;
    private String tokenAppAssociated;
    private String userName;
    private String accountId;
    private String registeredEmail;
    private String password;
    private String generalField1;
    private String getGeneralField2;
    private String note;
    private String itEmail;
    private Date expireDate;
    private Boolean expireDateAdvise;

    public ConnectionModel(int id, String factoryName, String kindOfVpn, String tokenAppAssociated, String userName, String accountId, String registeredEmail, String password, String generalField1, String getGeneralField2, String note, String itEmail, Date expireDate, Boolean expireDateAdvise)
    {
        this.id = id;
        this.factoryName = factoryName;
        this.kindOfVpn = kindOfVpn;
        this.tokenAppAssociated = tokenAppAssociated;
        this.userName = userName;
        this.accountId = accountId;
        this.registeredEmail = registeredEmail;
        this.password = password;
        this.generalField1 = generalField1;
        this.getGeneralField2 = getGeneralField2;
        this.note = note;
        this.itEmail = itEmail;
        this.expireDate = expireDate;
        this.expireDateAdvise = expireDateAdvise;
    }

    public ConnectionModel()
    {
    }

    @Override
    public String toString()
    {
        return "ConnectionModel{" +
                "id=" + id +
                ", factoryName='" + factoryName + '\'' +
                ", kindOfVpn='" + kindOfVpn + '\'' +
                ", tokenAppAssociated='" + tokenAppAssociated + '\'' +
                ", userName='" + userName + '\'' +
                ", accountId='" + accountId + '\'' +
                ", registeredEmail='" + registeredEmail + '\'' +
                ", password='" + password + '\'' +
                ", generalField1='" + generalField1 + '\'' +
                ", getGeneralField2='" + getGeneralField2 + '\'' +
                ", note='" + note + '\'' +
                ", itEmail='" + itEmail + '\'' +
                ", expireDate=" + expireDate +
                ", expireDateAdvise=" + expireDateAdvise +
                '}';
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFactoryName()
    {
        return factoryName;
    }

    public void setFactoryName(String factoryName)
    {
        this.factoryName = factoryName;
    }

    public String getKindOfVpn()
    {
        return kindOfVpn;
    }

    public void setKindOfVpn(String kindOfVpn)
    {
        this.kindOfVpn = kindOfVpn;
    }

    public String getTokenAppAssociated()
    {
        return tokenAppAssociated;
    }

    public void setTokenAppAssociated(String tokenAppAssociated)
    {
        this.tokenAppAssociated = tokenAppAssociated;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public String getRegisteredEmail()
    {
        return registeredEmail;
    }

    public void setRegisteredEmail(String registeredEmail)
    {
        this.registeredEmail = registeredEmail;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getGeneralField1()
    {
        return generalField1;
    }

    public void setGeneralField1(String generalField1)
    {
        this.generalField1 = generalField1;
    }

    public String getGetGeneralField2()
    {
        return getGeneralField2;
    }

    public void setGetGeneralField2(String getGeneralField2)
    {
        this.getGeneralField2 = getGeneralField2;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getItEmail()
    {
        return itEmail;
    }

    public void setItEmail(String itEmail)
    {
        this.itEmail = itEmail;
    }

    public Date getExpireDate()
    {
        return expireDate;
    }

    public void setExpireDate(Date expireDate)
    {
        this.expireDate = expireDate;
    }

    public Boolean getExpireDateAdvise()
    {
        return expireDateAdvise;
    }

    public void setExpireDateAdvise(Boolean expireDateAdvise)
    {
        this.expireDateAdvise = expireDateAdvise;
    }


}
