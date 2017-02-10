package com.scubacode.library.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by htorres on 14/08/2016.
 */
public class ActivityFragmentBase extends Fragment
{
    public enum MessageType
    {
        Asterisk,Error,Exclamation,Hand,Information,None,Question,Stop,Warning
    }

    public void reportTransient(String message) {
        reportTransient(null, message);
    }

    public void reportTransient(String message, MessageType messageType)
    {
        reportTransient(null,message,messageType, getContext());
    }

    public void reportTransient(String tag, String message) {
        reportTransient(tag, message, null,getContext());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        int x=0;
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
