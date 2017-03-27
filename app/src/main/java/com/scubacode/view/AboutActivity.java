package com.scubacode.view;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.scubacode.dconfianza.R;
import com.scubacode.library.ui.ActivityBase;


/**
 * Created by htorres on 28/09/2016.
 */
public class AboutActivity extends ActivityBase
{
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_about);
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            final TextView email = (TextView) findViewById(R.id.email_textview);
            final TextView version= (TextView) findViewById(R.id.version);
            version.setText(String.format("%1$s %2$s",getString(R.string.misc_about_text1), pInfo.versionName));
            String text = String.format("<a href='#'>%1$s</a>",email.getText());
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
