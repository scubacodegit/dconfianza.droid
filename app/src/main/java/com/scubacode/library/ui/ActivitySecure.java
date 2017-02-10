package com.scubacode.library.ui;

import android.content.Intent;
import android.os.Bundle;

import com.scubacode.library.security.UserSessionManager;
import com.scubacode.LoginActivity;

/**
 * Created by htorres on 17/02/2016.
 */
public class ActivitySecure extends ActivityFormBase
{
    protected UserSessionManager session;
    protected Boolean userIsLoggedIn=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            session= UserSessionManager.getSessionInstnce(getBaseContext());
            if(this.session.getUser()!=null)
            {
                userIsLoggedIn=true;

            }
        }
        catch(Exception ex)
        {
            handleException(ex,true);
        }

    }

    protected void LoginRedirect()
    {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
