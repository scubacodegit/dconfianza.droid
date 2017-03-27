package com.scubacode.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.scubacode.dconfianza.R;
import com.scubacode.library.ui.ActivityBase;
import com.scubacode.library.utility.Email;
import com.scubacode.model.User;
import com.scubacode.library.io.ApiAdapter;
import com.scubacode.library.security.UserSessionManager;

import com.scubacode.library.utility.Encryption;
import com.scubacode.library.utility.HttpHelper;
import com.scubacode.library.utility.StringHelper;
import com.scubacode.dconfianza.MainActivity;

import static com.scubacode.library.io.ApiConstants.TERMS_URL;

/**
 * Created by htorres on 12/07/2016.
 */
public class CreateAccountActivity extends ActivityBase
{
    //Form Fields
    private TextInputEditText firstName=null;
    private TextInputEditText lastName=null;
    private TextInputEditText password=null;
    private TextInputEditText confirmPassword=null;
    private TextInputEditText email=null;
    private CheckBox terms=null;

    private TextInputLayout firstNameInputLayout= null;
    private TextInputLayout lastNameInputLayout= null;
    private TextInputLayout emailInputLayout= null;
    private TextInputLayout passwordInputLayout= null;
    private TextInputLayout confirmPasswordInputLayout= null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_account);

            firstName = (TextInputEditText) findViewById(R.id.edit_text_first_name);
            lastName = (TextInputEditText) findViewById(R.id.edit_text_last_name);
            email = (TextInputEditText) findViewById(R.id.edit_text_email);
            password = (TextInputEditText) findViewById(R.id.edit_text_password);
            confirmPassword = (TextInputEditText) findViewById(R.id.edit_text_confirm_password);
            terms = (CheckBox) findViewById(R.id.checkbox_disclaimer);

            firstNameInputLayout = (TextInputLayout) findViewById(R.id.input_layout_first_name);
            lastNameInputLayout = (TextInputLayout) findViewById(R.id.input_layout_last_name);
            emailInputLayout = (TextInputLayout) findViewById(R.id.input_layout_email);
            passwordInputLayout = (TextInputLayout) findViewById(R.id.input_layout_password);
            confirmPasswordInputLayout = (TextInputLayout) findViewById(R.id.input_layout_confirm_password);

            final TextView txt_viewterms = (TextView) findViewById(R.id.txt_view_terms);
            txt_viewterms.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(TERMS_URL));
                    startActivity(browserIntent);
                }
            }
            );

            firstName.addTextChangedListener(new TextWatcher()
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

            lastName.addTextChangedListener(new TextWatcher()
            {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    try
                    {
                        lastNameInputLayout.setErrorEnabled(false);
                        lastNameInputLayout.setError(null);
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

            password.addTextChangedListener(new TextWatcher()
            {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    try
                    {
                        passwordInputLayout.setErrorEnabled(false);
                        passwordInputLayout.setError(null);
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

            confirmPassword.addTextChangedListener(new TextWatcher()
            {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    try
                    {
                        confirmPasswordInputLayout.setErrorEnabled(false);
                        confirmPasswordInputLayout.setError(null);
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

            final Button registerButton = (Button) findViewById(R.id.button_register);
            registerButton.setOnClickListener(new View.OnClickListener()
            {

                public void onClick(View v)
                {
                    //validar campos
                    if ( StringHelper.isNullOrEmpty(firstName.getText().toString()))
                    {
                        firstNameInputLayout.setErrorEnabled(true);
                        firstNameInputLayout.setError(getString(R.string.misc_required_field));
                        firstName.requestFocus();
                        return;
                    }

                    if ( StringHelper.isNullOrEmpty(lastName.getText().toString()))
                    {
                        lastNameInputLayout.setErrorEnabled(true);
                        lastNameInputLayout.setError(getString(R.string.misc_required_field));
                        lastName.requestFocus();
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

                    if (StringHelper.isNullOrEmpty(password.getText().toString()))
                    {
                        passwordInputLayout.setErrorEnabled(true);
                        passwordInputLayout.setError(getString(R.string.misc_required_field));
                        password.requestFocus();
                        return;
                    }

                    if (StringHelper.isNullOrEmpty(confirmPassword.getText().toString()))
                    {
                        confirmPasswordInputLayout.setErrorEnabled(true);
                        confirmPasswordInputLayout.setError(getString(R.string.misc_required_field));
                        confirmPassword.requestFocus();
                        return;
                    }

                    if (!(password.getText().toString().equals(confirmPassword.getText().toString())))
                    {
                        reportTransient(getString(R.string.reg_password_mismatch),MessageType.Error);
                        password.requestFocus();
                        return;
                    }

                    if (!terms.isChecked())
                    {
                        reportTransient(getString(R.string.terms_unchecked),MessageType.Error);
                        return;
                    }

                    final ProgressDialog dialog = ProgressDialog.show(CreateAccountActivity.this,
                            getString(R.string.app_name),
                            getString(R.string.misc_please_wait));

                    //verificar si el email existe
                    Call<User> call = ApiAdapter.getApiService().getUserByEmail(email.getText().toString());
                    call.enqueue(new Callback<User>()
                    {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response)
                        {
                            try
                            {
                                dialog.cancel();
                                //si el email no existe entonces registrarlo
                                if(response.code()== HttpHelper.HttpStatus.NotFound.getValue())
                                {
                                    registerUser(firstName.getText().toString(),lastName.getText().toString(),email.getText().toString(),password.getText().toString());
                                }
                                else
                                {
                                    reportTransient(getString(R.string.reg_user_exists));
                                }
                            }
                            catch(Exception ex)
                            {
                                handleException(ex, true);
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t)
                        {
                            dialog.cancel();
                            handleException(t.getMessage(),true);
                        }
                    });
                }
            });
        }
        catch(Exception ex)
        {
            handleException(ex,true);
        }

    }

    private void registerUser(String firstName, String lastName, String email, String password) throws Exception
    {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(Encryption.EncryptToByteArray(password));

        Call<User> call = ApiAdapter.getApiService().createUser(user);
        call.enqueue(new Callback<User>()
        {
            @Override
            public void onResponse(Call<User> call, Response<User> response)
            {
                try
                {
                    UserSessionManager session= UserSessionManager.getSessionInstnce(getBaseContext());
                    session.setUser(response.body());

                    //ir al main
                    Intent intenet = new Intent().setClass(getBaseContext(), MainActivity.class);
                    startActivity(intenet);
                }
                catch(Exception ex)
                {
                    handleException(ex, true);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t)
            {
                handleException(t.getMessage(),true);
            }

        });

    }


}
