package com.scubacode.dconfianza;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scubacode.dconfianza.R;
import com.scubacode.model.Worker;
import com.scubacode.library.security.UserSessionManager;
import com.scubacode.library.ui.ActivityBase;

import java.util.List;

/**
 * Created by htorres on 28/07/2016.
 */
public class WorkerCardAdapter extends RecyclerView.Adapter<WorkerCardViewHolder>
{
    private List<Worker> items;
    private UserSessionManager session;
    private Context context;

    public WorkerCardAdapter(List<Worker> items)
    {
        this.items = items;
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    @Override
    public WorkerCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        WorkerCardViewHolder workerCardViewHolder = null;
        try
        {
            this.context=viewGroup.getContext();
            View v = LayoutInflater.from(this.context)
                    .inflate(R.layout.worker_cardview_item, viewGroup, false);
            workerCardViewHolder =  new WorkerCardViewHolder(v);
        }
        catch(Exception ex)
        {
            ActivityBase.handleException(ex,true,this.context);
        }

        return workerCardViewHolder;

    }

    @Override
    public void onBindViewHolder(WorkerCardViewHolder viewHolder, int i)
    {
        try
        {
            viewHolder.worker=items.get(i);
            viewHolder.name.setText(String.format("%1$s %2$s",items.get(i).getFirstName(),items.get(i).getLastName()));
            viewHolder.email.setText(items.get(i).getEmail());
            viewHolder.mobilPhone.setText(items.get(i).getMobilPhone());
            viewHolder.workPhone.setText(items.get(i).getWorkPhone());
            viewHolder.ratingBar.setRating((float)items.get(i).getRating());

        }
        catch(Exception ex)
        {
            ActivityBase.handleException(ex,true,this.context);
        }

    }
}
