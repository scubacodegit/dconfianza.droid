package com.scubacode.dconfianza;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.scubacode.dconfianza.R;
import com.scubacode.library.utility.Email;
import com.scubacode.model.ContactMessage;
import com.scubacode.library.io.ApiAdapter;
import com.scubacode.library.ui.ActivitySecure;
import com.scubacode.library.utility.StringHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htorres on 05/09/2016.
 */
public class ContactActivity extends ActivitySecure
{

    //Form Fields
    private TextInputEditText name=null;
    private TextInputEditText email=null;
    private TextInputEditText message=null;

    private TextInputLayout firstNameInputLayout = null;
    private TextInputLayout emailInputLayout = null;
    private TextInputLayout messageInputLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            if(!userIsLoggedIn)
            {
                finish();
                this.LoginRedirect();
                return;
            }
            setContentView(R.layout.activity_contact);

            final Button submit = (Button) findViewById(R.id.button_submit);

            name = (TextInputEditText) findViewById(R.id.input_edit_text_first_name);
            email= (TextInputEditText) findViewById(R.id.input_edit_text_email);
            message= (TextInputEditText) findViewById(R.id.input_edit_text_message);

            firstNameInputLayout = (TextInputLayout) findViewById(R.id.input_layout_first_name);
            emailInputLayout = (TextInputLayout) findViewById(R.id.input_layout_email);
            messageInputLayout = (TextInputLayout) findViewById(R.id.input_layout_message);

            name.addTextChangedListener(new TextWatcher()
            {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    try
                    {
                        firstNameInputLayout.setErrorEnabled(false);
                        firstNameInputLayout.setError(null);
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

            message.addTextChangedListener(new TextWatcher()
            {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    try
                    {
                        messageInputLayout.setErrorEnabled(false);
                        messageInputLayout.setError(null);
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

            submit.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    try
                    {

                        //validate

                        if ( StringHelper.isNullOrEmpty(name.getText().toString()))
                        {
                            firstNameInputLayout.setErrorEnabled(true);
                            firstNameInputLayout.setError(getString(R.string.misc_required_field));
                            name.requestFocus();
                            return;
                        }

                        if (StringHelper.isNullOrEmpty(email.getText().toString()))
                        {
                            emailInputLayout.setErrorEnabled(true);
                            emailInputLayout.setError(getString(R.string.misc_required_field));
                            email.requestFocus();
                            return;
                        }

                        if (!Email.isValid(email.getText().toString()) )
                        {
                            emailInputLayout.setErrorEnabled(true);
                            emailInputLayout.setError(getString(R.string.reg_invalid_email));
                            email.requestFocus();
                            return;
                        }

                        if ( StringHelper.isNullOrEmpty(message.getText().toString()))
                        {
                            messageInputLayout.setErrorEnabled(true);
                            messageInputLayout.setError(getString(R.string.misc_required_field));
                            name.requestFocus();
                            return;
                        }


                        final ProgressDialog waitDialog = ProgressDialog.show(ContactActivity.this,
                                getString(R.string.app_name),
                                getString(R.string.misc_please_wait));

                        ContactMessage contactMessage = new ContactMessage();
                        contactMessage.setUserID(session.getUser().getUserID());
                        contactMessage.setEmail(email.getText().toString());
                        contactMessage.setMessage(message.getText().toString());

                        Call<Integer> call = ApiAdapter.getApiService().addMessage(contactMessage);
                        call.enqueue(new Callback<Integer>()
                        {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response)
                            {
                                waitDialog.cancel();

                                AlertDialog dialog = alertBox(
                                        getString(R.string.main_message_received),
                                        getString(R.string.misc_ok),
                                        new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(DialogInterface dialog, int which) {
                                                try
                                                {
                                                    dialog.dismiss();
                                                    //limpiar forma
                                                    refreshForm();
                                                }
                                                catch(Exception ex)
                                                {
                                                    handleException(ex,true);
                                                }
                                            }
                                        }
                                );
                                dialog.show();
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t)
                            {
                                waitDialog.cancel();
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

        }
        catch(Exception ex)
        {
            handleException(ex,true);
        }
    }

    private void refreshForm()
    {
        name.setText("");
        email.setText("");
        message.setText("");
        name.requestFocus();
    }
}
