package com.beatutify.util;

import android.content.Context;

import com.beatutify.model.Customer;
import com.google.gson.Gson;

/**
 * Created by gaurav.singh on 3/12/2018.
 */

public class AppGlobalData {
    private static final AppGlobalData ourInstance = new AppGlobalData();
    private boolean isGuest = false;

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public static AppGlobalData getInstance() {
        return ourInstance;
    }

    private AppGlobalData() {
    }

    private Customer loggedInCustomer;
    private String token = "";
    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public void setLoggedInCustomer(Customer loggedInCustomer) {
        this.loggedInCustomer = loggedInCustomer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Clear all the login data after user press logout.
     * @param context
     */
    public void logOut(Context context)
    {
      //  LoginManager.getInstance().logOut();
        Prefs.clearAll(context);
    }

    /**
     * Set login data via Preference.
     * @param context
     * @return
     */
    public boolean setFromPrefs(Context context)
    {
        loggedInCustomer=null;
        token=null;
        isGuest = false;
        String customerData =  Prefs.getString(context, AppConstants.PREF.LOGGED_IN_CUSTOMER_DATA,null);
        token =  Prefs.getString(context, AppConstants.PREF.CUSTOMER_TOKEN,null);
        isGuest = Prefs.getBoolean(context, AppConstants.PREF.IS_GUEST, false);
        if (customerData!=null)
            loggedInCustomer=new Gson().fromJson(customerData,Customer.class);

        return  loggedInCustomer != null && token != null && !isGuest;
    }

    /**
     * Save login data to prefs.
     * @param context
     */
    public void saveToPrefs(Context context) {
        if (loggedInCustomer!=null)
        Prefs.saveString(context, AppConstants.PREF.LOGGED_IN_CUSTOMER_DATA,new Gson().toJson(loggedInCustomer));
        Prefs.saveBoolean(context, AppConstants.PREF.IS_GUEST, isGuest);
        if (token!=null)
            Prefs.saveString(context, AppConstants.PREF.CUSTOMER_TOKEN,token);

    }
}
