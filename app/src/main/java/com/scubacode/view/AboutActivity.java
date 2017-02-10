package com.scubacode.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;


import com.scubacode.dconfianza.R;
import com.scubacode.library.ui.ActivityFormBase;
import com.scubacode.library.ui.ActivityFragmentBase;

import java.util.List;

/**
 * Created by htorres on 28/09/2016.
 */
public class AboutActivity extends ActivityFormBase
{
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_about);
            final TextView email = (TextView) findViewById(R.id.email_textview);
            String text = "<a href='#'> "+ email.getText() +"</a>";
            email.setText(Html.fromHtml(text));
            email.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {email.getText().toString()});
                    intent.putExtra(android.content.Intent.EXTRA_SUBJECT,getString(R.string.main_email_subject));
                    startActivity(Intent.createChooser(intent,"Send"));

                }

            });

        }
        catch(Exception ex)
        {
            handleException(ex,true);
        }

    }
}
