package com.wdsm.fitgain.Entities;


public class User {
    private static User instance;
    private String eMail;
    private String login;

    public static User getInstance() {
        if (instance == null){
            instance = new User();
        }
        return instance;
    }

    public String getEMail() {
        return eMail;
    }

    public String getLogin() {
        return login;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
