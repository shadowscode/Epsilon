package com.z2i.epsilon.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.z2i.epsilon.R;

public class RequestViewHolder extends RecyclerView.ViewHolder{
    public TextView title,domain,status,date,time;

    public RequestViewHolder(View itemView) {
        super(itemView);
        title=(TextView)itemView.findViewById(R.id.rtitle);
        domain=(TextView)itemView.findViewById(R.id.rdomain);
        status=(TextView)itemView.findViewById(R.id.rStatus);
        date=(TextView)itemView.findViewById(R.id.rDate);
        time=(TextView)itemView.findViewById(R.id.rtime);

    }
}
