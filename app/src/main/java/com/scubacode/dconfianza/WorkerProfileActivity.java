package com.scubacode.dconfianza;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scubacode.model.Worker;
import com.scubacode.model.WorkerReview;
import com.scubacode.library.io.ApiAdapter;
import com.scubacode.library.ui.ActivityBase;
import com.scubacode.library.ui.ActivitySecure;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerProfileActivity extends ActivitySecure {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    private Worker worker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!userIsLoggedIn)
        {
            finish();
            this.LoginRedirect();
            return;
        }
        setContentView(R.layout.activity_worker_profile);
        showToolbar(null, false);

        recycler = (RecyclerView) findViewById(R.id.recycler_review);
        recycler.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);

        Gson gson = new Gson();
        worker =  gson.fromJson(getIntent().getStringExtra("Worker"),Worker.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Gson gson = new Gson();
                    Intent intent = new Intent().setClass(WorkerProfileActivity.this, AddRatingActivity.class);
                    intent.putExtra("Worker", gson.toJson(worker));
                    startActivity(intent);
                }
                catch(Exception ex)
                {
                    ActivityBase.handleException(ex, true, getApplicationContext());
                }
            }
        });
        Call<List<WorkerReview>> call = ApiAdapter.getApiService().getWorkerReviews(worker.getID());
        call.enqueue(new Callback<List<WorkerReview>>()
        {
            @Override
            public void onResponse(Call<List<WorkerReview>> call, Response<List<WorkerReview>> response)
            {
                try
                {
                    adapter = new ReviewCardAdapter(response.body());
                    recycler.setAdapter(adapter);
                }
                catch(Exception ex)
                {
                    ActivityBase.handleException(ex,true,getApplicationContext());
                }
            }
            @Override
            public void onFailure(Call<List<WorkerReview>> call, Throwable t)
            {
                ActivityBase.handleException(t.getMessage(),true, getApplicationContext());
            }

        });

        TextView text_name = (TextView) findViewById(R.id.text_name);
        text_name.setText(String.format("%1$s %2$s",worker.getFirstName(),worker.getLastName()));

        TextView text_mobile_phone = (TextView) findViewById(R.id.text_mobile_phone);
        text_mobile_phone.setText(worker.getMobilPhone());

        TextView text_work_phone = (TextView) findViewById(R.id.text_work_phone);
        text_work_phone.setText(worker.getWorkPhone());

        TextView text_email = (TextView) findViewById(R.id.text_email);
        text_email.setText(worker.getEmail());

        TextView text_location = (TextView) findViewById(R.id.text_location);
        text_location.setText(worker.getLocationName());

        TextView text_service = (TextView) findViewById(R.id.text_service);
        text_service.setText(worker.getServiceName());

        RatingBar rating_bar = (RatingBar) findViewById(R.id.rating_bar2);
        rating_bar.setRating((float)worker.getRating());


    }
}
