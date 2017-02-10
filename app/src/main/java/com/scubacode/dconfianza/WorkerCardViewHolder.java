package com.scubacode.dconfianza;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scubacode.dconfianza.R;
import com.scubacode.model.Worker;
import com.scubacode.library.ui.ActivityBase;

/**
 * Created by htorres on 28/07/2016.
 */
public class WorkerCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView name;
    public TextView email;
    public TextView mobilPhone;
    public TextView workPhone;
    public Worker worker;
    public RatingBar ratingBar;


    public WorkerCardViewHolder(View v)
    {
        super(v);
        v.setClickable(true);
        v.setOnClickListener(this);

        name = (TextView) v.findViewById(R.id.text_name);
        email = (TextView) v.findViewById(R.id.text_email);
        mobilPhone = (TextView) v.findViewById(R.id.text_mobil_phone);
        workPhone = (TextView) v.findViewById(R.id.text_work_phone);
        ratingBar = (RatingBar) v.findViewById(R.id.rating_bar);
    }

    @Override
    public void onClick(View v)
    {
        try
        {
            Gson gson = new Gson();
            Intent intent = new Intent().setClass(v.getContext(), WorkerProfileActivity.class);
            intent.putExtra("Worker", gson.toJson(this.worker));
            v.getContext().startActivity(intent);
        }
        catch(Exception ex)
        {
            ActivityBase.handleException(ex,true,v.getContext());
        }
    }
}
