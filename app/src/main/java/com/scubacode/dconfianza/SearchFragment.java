package com.scubacode.dconfianza;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.scubacode.library.ui.ActivityFragmentBase;
import com.scubacode.model.Location;
import com.scubacode.model.Service;
import com.scubacode.model.Worker;
import com.scubacode.library.io.ApiAdapter;
import com.scubacode.library.ui.ActivityBase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htorres on 24/07/2016.
 */
public class SearchFragment extends ActivityFragmentBase
{
    Spinner locationSpinner=null;
    Spinner serviceSpinner=null;
    int locationID=0;
    int serviceID=0;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    private SwipeRefreshLayout swiperefresh = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view =inflater.inflate(R.layout.activity_search, container, false);
        try
        {
            recycler = (RecyclerView) view.findViewById(R.id.recycler);
            recycler.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(getContext());
            recycler.setLayoutManager(linearLayoutManager);
            locationSpinner  = (Spinner) view.findViewById(R.id.spinner_location);
            swiperefresh =  (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);

            Call<List<Location>> call = ApiAdapter.getApiService().getLocations();
            call.enqueue(new Callback<List<Location>>()
            {
                @Override
                public void onResponse(Call<List<Location>> call, Response<List<Location>> response)
                {
                    try
                    {
                        locationSpinnerOnResponse(locationSpinner, R.layout.support_simple_spinner_dropdown_item , response.body());
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

            locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                {
                    try
                    {
                        locatoinSpinnerOnItemSelected(locationSpinner,view, position);
                    }
                    catch(Exception ex)
                    {
                        ActivityBase.handleException(ex,true, selectedItemView.getContext());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView)
                {

                }
            });

            swiperefresh.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            updateOperation();
                        }
                    }
            );

        }
        catch(Exception ex)
        {
            ActivityBase.handleException(ex,true,this.getContext());
        }

        return view;
    }

    private void locationSpinnerOnResponse(Spinner spinner, int textViewResourceId, List<Location> values)
    {
        Location location = new Location();
        location.setID(0);
        location.setName(getString(R.string.main_location_prompt));
        values.add(location);

        spinner.setAdapter(new LocationSpinnerAdapter(spinner.getContext(), textViewResourceId ,values));
        spinner.setSelection(values.size()-1);
    }

    private void locatoinSpinnerOnItemSelected(Spinner locationSpinner,View view, int position)
    {
        locationID=((LocationSpinnerAdapter)locationSpinner.getAdapter()).getItem(position).getID();
        serviceSpinner= (Spinner) view.findViewById(R.id.spinner_service);
        if(locationID>0)
        {
            Call<List<Service>> call = ApiAdapter.getApiService().getServicesByLocation(locationID);
            call.enqueue(new Callback<List<Service>>()
            {
                @Override
                public void onResponse(Call<List<Service>> call, Response<List<Service>> response)
                {
                    try
                    {
                        serviceSpinner.setEnabled(true);
                        serviceSpinnerOnResponse(serviceSpinner,response.body());
                    }
                    catch(Exception ex)
                    {
                        ActivityBase.handleException(ex,true,getContext());
                    }

                }

                @Override
                public void onFailure(Call<List<Service>> call, Throwable t)
                {
                    ActivityBase.handleException(t.getMessage(),true,getContext());
                }

            });

            serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                {
                    try
                    {
                        serviceID=((ServiceSpinnerAdapter)serviceSpinner.getAdapter()).getItem(position).getID();
                        if(serviceID!=0)
                        {
                            bindWorkersList(locationID,serviceID);
                        }
                    }
                    catch(Exception ex)
                    {
                        ActivityBase.handleException(ex,true,getContext());
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView)
                {

                }

            });
        }
        else
        {
            //popular dropdown servicio son solo un registro indicando "Seleccione Servicio"
            List<Service> list = new ArrayList<Service>();
            Service dummy = new Service();
            dummy.setID(1);
            dummy.setName("");
            list.add(dummy);
            serviceSpinner.setEnabled(false);
            serviceSpinnerOnResponse(serviceSpinner,list);

        }
    }

    private void serviceSpinnerOnResponse(Spinner serviceSpinner,List<Service> values)
    {
        //agrega en la posicion 0 "Seleciones Ciudad"
        Service service = new Service();
        service.setID(0);
        service.setName(getString(R.string.main_service_prompt));
        values.add(service);
        serviceSpinner.setAdapter(new ServiceSpinnerAdapter(serviceSpinner.getContext(), R.layout.support_simple_spinner_dropdown_item ,values));
        serviceSpinner.setSelection(values.size()-1);

    }

    //Listar los workers pertenecientes a la selección del usuario
    private void  bindWorkersList(int locationID, int serviceID)
    {
        Call<List<Worker>> call = ApiAdapter.getApiService().getActiveWorkers(locationID,serviceID);
        call.enqueue(new Callback<List<Worker>>()
        {
            @Override
            public void onResponse(Call<List<Worker>> call, Response<List<Worker>> response)
            {
                try
                {
                    adapter = new WorkerCardAdapter(response.body());
                    recycler.setAdapter(adapter);
                    if(response.body().size()<=0)
                        ActivityBase.reportTransient(getString(R.string.main_service_nodata), ActivityBase.MessageType.Information,getContext());

                }
                catch(Exception ex)
                {
                    ActivityBase.handleException(ex,true,getContext());
                }


            }

            @Override
            public void onFailure(Call<List<Worker>> call, Throwable t)
            {
                ActivityBase.handleException(t.getMessage(), true, getContext());
            }

        });

    }

    private void updateOperation()
    {
        bindWorkersList(locationID,serviceID);
        swiperefresh.setRefreshing(false) ;
    }
}
