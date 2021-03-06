package com.scubacode.library.ui;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.scubacode.library.security.UserSessionManager;
import com.scubacode.dconfianza.R;
import com.scubacode.dconfianza.ContactActivity;
import com.scubacode.view.AboutActivity;

/**
 * Created by htorres on 12/07/2016.
 */
public class ActivityBase extends AppCompatActivity
{
    private Boolean showmenu=false;
    public Boolean getShowmenu() {
        return showmenu;
    }

    public void showToolbar(String tittle, boolean upButton){
        this.showmenu=true;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (tittle!=null)
            getSupportActionBar().setTitle(tittle);
        if(upButton)
            getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

        getSupportActionBar().setIcon(R.drawable.toolbar_logo);
    }

    public void setShowmenu(Boolean showmenu) {
        this.showmenu = showmenu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(this.showmenu)
        {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try
        {
            switch (item.getItemId()) {
                case android.R.id.home:
                    String parent= NavUtils.getParentActivityName(this);
                    Intent intent = new Intent().setClass(getBaseContext(), Class.forName(parent));
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.Logout:
                    UserSessionManager session =UserSessionManager.getSessionInstnce(getApplicationContext());
                    session.logoutUser();
                    break;
                case R.id.ContactUs:
                    Intent intentContactUs = new Intent().setClass(getApplicationContext(), ContactActivity.class);
                    startActivity(intentContactUs);
                    break;
                case R.id.About:
                    Intent intentAbout = new Intent().setClass(getApplicationContext(), AboutActivity.class);
                    startActivity(intentAbout);
                    break;
                default:
                    break;
            }

        }
        catch(Exception ex)
        {
            handleException(ex);
        }


        return super.onOptionsItemSelected(item);
    }

    public enum MessageType
    {
        Asterisk,Error,Exclamation,Hand,Information,None,Question,Stop,Warning
    }

    public void reportTransient(String message) {
        reportTransient(null, message);
    }

    public void reportTransient(String message, MessageType messageType)
    {
        reportTransient(null,message,messageType,getApplicationContext());
    }

    public void reportTransient(String tag, String message) {
        reportTransient(tag, message, null,getApplicationContext());
    }

    public static void reportTransient(String message, MessageType messageType, Context context)
    {
        reportTransient(null, message, messageType, context);
    }

    public static void reportTransient(String tag, String message, MessageType messageType, Context context)
    {
        String s = !TextUtils.isEmpty(tag) ? String.format("%1$s : %2$s", tag, message) : message;
        Toast toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        toast.show();
    }

    public AlertDialog alertBox(String title,
                                String message,
                                String positiveButton,
                                String negativeButton,
                                DialogInterface.OnClickListener positiveListener,
                                DialogInterface.OnClickListener negativeListener,
                                MessageType messageType)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        if (positiveButton!= null) builder.setPositiveButton(positiveButton, positiveListener!=null ? positiveListener : null);
        if (negativeButton!= null) builder.setNegativeButton(negativeButton, negativeListener!=null ? negativeListener : null);
        return builder.create();
    }
    public AlertDialog alertBox(String message,
                                String positiveButton,
                                DialogInterface.OnClickListener positiveListener)
    {
        return alertBox(null,message,positiveButton,null,positiveListener,null,MessageType.Information);
    }
    public AlertDialog alertBox(String title,String message,String positiveButton,MessageType messageType)
    {
        return alertBox(title, message, positiveButton, null, null, null, messageType);
    }

    public void handleException(Exception ex, Boolean show)
    {
        if (show) reportTransient(ex.getMessage(),MessageType.Error);
        handleException(ex);
    }

    public void handleException(String ex, Boolean show)
    {
        if (show) reportTransient(ex,MessageType.Error);
        handleException(ex);
    }

    public static void handleException(Exception ex, Boolean show, Context context)
    {
        if (show) reportTransient(ex.getMessage(),MessageType.Error,context);
        handleException(ex);
    }

    public static void handleException(Exception ex)
    {
        String msg = ex.getMessage();
    }

    public static void handleException(String ex)
    {
        int x=0;
    }

    public static void handleException(String ex, Boolean show, Context context)
    {
        if (show) reportTransient(ex,MessageType.Error,context);
        handleException(ex);
    }

}
