package com.scubacode;
/*
Created by
Horacio Torres
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.scubacode.dconfianza.R;
import com.scubacode.model.Credentials;
import com.scubacode.model.User;
import com.scubacode.library.io.ApiAdapter;
import com.scubacode.library.security.UserSessionManager;
import com.scubacode.library.ui.ActivityFormBase;
import com.scubacode.library.utility.Email;
import com.scubacode.library.utility.Encryption;
import com.scubacode.library.utility.HttpHelper;
import com.scubacode.library.utility.StringHelper;
import com.scubacode.view.CreateAccountActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends ActivityFormBase
{
    private TextInputLayout emailInputLayout=null;
    private TextInputEditText email=null;
    private TextInputEditText password=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button login= (Button) findViewById(R.id.login);
        final TextView createHere= (TextView) findViewById(R.id.createHere);
        emailInputLayout= (TextInputLayout) findViewById(R.id.emailInputLayout);
        final TextInputEditText email= (TextInputEditText) findViewById(R.id.email);
        final TextInputEditText password= (TextInputEditText) findViewById(R.id.password);

        email.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                try
                {
                    emailInputLayout.setErrorEnabled(false);
                    emailInputLayout.setError(null);
                }
                catch(Exception ex)
                {
                    handleException(ex,true);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }

        });

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                try
                {
                    if ( StringHelper.isNullOrEmpty(email.getText().toString()) || !Email.isValid(email.getText().toString()))
                    {
                        emailInputLayout.setErrorEnabled(true);
                        emailInputLayout.setError(getString(R.string.reg_invalid_email));
                        return;
                    }

                    final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this,
                            StringHelper.getValueFromResourceCode("app_name", getBaseContext()),
                            StringHelper.getValueFromResourceCode("misc_please_wait", getBaseContext()));

                    Credentials credentials = new Credentials();
                    credentials.setEmail(email.getText().toString());
                    credentials.setPassword(Encryption.EncryptToByteArray(password.getText().toString()));

                    Call<User> call = ApiAdapter.getApiService().login(credentials);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response)
                        {
                            dialog.cancel();
                            if(response.code()==HttpHelper.HttpStatus.NotFound.getValue())
                            {
                                reportTransient(StringHelper.getValueFromResourceCode("reg_access_denied", getBaseContext()));
                            }
                            else
                            {
                                UserSessionManager session =  UserSessionManager.getSessionInstnce(getApplicationContext());
                                session.setUser(response.body());
                                Intent intenet = new Intent().setClass(getBaseContext(), com.scubacode.dconfianza.MainActivity.class);
                                startActivity(intenet);
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t)
                        {
                            dialog.cancel();
                            if (t.hashCode()== HttpHelper.HttpStatus.NotFound.getValue())
                                reportTransient(StringHelper.getValueFromResourceCode("reg_access_denied", getBaseContext()));
                            else
                                handleException(t.getMessage(),true);
                        }
                    });

                }
                catch(Exception ex)
                {
                    handleException(ex,true);
                }
            }
        });

        createHere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent().setClass(LoginActivity.this, CreateAccountActivity.class);
                startActivity(mainIntent);
            }
        });
    }

}
