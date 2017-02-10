package com.scubacode.dconfianza;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.scubacode.dconfianza.R;


public class ReviewCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView text_name;
    public TextView text_created_date;
    public RatingBar ratingBar;
    public TextView text_comment;

    public ReviewCardViewHolder(View v)
    {
        super(v);
        v.setClickable(true);
        v.setOnClickListener(this);

        text_name = (TextView) v.findViewById(R.id.text_name);
        text_created_date = (TextView) v.findViewById(R.id.text_created_date);
        ratingBar = (RatingBar) v.findViewById(R.id.rating_bar);
        text_comment= (TextView) v.findViewById(R.id.text_comment);
    }

    @Override
    public void onClick(View v)
    {

    }
}
