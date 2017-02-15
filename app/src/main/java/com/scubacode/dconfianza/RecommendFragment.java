package com.scubacode.dconfianza;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.scubacode.dconfianza.R;

import com.scubacode.library.ui.ActivityFragmentBase;
import com.scubacode.library.utility.Email;
import com.scubacode.model.Location;
import com.scubacode.model.Service;
import com.scubacode.model.Worker;
import com.scubacode.library.io.ApiAdapter;
import com.scubacode.library.ui.ActivityBase;
import com.scubacode.library.ui.ActivityFragmentFormBase;
import com.scubacode.library.utility.StringHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htorres on 24/07/2016.
 */
public class RecommendFragment extends ActivityFragmentBase
{

    //Form Fields
    private TextInputEditText firstName=null;
    private TextInputEditText lastName=null;
    private TextInputEditText mobilePhone=null;
    private TextInputEditText workPhone=null;
    private TextInputEditText email=null;
    private Spinner spinnerLocation=null;
    private Spinner spinnerService=null;

    private TextInputLayout firstNameInputLayout= null;
    private TextInputLayout lastNameInputLayout= null;
    private TextInputLayout emailInputLayout= null;

    private View myFragmentView;
    private int userID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        try
        {
            userID = getArguments().getInt("userID", 0);

            if(userID==0)
                throw new Exception(getString(R.string.misc_session_error));

            myFragmentView =  inflater.inflate(R.layout.activity_recommend, container, false);

            firstName = (TextInputEditText) myFragmentView.findViewById(R.id.input_edit_text_first_name);
            lastName = (TextInputEditText) myFragmentView.findViewById(R.id.input_edit_text_last_name);
            mobilePhone = (TextInputEditText) myFragmentView.findViewById(R.id.input_edit_text_mobile_phone);
            workPhone = (TextInputEditText) myFragmentView.findViewById(R.id.input_edit_text_work_phone);
            email = (TextInputEditText) myFragmentView.findViewById(R.id.input_edit_text_email);

            firstNameInputLayout = (TextInputLayout) myFragmentView.findViewById(R.id.input_layout_first_name);
            lastNameInputLayout = (TextInputLayout) myFragmentView.findViewById(R.id.input_layout_last_name);
            emailInputLayout = (TextInputLayout) myFragmentView.findViewById(R.id.input_layout_email);

            //spinner location binding
            spinnerLocation  = (Spinner) myFragmentView.findViewById(R.id.spinner_location);
            Call<List<Location>> call = ApiAdapter.getApiService().getLocations();
            call.enqueue(new Callback<List<Location>>()
            {
                @Override
                public void onResponse(Call<List<Location>> call, Response<List<Location>> response)
                {
                    try
                    {
                        spinnerLocationOnResponse(spinnerLocation, R.layout.support_simple_spinner_dropdown_item , response.body());
                    }
                    catch(Exception ex)
                    {
                        ActivityBase.handleException(ex,true, getContext());
                    }
                }

                @Override
                public void onFailure(Call<List<Location>> call, Throwable t)
                {
                    ActivityBase.handleException(t.getMessage(),true, getContext());
                }

            });

            //spinner service binding
            spinnerService  = (Spinner) myFragmentView.findViewById(R.id.spinner_service);
            Call<List<Service>> callService = ApiAdapter.getApiService().getServices();
            callService.enqueue(new Callback<List<Service>>()
            {
                @Override
                public void onResponse(Call<List<Service>> call, Response<List<Service>> response)
                {
                    try
                    {
                        spinnerServiceOnResponse(spinnerService, R.layout.support_simple_spinner_dropdown_item , response.body());
                    }
                    catch(Exception ex)
                    {
                        ActivityBase.handleException(ex,true, getContext());
                    }
                }

                @Override
                public void onFailure(Call<List<Service>> call, Throwable t)
                {
                    ActivityBase.handleException(t.getMessage(),true, getContext());
                }

            });

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

            Button button_add_worker = (Button) myFragmentView.findViewById(R.id.button_add_worker);
            button_add_worker.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {

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

                    if(StringHelper.isNullOrEmpty(mobilePhone.getText().toString()) && StringHelper.isNullOrEmpty(workPhone.getText().toString()) )
                    {
                        reportTransient(getString(R.string.main_phone_validation), MessageType.Error);
                        return;
                    }


                    if ( !StringHelper.isNullOrEmpty(email.getText().toString()))
                    {
                        if (!Email.isValid(email.getText().toString()) )
                        {
                            emailInputLayout.setErrorEnabled(true);
                            emailInputLayout.setError(getString(R.string.reg_invalid_email));
                            email.requestFocus();
                            return;
                        }
                    }

                    int locationID = ((Location)spinnerLocation.getSelectedItem()).getID();
                    if(locationID==0)
                    {
                        reportTransient(getString(R.string.main_location_prompt), MessageType.Error);
                        return;
                    }

                    int serviceID = ((Service)spinnerService.getSelectedItem()).getID();
                    if(serviceID==0)
                    {
                        reportTransient(getString(R.string.main_service_prompt), MessageType.Error);
                        return;
                    }

                    saveRecord(firstName.getText().toString(),
                            lastName.getText().toString(),
                            mobilePhone.getText().toString(),
                            workPhone.getText().toString(),
                            email.getText().toString(),
                            locationID,
                            serviceID,
                            userID);
                }

            });


        }
        catch(Exception ex)
        {
            ActivityBase.handleException(ex,true, getContext());
        }

        return myFragmentView;
    }

    //Pupula el dropdown locations
    private void spinnerLocationOnResponse(Spinner spinner, int textViewResourceId, List<Location> values)
    {
        Location location = new Location();
        location.setID(0);
        location.setName(getString(R.string.main_location_prompt));
        values.add(location);

        spinner.setAdapter(new LocationSpinnerAdapter(spinner.getContext(), textViewResourceId ,values));
        spinner.setSelection(values.size()-1);
    }

    //popula el dropdown services
    private void spinnerServiceOnResponse(Spinner spinner, int textViewResourceId, List<Service> values)
    {
        Service service = new Service();
        service.setID(0);
        service.setName(getString(R.string.main_service_prompt));
        values.add(service);

        spinner.setAdapter(new ServiceSpinnerAdapter(spinner.getContext(), textViewResourceId ,values));
        spinner.setSelection(values.size()-1);
    }

    private void saveRecord(String firstName, String lastName, String mobilePhone, String workPhone, String email, int locationID, int serviceID, int userID)
    {
        Worker worker = new Worker();
        worker.setFirstName((firstName));
        worker.setLastName(lastName);
        worker.setMobilePhone(mobilePhone);
        worker.setWorkPhone((workPhone));
        worker.setEmail(email);
        worker.setLocationID((locationID));
        worker.setServiceID(serviceID);
        Call<Integer> call = ApiAdapter.getApiService().addWorker(userID,worker);
        call.enqueue(new Callback<Integer>()
        {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response)
            {
                AlertDialog dialog = alertBox(
                        getString(R.string.main_worker_added),
                        getString(R.string.misc_ok),
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which) {
                                try
                                {
                                    clearForm();
                                    dialog.dismiss();
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
                handleException(t.getMessage(),true);
            }
        });
    }

    private void clearForm()
    {
        firstName.setText("");
        lastName.setText("");
        mobilePhone.setText("");
        workPhone.setText("");
        email.setText("");
        firstName.requestFocus();
        spinnerLocation.setSelection(spinnerLocation.getCount());
        spinnerService.setSelection(spinnerService.getCount());
    }
}
