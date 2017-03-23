package com.scubacode.library.utility;

/**
 * Created by htorres on 13/07/2016.
 */
public class HttpHelper {

    public enum HttpStatus
    {
        NotFound(404),OK(200);
        private final int value;
        HttpStatus(int value)
        {
            this.value=value;
        }

        public int getValue() {
            return value;
        }
    }

}
