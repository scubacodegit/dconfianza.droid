package com.scubacode.dconfianza;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scubacode.dconfianza.R;
import com.scubacode.model.Worker;
import com.scubacode.model.WorkerReview;
import com.scubacode.library.io.ApiAdapter;
import com.scubacode.library.ui.ActivitySecure;
import com.scubacode.library.utility.StringHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htorres on 09/08/2016.
 */
public class AddRatingActivity extends ActivitySecure {

    /*
    multiline
    http://stackoverflow.com/questions/4233626/allow-multi-line-in-edittext-view-in-android
    */

    private Worker worker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_rating);
            if(!userIsLoggedIn)
            {
                finish();
                this.LoginRedirect();
                return;
            }
            showToolbar(null, false);

            Gson gson = new Gson();
            worker =  gson.fromJson(getIntent().getStringExtra("Worker"),Worker.class);

            TextView text_name = (TextView) findViewById(R.id.text_name);
            text_name.setText(String.format("%1$s %2$s",worker.getFirstName(),worker.getLastName()));

            final RatingBar rating_bar = (RatingBar) findViewById(R.id.rating_bar);

            Button button_add_rating = (Button) findViewById(R.id.button_add_rating);
            button_add_rating.setOnClickListener(new View.OnClickListener()
            {

                public void onClick(View v)
                {
                    try
                    {
                        EditText text_comment = (EditText) findViewById(R.id.text_comment);
                        String comment = text_comment.getText().toString();
                        double rating = (double)rating_bar.getRating();
                        if(comment.length()==0)
                        {
                            reportTransient(getString(R.string.main_add_comment));
                            return;
                        }

                        if(rating==0)
                        {
                            reportTransient(getString(R.string.main_select_rating));
                            return;
                        }

                        AddComment(session.getUser().getUserID(),worker.getID(),comment,rating);

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
            handleException(ex);
        }



    }


    private void AddComment(int userID, int workerID, String review, double rating)
    {
        WorkerReview workerReview = new WorkerReview();
        workerReview.setCreatedByID(userID);
        workerReview.setWorkerID(workerID);
        workerReview.setRerview(review);
        workerReview.setRating(rating);
        Call<Integer> call = ApiAdapter.getApiService().addReview(workerReview);
        call.enqueue(new Callback<Integer>()
        {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response)
            {
                AlertDialog dialog = alertBox(
                        getString(R.string.main_rating_added),
                        getString(R.string.misc_ok),
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which) {
                                try
                                {
                                    dialog.dismiss();
                                    Gson gson = new Gson();
                                    Intent intent = new Intent().setClass(getApplicationContext(), WorkerProfileActivity.class);
                                    intent.putExtra("Worker", gson.toJson(worker));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
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
}
