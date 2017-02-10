package com.scubacode.library.utility;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by htorres on 12/07/2016.
 */
public class StringHelper {

    public static String getValueFromResourceCode(String code, Context context)
    {
        int i = context.getResources().getIdentifier(code, "string", context.getPackageName());
        return  context.getString(i);
    }



    public static boolean isNullOrEmpty(String s)
    {
        if(s == null)  return true;
        if (s.trim().length() == 0 ) return true;
        return false;
    }

}
