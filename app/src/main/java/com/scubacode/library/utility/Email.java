package com.scubacode.library.utility;

/**
 * Created by htorres on 20/09/2016.
 */
public class Email
{

    public static Boolean isValid(String email)
    {
       return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

}
