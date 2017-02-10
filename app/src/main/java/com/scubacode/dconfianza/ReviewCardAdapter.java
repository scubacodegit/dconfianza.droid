package com.scubacode.dconfianza;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scubacode.dconfianza.R;
import com.scubacode.model.WorkerReview;
import com.scubacode.library.security.UserSessionManager;
import com.scubacode.library.ui.ActivityBase;
import com.scubacode.library.utility.DateTimeHelper;

import java.util.List;

/**
 * Created by htorres on 29/07/2016.
 */
public class ReviewCardAdapter extends RecyclerView.Adapter<ReviewCardViewHolder>
{
    private List<WorkerReview> items;
    private UserSessionManager session;
    private Context context;

    public ReviewCardAdapter(List<WorkerReview> items)
    {
        this.items = items;
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    @Override
    public ReviewCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        ReviewCardViewHolder reviewCardViewHolder = null;
        try
        {
            this.context=viewGroup.getContext();
            View v = LayoutInflater.from(this.context)
                    .inflate(R.layout.review_cardview_item, viewGroup, false);
            reviewCardViewHolder =  new ReviewCardViewHolder(v);
        }
        catch(Exception ex)
        {
            ActivityBase.handleException(ex,true,this.context);
        }

        return reviewCardViewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewCardViewHolder viewHolder, int i)
    {
        try
        {
            viewHolder.text_name.setText(String.format("%1$s %2$s",items.get(i).getCreatedByFirstName(),items.get(i).getCreatedByLastName()));
            viewHolder.text_created_date.setText(DateTimeHelper.toShortDateString(items.get(i).getCreatedDate(), viewHolder.text_created_date.getContext()));
            viewHolder.ratingBar.setRating((float)items.get(i).getRating());
            viewHolder.text_comment.setText(items.get(i).getReview());
        }
        catch(Exception ex)
        {
            ActivityBase.handleException(ex,true,this.context);
        }
    }

}
