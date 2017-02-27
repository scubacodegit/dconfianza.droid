package com.scubacode.library.utility;

/**
 * Created by htorres on 12/07/2016.
 */
public class StringHelper {

    public static boolean isNullOrEmpty(String s)
    {
        if(s == null)  return true;
        if (s.trim().length() == 0 ) return true;
        return false;
    }

}
