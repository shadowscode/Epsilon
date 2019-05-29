package com.z2i.epsilon.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.z2i.epsilon.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    public TextView title, desc, date, time;
    public ImageView icon;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.notification_title);
        desc = (TextView) itemView.findViewById(R.id.notification_desc);
        time = (TextView) itemView.findViewById(R.id.notification_time);
        date = (TextView) itemView.findViewById(R.id.notification_date);
        icon = (ImageView) itemView.findViewById(R.id.notification_icon);


    }
}
